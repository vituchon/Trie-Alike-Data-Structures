/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domain.DATrie;

import java.util.Vector;

/**
 *
 * @author Vitucho
 */
public class CompactedElementTrie
{
    CompactedElementTrieNode[]   nodes;
    StringBuffer        tail;
    int[]               charCodes;
    
    /** Caracter que indica el fin de palabra en el buffer de texto de colas */
    public static final String END_CHAR    = "|";
    /** Nodo raiz */
    private static final int ROOT_NODE      = 0;
    
    public CompactedElementTrie (ReducedTrie rt)
    {
        this.nodes = null;
        this.tail  = new StringBuffer ();
        this.charCodes = null;
        this.charCodes = rt.charCodes;

        int count = rt.maxNode + 1;
        
        this.nodes = new CompactedElementTrieNode [count];
        this.tail.append (rt.tail);

        this.nodes[0] = new CompactedElementTrieNode ();
        this.nodes[0].link = rt.nodes[ROOT_NODE].base;
        this.nodes[0].lcheck = 0;

        for (int i = 1;i < count;i++)
        {
            this.nodes[i] = new CompactedElementTrieNode ();
            this.nodes[i].isLeaf = rt.nodes[i].isLeaf();
            this.nodes[i].link   = rt.nodes[i].base;
            if (rt.nodes[i].check == CompactedElementTrieNode.UNDEFINED)
                this.nodes[i].lcheck = CompactedElementTrieNode.UNDEFINED;
            else
            {
                int last = rt.nodes[i].check;
                int cod = i - rt.nodes[last].base;
                this.nodes[i].lcheck = getChar (cod);
            }
        }
    }
    
    /** Verifica si existe una palabra en este trie
     * 
     * @param str Palabra a buscar
     * @return true si existe, false sino
     */
    public boolean exists (String str)
    {
        if (str == null || str.isEmpty ())
            return false;
        
        int curNode = ROOT_NODE;
        int i = 0;
        String strTail;
        boolean end = false,find = false;
        while (!end && !find)
        {
            int curLink = this.nodes[curNode].link;
            if (curLink <= 0) // si la base es igual o menor a cero, busco en el arreglo de colas
            {
                int index = -curLink; // -curBase me daria donde deberia estar
                strTail = get_tail (index);
                find = strTail.equals (str.substring (i));
                end = true;
            }
            else // sino, sigo recorriendo el trie
            {
                int nextNode = curLink + this.charCodes[str.charAt (i)];
                if (nextNode >= this.nodes.length || this.nodes[nextNode].lcheck != str.charAt (i))
                    end = true;
                else
                {
                    i++;
                    curNode = nextNode;
                    if (i == str.length () && this.nodes[curNode].link > 0)
                        return this.nodes[curNode].isLeaf;
                }
            }
        }
        
        return find;
    }
    
    public long space ()
    {
        long bytes = this.nodes.length * 4; // tamano de los punteros [nodes]
        bytes += this.nodes.length * CompactedElementTrieNode.space(); // tamano de los contenidos de los punteros [nodes]
        bytes += tail.length(); // tamano de la cola [tail]
        return bytes;
    }
    
    /** Obtiene de la cola de texto el sufijo que comienza en la posicion index y
     *  termina en el caracter de fin de palabra.
     * 
     * @param index
     * @return Cadena de texto que comienza
     * @see ReducedTrie#END_CHAR
     */
    private String get_tail (int index)
    {
        int end = this.tail.indexOf (END_CHAR,index);
        return this.tail.substring (index,end);
    }

    private char getChar(int code)
    {
        char c = 0;
        while (c < this.charCodes.length && code != this.charCodes[c])
            c++;

        return c;
    }
    
    /** Obtiene un cadena de texto de un diagrama de navegacion.
     * 
     * @return
     */
    public String list_nodes ()
    {
        StringBuffer res = new StringBuffer (255);
        list_nodes_rec (res,ROOT_NODE);
        return res.toString ();
    }    
    
    private void list_nodes_rec (StringBuffer buf,int node)
    {
        
        Vector<Integer> nodos = new Vector<Integer> (10);
        buf.append ("De Nodo: " + node + ". isLeaf = " + this.nodes[node].isLeaf + "\n");
        for (char c = 0;c < this.charCodes.length;c++)
        {
            if (this.nodes[node].link > 0)
            {
                int t = this.nodes[node].link + this.charCodes[c];
                if (t < this.nodes.length && this.nodes[t].lcheck == c)
                {
                    buf.append ("   Con " + c + " voy a nodo " + t + "\n");
                    nodos.add (t);
                }                
            }
            else if (this.nodes[node].lcheck != CompactedElementTrieNode.UNDEFINED)
            {
                buf.append ("   De este nodo obtengo: " + this.get_tail (-this.nodes[node].link) + "\n");
                c = (char) this.charCodes.length;
            }

        }
        for (int i : nodos)
        {
            list_nodes_rec (buf,i);
        }
    }       

    
}
