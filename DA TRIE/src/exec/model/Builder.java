/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exec.model;

import domain.Array.IndexedArray;
import domain.DATrie.DescentTrie;
import domain.DATrie.ReducedTrie;
import domain.DATrie.SuffixTrie;
import domain.Dictionary;
import domain.TrieList.TrieList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * El armador de las estructuras diversas
 */
public class Builder extends Thread
{

    BuilderListener builderListener;
    String fileName;
    boolean infoGetted;
    FileInformation fi;
    Results performanceResults;
    ReducedTrie rt;
    IndexedArray ia;
    TrieList tl;
    DescentTrie dt;
    SuffixTrie st;
    long interval;
    boolean abort;

    private void checkInterrupt() throws InterruptedException
    {
        if (this.isInterrupted() || this.abort)
            throw new InterruptedException ("Cancelado");
        
    }

    private enum Mode
    {

        ADD, SEARCH, REMOVE;
    }

    public enum NotifyEvent
    {

        START, STEP_BEGIN, STEP_END, DATA_RETRIEVED, WORKING, ABORTED, END;
    }

    public Builder(BuilderListener actionTaskListener, String fileName)
    {
        this.builderListener = actionTaskListener;
        this.fileName = fileName;
        this.rt = new ReducedTrie();
        this.ia = new IndexedArray();
        this.tl = new TrieList();
        this.st = new SuffixTrie();
        this.infoGetted = false;
        this.fi = null;
        this.performanceResults = new Results();
        this.abort = false;
    }

    public FileInformation getFileInfo()
    {
        return this.fi;
    }

    public Results getResults()
    {
        return this.performanceResults;
    }

    @Override
    public void run()
    {
        try
        {
            long t1, t2;

            this.interval = 0;
            clearResults();
            this.fireEvent(NotifyEvent.START, "");

            checkInterrupt();

            getinformation(fileName);
            this.fireEvent(NotifyEvent.DATA_RETRIEVED, "Obtención datos preliminares", fi);

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "CONSTRUCCIÓN ESTRUCTURAS");
            this.fireEvent(NotifyEvent.WORKING, "Construyendo Indexed Array");
            t1 = System.currentTimeMillis();
            test(ia, this.fileName, Mode.ADD);
            t2 = System.currentTimeMillis();
            this.fireEvent(NotifyEvent.DATA_RETRIEVED, "Obtención datos", fi);
            performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.TIME_ADD, t2 - t1);
            performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.REAL_SPACE, ia.space());
            performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.OPTIMAL_SPACE, ia.optimalSpace());

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "Construyendo Trie List");
            t1 = System.currentTimeMillis();
            test(tl, this.fileName, Mode.ADD);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.TIME_ADD, t2 - t1);
            performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.REAL_SPACE, tl.space());
            performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.OPTIMAL_SPACE, tl.optimalSpace());

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "Construyendo Suffix Trie");
            t1 = System.currentTimeMillis();
            test(st, this.fileName, Mode.ADD);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.SUFFIX_TRIE, ParameterType.TIME_ADD, t2 - t1);
            performanceResults.setValue(EstructureType.SUFFIX_TRIE, ParameterType.REAL_SPACE, st.space());
            performanceResults.setValue(EstructureType.SUFFIX_TRIE, ParameterType.OPTIMAL_SPACE, st.optimalSpace());

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "Construyendo Reduced Trie");
            t1 = System.currentTimeMillis();
            test(rt, this.fileName, Mode.ADD);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.TIME_ADD, t2 - t1);
            performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.REAL_SPACE, rt.space());
            performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.OPTIMAL_SPACE, rt.optimalSpace());

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "Contruyendo Descent Trie");
            t1 = System.currentTimeMillis();
            rt.trim();
            //dt = DescentTrie.reducedtoDescentTrie(rt, 2);
            //dt = new DescentTrie(rt);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.DESCENT_TRIE, ParameterType.TIME_ADD, t2 - t1);
            performanceResults.setValue(EstructureType.DESCENT_TRIE, ParameterType.REAL_SPACE, rt.space());
            performanceResults.setValue(EstructureType.DESCENT_TRIE, ParameterType.OPTIMAL_SPACE, rt.space());

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "REALIZANDO PRUEBAS DE BUSQUEDA");

            this.fireEvent(NotifyEvent.WORKING, "Busqueda en Indexed Array");
            t1 = System.currentTimeMillis();
            test(ia, this.fileName, Mode.SEARCH);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.TIME_SEARCH, t2 - t1);

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "Busqueda en Trie List");
            t1 = System.currentTimeMillis();
            test(tl, this.fileName, Mode.SEARCH);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.TIME_SEARCH, t2 - t1);

            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "Busqueda en Suffix Trie");
            t1 = System.currentTimeMillis();
            test(st, this.fileName, Mode.SEARCH);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.SUFFIX_TRIE, ParameterType.TIME_SEARCH, t2 - t1);


            checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING, "Busqueda en Reduced Trie");
            t1 = System.currentTimeMillis();
            test(rt, this.fileName, Mode.SEARCH);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.TIME_SEARCH, t2 - t1);

            
            /*checkInterrupt();
            this.fireEvent(NotifyEvent.WORKING,"Busqueda en Descent Trie");
            t1 = System.currentTimeMillis();
            test (dt,this.fileName,Mode.SEARCH);
            t2 = System.currentTimeMillis();
            performanceResults.setValue(EstructureType.DESCENT_TRIE,ParameterType.TIME_SEARCH,t2 - t1);*/

            this.fireEvent(NotifyEvent.END, "");
        }
        catch (Exception e)
        {
            fireEvent(NotifyEvent.ABORTED, e.getMessage());
        }
    }

    public void abort()
    {
        this.abort = true;
    }

    private void fireEvent(NotifyEvent event, String descrip, Object data)
    {
        long milis;
        if (this.interval == 0)
        {
            milis = 0;
        }
        else
        {
            milis = System.currentTimeMillis() - this.interval;
        }
        this.builderListener.notifyEvent(event, new Date(), milis, descrip, data);
        this.interval = System.currentTimeMillis();
    }

    private void fireEvent(NotifyEvent event, String descrip)
    {
        fireEvent(event, descrip, null);
    }

    public static interface BuilderListener
    {

        public void notifyEvent(NotifyEvent event, Date date, long interval, String descrip, Object data);

        public void notifyInit(int total);

        public void notifyProgress(int step);
    }

    private void getinformation(String filename)
    {
        try
        {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            Charset c = Charset.forName("ISO-8859-1"); // para decodificarlos bien
            InputStreamReader isr = new InputStreamReader(fis, c);
            BufferedReader bufRead = new BufferedReader(isr);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count
            int wordCount = 0;

            // Read first line
            line = bufRead.readLine();
            lineCount++;

            while (line != null)
            {
                String[] result = line.split(" ");
                wordCount += result.length;
                line = bufRead.readLine();
                lineCount++;
            }

            bufRead.close();
            fis.close();

            fi = new FileInformation(wordCount, -1, file.length());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void test(Dictionary t, String filename, Mode mode)
    {
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            Charset c = Charset.forName("ISO-8859-1"); // para decodificarlos bien
            InputStreamReader isr = new InputStreamReader(fis, c);

            BufferedReader bufRead = new BufferedReader(isr);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count
            int wordCount = 0, insertCount = 0, deletedCount = 0, findedCount = 0;

            this.builderListener.notifyInit((int) fi.getTotalWords());
            // Read first line
            line = bufRead.readLine();
            lineCount++;


            boolean inserted, deleted;
            while (line != null)
            {
                String[] result = line.split(" ");
                for (int x = 0; x < result.length; x++)
                {
                    wordCount++;
                    switch (mode)
                    {
                        case ADD:
                            inserted = t.insert(result[x]);
                            if (inserted)
                            {
                                insertCount++;
                            }
                            break;
                        case REMOVE:
                            deleted = t.delete(result[x]);
                            if (deleted)
                            {
                                deletedCount++;
                            }
                            break;
                        case SEARCH:
                            if (t.exists(result[x]))
                            {
                                findedCount++;
                            }
                            break;

                    }
                    this.builderListener.notifyProgress(wordCount);
                }
                line = bufRead.readLine();
                lineCount++;
            }

            bufRead.close();
            fis.close();
            System.out.println(wordCount + " palabras procesadas en " + lineCount + " lineas.");
            System.out.println("Insertadas: " + insertCount + " borradas: " + deletedCount + " encontradas " + findedCount);

            if (!infoGetted)
            {
                fi.setKeyWords(insertCount);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void clearResults()
    {
        performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.TIME_ADD, -1);
        performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.REAL_SPACE, -1);
        performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.OPTIMAL_SPACE, -1);
        performanceResults.setValue(EstructureType.INDEXED_ARRAY, ParameterType.TIME_ADD, -1);
        performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.REAL_SPACE, -1);
        performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.OPTIMAL_SPACE, -1);
        performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.TIME_ADD, -1);
        performanceResults.setValue(EstructureType.LIST_TRIE, ParameterType.REAL_SPACE, -1);
        performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.OPTIMAL_SPACE, -1);
        performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.TIME_ADD, -1);
        performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.REAL_SPACE, -1);
        performanceResults.setValue(EstructureType.REDUCED_TRIE, ParameterType.OPTIMAL_SPACE, -1);
    }
}
