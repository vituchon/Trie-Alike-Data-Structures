
package domain.Array;

import domain.Dictionary;

public class IndexedArray implements Dictionary
{
    private String[] phrases;
    private int count;
    Index index;
    
    public IndexedArray ()
    {
        this.phrases = new String[2000];
        for (int i = 1; i < this.phrases.length;i++)
            this.phrases[i] = null;
        this.count = 0;
        this.index = new Index (Short.MAX_VALUE + 1);
    }
    
    public boolean insert (String phrase)
    {
        boolean inserted = false;
        if (phrase == null)
            return false;
        
        if (index.bynarySearch (phrase) == -1) 
        {
            if (this.count == this.phrases.length)
            {
                incrementSize ();
            }
            this.phrases[this.count] = phrase;
            this.index.add (phrase, count);
            this.count++;
            inserted =true;
        }        
        return inserted;
    }
    
    
    public int search (String phrase)
    {
        return index.bynarySearch (phrase);
    }
    
    public boolean exists (String phrase)
    {
        return search (phrase) != -1;
    }
         
    public void cleanAll ()
    {
        this.count = 0;
        for (int i = 0; i < this.phrases.length;i++)
            this.phrases[i] = null;
        this.index = new Index (Short.MAX_VALUE + 1);
    }

    public int size ()
    {
        return this.count;
    }
    
    public long space ()
    {
        long bytes = 4; // tamaño int [count]
        bytes += this.phrases.length * 4; // tamaño de los punteros [phrases]
        for (int i = 0; i < this.phrases.length;i++) // tamaño de cada referencia por puntero [phrases]
            bytes += (this.phrases[i] != null) ? (this.phrases[i].length ()) : 0;
        
        bytes += this.index.space () + 4; // tamaño del indice [index] + puntero al indice [index]
        return bytes;
    }

    public long optimalSpace ()
    {
        long bytes = 4; // tamaño int [count]
        bytes += this.count * 4; // tamaño de los punteros [phrases]
        for (int i = 0; i < this.count;i++) // tamaño de cada referencia por puntero [phrases]
            bytes += (this.phrases[i] != null) ? (this.phrases[i].length ()) : 0;

        bytes += this.index.optimalSpace () + 4; // tamaño del indice [index] + puntero al indice [index]
        return bytes;
    }
    
    @Override
    public String toString ()
    {
        StringBuffer res = new StringBuffer ();
        
        for (int i = 0;i < this.count;i++)
        {
            res.append ("Phrase["+i+"]="+this.phrases[i]+"\n");
        }
        return res.toString ();
    }
    
    public String[] getStrings ()
    {
        String[] r = new String [this.count];
        for (int i = 0;i < this.count;i++)
            r[i] = this.phrases[i];
        
        return r;
    }    

    private void incrementSize ()
    {
        String[] temp = this.phrases;
        this.phrases = new String[this.count * 2];
        for (int i = 0; i < this.phrases.length; i++)
            this.phrases[i] = null;
        System.arraycopy (temp, 0, this.phrases, 0, this.count);
    }

    public boolean delete(String str)
    {
        return false;
    }
    
    class Index
    {
        int[] indexes;
        String[] phrases;
        int count = 0;
        
        public Index (int initSize)
        {
            this.count = 0;
            this.indexes = new int [initSize];
            this.phrases = new String [initSize];         
        }
        
        public void add (String phrase,int index)
        {
            if (count == phrases.length)
                incrementSize ();
                
            int pos = insertionSearch (phrase);
            int i;
            for (i = this.count;i > pos;i--)
            {
                this.phrases[i] = this.phrases[i - 1];
                this.indexes[i] = this.indexes[i - 1];
            }
            this.phrases[i] = phrase;
            this.indexes[i] = index;
            this.count++;
        }
        
        public long space ()
        {
            long bytes = 4; // tamañó de int [count]
            bytes += this.indexes.length * 4; // tamaño de un arreglo de int [indexes]
            bytes += this.phrases.length * 4; // tamalo del arreglo de punteros [phrases]
            for (int i = 0; i < this.phrases.length;i++) // tamaño de cada referencia por puntero [phrases]
                bytes += (this.phrases[i] != null) ? (this.phrases[i].length ()) : 0;
            return bytes;
        }

        public long optimalSpace()
        {
            long bytes = 4; // tamañó de int [count]
            bytes += this.count * 4; // tamaño de un arreglo de int [indexes]
            bytes += this.count * 4; // tamalo del arreglo de punteros [phrases]
            for (int i = 0; i < this.count;i++) // tamaño de cada referencia por puntero [phrases]
                bytes += (this.phrases[i] != null) ? (this.phrases[i].length ()) : 0;
            return bytes;
        }
        
        public int bynarySearch (String phrase)
        {
            int from = 0;
            int to = this.count - 1;
            int res = -1;
            
            while ((from <= to) && (res == -1))
            {
                int mid = (from + to) / 2;
                int comp = phrase.compareTo (phrases[mid]);
                if (comp == 0)
                    res = indexes[mid];
                else if (comp < 0)
                    to = mid - 1;
                else // comp > 0 
                    from = mid + 1;
            }
            return res;

        }
        
        private int insertionSearch (String phrase)
        {
            int from = 0;
            int to  = this.count-1;
            int mid = 0,res = 0,comp = 0;
            
            boolean find = false;
            while ((from <= to) && (!find))
            {
                mid = (from + to) / 2;                
                comp = phrase.compareTo (phrases[mid]);
                if (comp == 0)
                    find = true;
                else if (comp < 0) 
                    to = mid - 1;
                else // comp > 0 
                    from = mid + 1;
            }
            
            if (comp == 0)
                res = mid;
            else if (comp < 0)
                res = from;
            else
                res = to + 1;
            return res;
        }   
        
        private void incrementSize ()
        {
            String[] tempStr = this.phrases;
            int[]    tempInt = this.indexes;
            this.phrases = new String[this.phrases.length * 2];
            this.indexes = new int[this.phrases.length * 2];
            System.arraycopy (tempStr,0,this.phrases,0,this.count);
            System.arraycopy (tempInt,0,this.indexes,0,this.count);
        }
        
        @Override
        public String toString ()
        {
            StringBuffer b = new StringBuffer ();
            for (int i=0;i < this.count;i++)
                b.append ("["+this.phrases[i] +"|"+this.indexes[i]+"]\n");
            
            return b.toString ();
        }


    }
}
