/*
 *  ESTE ES EL MAIN VIEJO USADO PARA TESTEO
 */
package exec;

import domain.DATrie.DescentTrie;
import domain.Array.IndexedArray;
import domain.DATrie.MapTrie;
import domain.DATrie.ReducedTrie;
import domain.TrieList.TrieList;
import domain.DATrie.SuffixTrie;
import domain.DATrie.TrieTree;
import java.nio.charset.Charset;
import java.io.*;
/*import jxl.write.*;
import jxl.write.biff.RowsExceededException;*/

/**
 * A Continuación bocha de codigo de testeo...
 * @author Vitucho
 */
public class runClass
{

    public static final int MODE_INSERT = 0;
    public static final int MODE_DELETE = 1;
    public static final int MODE_SEARCH = 2;
    
    public static void main_false (String args[]) throws IOException
    {   
        SuffixTrie vt = new SuffixTrie ();
        ReducedTrie rt = new ReducedTrie();
        MapTrie t = new MapTrie ();
        IndexedArray d = new IndexedArray();

        /*tr.insert ("bachelor");
        tr.insert ("bachelar");
        tr.insert ("jar");
        tr.insert ("badge");
        tr.insert ("baby");
        tr.insert ("bachelur");*/
        /*tr.insert ("bachelar");
        tr.insert ("bachelor");
        tr.insert ("bachelora");
        tr.insert ("bachelara");
        tr.insert ("beats");
        tr.insert ("beatd");
        tr.insert ("beatc");
        tr.delete ("bachelar");
        tr.delete ("bachelor");
        tr.delete ("bachelara");
        tr.delete ("beats");
        tr.delete ("beatd");
        tr.delete ("beatc");
        tr.delete ("bachelora");
        System.out.println (tr.list_nodes());
        printStrings(tr);*/




        /*for (int i = 20;i > 0;i--)
            t.put(String.valueOf(i),i);
        for (int i = 100;i < 150;i++)
            t.put(String.valueOf(i),i);
        t.put("a", "a");
        t.put("aa",200);
        t.put("ab", 300);
        t.remove("aa");
        t.put ("abcd",1);
        t.put ("abde",2);
        t.put ("abc",3);
        t.put ("abd",66);
        t.put ("abe",77);
        
        System.out.println (t.list_nodes());
        String s[] = t.getStrings();
        for (String string : s)
        {
            System.out.println (string);
        }*/
        


        //tr.trim();
        /*t.delete("abc");
        t.delete("abd");
        t.delete("abe");
        t.delete("abcd");
        t.delete("abde");

        System.out.println (t.list_nodes());
        
       
        printStrings(t);*/
         
        

       /*tr.delete("bachelor");
        tr.delete("jar");
        tr.delete("badge");
        tr.delete ("baby");
        printStrings(tr);*/
        /*rto.insert("ama|rgo");
        rto.insert("am|arga");*/
        //rto.insert("am|ar");
        /*System.out.println (rto);
        System.out.println (rto.list_nodes());*/
        /*rto.delete("ama|rgo");
        rto.delete("am|arga");*/
        //rto.delete("am|ar");

        TrieTreeRunTest ("files/bibble.txt",rt);
        System.out.println ("-------------------------------------------");
        MapTrieRunTest ("files/bibble.txt",t);
        System.out.println ("-------------------------------------------");
        IndexedArrayRunTest ("files/bibble.txt",d);

        //System.out.println (rto);
        /*long t1,t2,total;


        
        t1 = System.currentTimeMillis ();
        test (rto,"files/words.spanish.txt",MODE_INSERT);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN INSERSIÓN REDUCED TRIE. TIEMPO: " + total + " milisegundos.");
        
        // BUSQUEDAS        
        t1 = System.currentTimeMillis ();
        test (vt,"files/words.spanish.txt", MODE_SEARCH);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN BUSQUEDA VITUCHO TRIE. TIEMPO: " + total + " milisegundos.");                
        
        t1 = System.currentTimeMillis ();
        test (rto,"files/words.spanish.txt", MODE_SEARCH);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN BUSQUEDA REDUCED TRIE. TIEMPO: " + total + " milisegundos.");
        
        System.out.println ("Vitucho TrieTree " + vt.optimalSpace () + " Reduced TrieTree " + rto.optimalSpace ());
        

        test (vt,"files/words.spanish.txt",MODE_DELETE);
        test (rto,"files/words.spanish.txt",MODE_DELETE);
        
        
        //rto.insert("a");
        printStrings (vt);
        printStrings (rto);
        System.out.println ("Vitucho TrieTree " + vt.optimalSpace () + " Reduced TrieTree " + rto.optimalSpace ());/*
        /*rto.insert ("bad");
        rto.insert ("bag");
        rto.insert ("ba");
        rto.insert ("beat");
        rto.insert ("eat");
        rto.insert ("zuzo");
        rto.insert ("zuzo`n");*/
        //System.out.println (rto.list_nodes ());
        //DescentTrie dt = rto.compact ();
        //test (dt,"files/netsetup.txt");
        //test (rto,"files/b.txt",MODE_SEARCH);
        //check (dt,"bas");
        //System.out.println ("Descent TrieTree " + dt.space () + " Reduced TrieTree " + rto.optimalSpace ());
        //System.out.println (dt.list_nodes ());   
        
        
        /*String filename = "input.xls";
        WorkbookSettings ws = new WorkbookSettings ();
        ws.setLocale (new Locale ("en", "EN"));
        WritableWorkbook workbook = Workbook.createWorkbook (new File (filename), ws);
        WritableSheet s1 = workbook.createSheet ("biblia", 0);
        WritableSheet s2 = workbook.createSheet ("diccionario", 0);
        WritableSheet s3 = workbook.createSheet ("punto net setup", 0);
        WritableSheet s4 = workbook.createSheet ("RE DIST", 0);
        WritableSheet s5 = workbook.createSheet ("SMART TAG CONVERSIONS LIST", 0);
        test_excel (s1,"files/bibble.txt");
        System.gc ();
        test_excel (s2,"files/words.spanish.txt");
        System.gc ();
        test_excel (s3,"files/netsetup.txt");
        System.gc ();
        test_excel (s4,"files/redist.txt");
        System.gc ();
        test_excel (s5,"files/words.spanish.txt");
        System.gc ();
        workbook.write ();
        workbook.close ();*/
    }

    private static void TrieTreeRunTest(String filename,TrieTree tr)
    {
        long total,t1,t2;
        t1 = System.currentTimeMillis();
        test(tr,filename,MODE_INSERT);
        //tr.trim ();
        t2 = System.currentTimeMillis();
        total = t2 - t1;
        System.out.println("FIN INSERSIÓN. TIEMPO: " + total + " milisegundos.");
        System.out.println("Espacios:\nNORMAL: " + tr.space() + " OPTIMO: " + tr.optimalSpace());
        test(tr,filename, MODE_SEARCH);
        test(tr,filename, MODE_DELETE);
        printStrings(tr);
    }

    private static void MapTrieRunTest(String filename,MapTrie mt)
    {
        long total,t1,t2;
        t1 = System.currentTimeMillis();
        test(mt,filename,MODE_INSERT);
        //tr.trim ();
        t2 = System.currentTimeMillis();
        total = t2 - t1;
        System.out.println("FIN INSERSIÓN. TIEMPO: " + total + " milisegundos.");
        System.out.println("Espacios:\nNORMAL: " + mt.space() + " OPTIMO: " + mt.optimalSpace());
        test(mt,filename, MODE_SEARCH);
        test(mt,filename, MODE_DELETE);
        printStrings(mt);
    }

    private static void IndexedArrayRunTest(String filename,IndexedArray ia)
    {
        long total,t1,t2;
        t1 = System.currentTimeMillis();
        test(ia,filename,MODE_INSERT);
        //tr.trim ();
        t2 = System.currentTimeMillis();
        total = t2 - t1;
        System.out.println("FIN INSERSIÓN. TIEMPO: " + total + " milisegundos.");
        System.out.println("Espacios:\nNORMAL: " + ia.space() + " OPTIMO: " + ia.optimalSpace());
        test(ia,filename, MODE_SEARCH);
        test(ia,filename, MODE_DELETE);
        printStrings(ia);
    }

    private static boolean check (TrieTree t,String str)
    {
        boolean res = t.exists (str);
        System.out.println ("Existe " + str + " : " + res);
        return res;
    }
    
    /*private static void addLabel (int fil, int col, String string, WritableSheet s) throws WriteException
    {
        Label lb = new Label(col,fil,string);
        s.addCell(lb);
    }

    private static void addNumber (int fil, int col, float n, WritableSheet s) throws RowsExceededException, WriteException
    {
        Number lb = new Number(col,fil,n);
        s.addCell(lb);
    }*/
  
    private static void printStrings (TrieTree t)
    {
        String[] words = t.getStrings ();
        System.out.println ("String contenidos: " + words.length);
        for (String word : words)
        {
            System.out.println ("   " + word);
        }  
    }
    
    
    private static void printStrings (IndexedArray d)
    {
        String[] words = d.getStrings ();
        System.out.println ("String contenidos: " + words.length);
        for (String word : words)
        {
            System.out.println ("   " + word);
        }  
    }
    
    private static void printStrings (TrieList t)
    {
        t.display ();
    }    
    
    private static void printStrings (SuffixTrie vt)
    {
        String[] words = vt.getStrings ();
        System.out.println ("String contenidos: " + words.length);
        for (String word : words)
        {
            System.out.println ("   " + word);
        } 
    } 
    
    /*private static void test_excel (WritableSheet s,String test_file) throws RowsExceededException, WriteException
    {
        long t1,t2,total;
        ReducedTrie reducedTrie = new ReducedTrie ();
        IndexedArray  dictionary  = new IndexedArray ();
        TrieList        listTrie    = new TrieList (2000);
        
        addLabel (1,1,"Archivo: " + test_file,s);
        addLabel (4,1,"Reduced Trie",s);
        addLabel (5,1,"Index Array",s);
        addLabel (6,1,"List Trie",s);
        
        addLabel (3,2,"Inserción",s);
        addLabel (3,3,"Busqueda",s);
        addLabel (3,4,"Espacios Reales",s);
        addLabel (3,5,"Espacios Optimos",s);
        addLabel (3,6,"Borrados",s);
        
        // INSERCIONES
        System.out.println ("INSERCIÓN REDUCED TRIE");
        t1 = System.currentTimeMillis ();
        test (reducedTrie, test_file, MODE_INSERT);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN INSERCIÓN REDUCED TRIE. TIEMPO: " + total + " milisegundos.");
        System.out.println ("Espacio en bytes: " + reducedTrie.space () + " (optimal = " + reducedTrie.optimalSpace () + " ) ");
        
        addNumber (4, 2, total, s);
        addNumber (4, 4, reducedTrie.space (), s);
        addNumber (4, 5, reducedTrie.optimalSpace (), s);
        
        System.out.println ("INSERCIÓN DICCIONARIO");
        t1 = System.currentTimeMillis ();
        test (dictionary, test_file, MODE_INSERT);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN INSERCIÓN DICCIONARIO. TIEMPO: " + (t2 - t1) + " milisegundos.");
        System.out.println ("Espacio en bytes: " + dictionary.space ());

        addNumber (5, 2, total, s);
        addNumber (5, 4, dictionary.space (), s);
        addNumber (5, 5, dictionary.space (), s); 
        
        System.out.println ("INSERCIÓN LIST TRIE");
        t1 = System.currentTimeMillis ();
        test (listTrie, test_file, MODE_INSERT);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN INSERCIÓN LIST TRIE. TIEMPO: " + total + " milisegundos.");
        System.out.println ("Espacio en bytes: " + listTrie.space () + " (optimal = " + listTrie.optimalSpace () + " ) ");

        addNumber (6, 2, total, s);
        addNumber (6, 4, listTrie.space (), s);
        addNumber (6, 5, listTrie.optimalSpace (), s);          
        
        // BUSQUEDAS
        t1 = System.currentTimeMillis ();
        System.out.println ("BUSQUEDA REDUCED TRIE");
        test (reducedTrie, test_file, MODE_SEARCH);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN BUSQUEDA REDUCED TRIE. TIEMPO: " + total + " milisegundos.");

        addNumber (4, 3, total, s);
        
        t1 = System.currentTimeMillis ();
        System.out.println ("BUSQUEDA DICCIONARIO");
        test (dictionary, test_file, MODE_SEARCH);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN BUSQUEDA DICCIONARIO. TIEMPO: " + total + " milisegundos.");

        addNumber (5, 3, total, s);
        
        System.out.println ("BUSQUEDA LIST TRIE");
        t1 = System.currentTimeMillis ();
        test (listTrie, test_file, MODE_SEARCH);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN BUSQUEDA LIST TRIE. TIEMPO: " + total + " milisegundos.");

        addNumber (6, 3, total, s);
        
        addLabel (8,1,"Palabras clave del archivo",s);
        addNumber (8,2,dictionary.size (),s);
        addLabel (9,1,"Tamaño del archivo",s);
        File file = new File (test_file);
        
        addNumber (9,2,file.length (),s);
        
        //ELIMINACIONES
        
        t1 = System.currentTimeMillis ();
        System.out.println ("ELIMINACIÓN REDUCED TRIE");
        test (reducedTrie, "editor.log",MODE_DELETE);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN ELIMINACIÓN REDUCED TRIE. TIEMPO: " + total + " milisegundos.");
        
        addNumber (4, 6, total, s);
         
        t1 = System.currentTimeMillis ();
        System.out.println ("ELIMINACIÓN DICCIONARIO");
        test (dictionary, test_file,MODE_DELETE);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN ELIMINACIÓN DICCIONARIO. TIEMPO: " + total + " milisegundos.");
        
        addNumber (5, 6, total, s)
        t1 = System.currentTimeMillis ();
        System.out.println ("ELIMINACIÓN LIST TRIE");
        test (listTrie, test_file,MODE_DELETE);
        t2 = System.currentTimeMillis ();
        total = t2 - t1;
        System.out.println ("FIN ELIMINACIÓN LIST TRIE. TIEMPO: " + total + " milisegundos.");
        
        addNumber (6, 6, total, s);
    }*/

    private static void test (TrieTree t,String filename,int mode)
    {
        try
        {
            /*	Sets up a file reader to read the file passed on the command
            line one character at a time */
            FileInputStream fis = new FileInputStream (filename);
            Charset c = Charset.forName ("ISO-8859-1"); // para decodificarlos bien
            InputStreamReader isr = new InputStreamReader (fis,c);

            BufferedReader bufRead = new BufferedReader (isr);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count
            int wordCount = 0,insertCount = 0,deletedCount = 0,findedCount = 0;

            // Read first line
            line = bufRead.readLine ();
            lineCount++;


            // Read through file one line at time. Print line # and line
            boolean inserted,deleted;
            while (line != null)
            {
                String[] result = line.split (" ");
                for (int x = 0; x < result.length; x++)
                {
                    wordCount++;
                    switch (mode)
                    {
                        case MODE_INSERT:
                            inserted = t.insert (result[x]);
                        if (inserted)
                            insertCount++;
                            break;
                        case MODE_DELETE:
                            deleted = t.delete (result[x]);
                        if (deleted)
                            deletedCount++;
                            break;
                        case MODE_SEARCH:
                            if (t.exists (result[x]))
                                findedCount++;
                            break;

                    }
                }
                line = bufRead.readLine ();
                lineCount++;
            }

            bufRead.close ();
            fis.close ();

            System.out.println (wordCount + " palabras procesadas en " + lineCount + " lineas.");
            System.out.println ("Insertadas: " + insertCount + " borradas: " + deletedCount + " encontradas " + findedCount);
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }
    
    private static void test (IndexedArray d,String filename,int mode)
    {
        try
        {
            /*	Sets up a file reader to read the file passed on the command
            line one character at a time */
            FileInputStream fis = new FileInputStream (filename);
            Charset c = Charset.forName ("ISO-8859-1"); // para decodificarlos bien
            InputStreamReader isr = new InputStreamReader (fis,c);
            
            BufferedReader bufRead = new BufferedReader (isr);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count 
            int wordCount = 0,insertCount = 0,deletedCount = 0,findedCount = 0;
            
            // Read first line
            line = bufRead.readLine ();
            lineCount++;
            

            // Read through file one line at time. Print line # and line
            boolean inserted,deleted;
            while (line != null)
            {
                String[] result = line.split (" ");
                for (int x = 0; x < result.length; x++)
                {
                    wordCount++;
                    switch (mode)
                    {
                        case MODE_INSERT:
                            inserted = d.insert (result[x]);
                        if (inserted)
                            insertCount++;
                            break;
                        case MODE_SEARCH:
                            if (d.exists (result[x]))
                                findedCount++;
                            break;
                            
                    }
                }
                line = bufRead.readLine ();
                lineCount++;
            }

            bufRead.close ();
            fis.close ();

            System.out.println (wordCount + " palabras procesadas en " + lineCount + " lineas.");            
            System.out.println ("Insertadas: " + insertCount + " borradas: " + deletedCount + " encontradas " + findedCount);
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }   

    private static void test (TrieList t,String filename,int mode)
    {
        try
        {
            /*	Sets up a file reader to read the file passed on the command
            line one character at a time */
            FileInputStream fis = new FileInputStream (filename);
            Charset c = Charset.forName ("ISO-8859-1"); // para decodificarlos bien
            InputStreamReader isr = new InputStreamReader (fis,c);
            
            BufferedReader bufRead = new BufferedReader (isr);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count 
            int wordCount = 0,insertCount = 0,deletedCount = 0,findedCount = 0;
            
            // Read first line
            line = bufRead.readLine ();
            lineCount++;
            

            // Read through file one line at time. Print line # and line
            boolean inserted,deleted;
            while (line != null)
            {
                String[] result = line.split (" ");
                for (int x = 0; x < result.length; x++)
                {
                    wordCount++;
                    switch (mode)
                    {
                        case MODE_INSERT:
                            inserted = t.insert (result[x],1);
                        if (inserted)
                            insertCount++;
                            break;
                        case MODE_SEARCH:
                            if (t.getEntry (result[x]) != -1)
                                findedCount++;
                            break;
                            
                    }
                }
                line = bufRead.readLine ();
                lineCount++;
            }

            bufRead.close ();
            fis.close ();

            System.out.println (wordCount + " palabras procesadas en " + lineCount + " lineas.");            
            System.out.println ("Insertadas: " + insertCount + " borradas: " + deletedCount + " encontradas " + findedCount);
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    private static void test (MapTrie t,String filename,int mode)
    {
        try
        {
            /*	Sets up a file reader to read the file passed on the command
            line one character at a time */
            FileInputStream fis = new FileInputStream (filename);
            Charset c = Charset.forName ("ISO-8859-1"); // para decodificarlos bien
            InputStreamReader isr = new InputStreamReader (fis,c);

            BufferedReader bufRead = new BufferedReader (isr);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count
            int wordCount = 0,insertCount = 0,deletedCount = 0,findedCount = 0;

            // Read first line
            line = bufRead.readLine ();
            lineCount++;


            // Read through file one line at time. Print line # and line
            boolean inserted,deleted;
            while (line != null)
            {
                String[] result = line.split (" ");
                for (int x = 0; x < result.length; x++)
                {
                    wordCount++;
                    switch (mode)
                    {
                        case MODE_INSERT:
                            inserted = (Boolean)t.put(result[x],insertCount);
                        if (inserted)
                            insertCount++;
                            break;
                        case MODE_DELETE:
                            deleted = (Boolean)t.remove(result[x]);
                            if (deleted)
                                deletedCount++;
                            break;
                        case MODE_SEARCH:
                            if (t.exists(result[x]))
                                findedCount++;
                            break;

                    }
                }
                line = bufRead.readLine ();
                lineCount++;
            }

            bufRead.close ();
            fis.close ();

            System.out.println (wordCount + " palabras procesadas en " + lineCount + " lineas.");
            System.out.println ("Insertadas: " + insertCount + " borradas: " + deletedCount + " encontradas " + findedCount);
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }

    private static void test (DescentTrie dt,String filename)
    {
        try
        {
            /*	Sets up a file reader to read the file passed on the command
            line one character at a time */
            FileInputStream fis = new FileInputStream (filename);
            Charset c = Charset.forName ("ISO-8859-1"); // para decodificarlos bien
            InputStreamReader isr = new InputStreamReader (fis,c);
            
            BufferedReader bufRead = new BufferedReader (isr);

            String line; 	// String that holds current file line
            int lineCount = 0;	// Line number of count 
            int wordCount = 0,findedCount = 0;
            
            // Read first line
            line = bufRead.readLine ();
            lineCount++;
            

            while (line != null)
            {
                String[] result = line.split (" ");
                for (int x = 0; x < result.length; x++)
                {
                    wordCount++;
                    if (check (dt, result[x]))
                        findedCount++;
                }
                line = bufRead.readLine ();
                lineCount++;
            }

            bufRead.close ();
            fis.close ();

            System.out.println (wordCount + " palabras procesadas en " + lineCount + " lineas.");            
            System.out.println ("Encontradas " + findedCount);
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
    }
   
}