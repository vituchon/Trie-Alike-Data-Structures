/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.DATrie;

import java.util.Arrays;
import java.util.Vector;

/**
 *
 * @author Vitucho
 */
public class DescentTrie implements TrieTree
{

    DescentTrieNode[] nodes;
    StringBuffer tail;
    int[] charCodes;
    int maxNode;
    
    /** Caracter que indica el fin de palabra en el buffer de texto de colas */
    public static final String END_CHAR    = "|";
    private static final char  ESCAPE_CHAR = '\\';

    /** Nodo raiz */
    private static final int ROOT_NODE = 0;

    public DescentTrie(ReducedTrie rt)
    {
        this.nodes = null;
        this.tail = new StringBuffer();
        this.charCodes = null;
        this.charCodes = rt.charCodes;

        int count = rt.maxNode + 1;

        this.nodes = new DescentTrieNode[count];
        this.tail.append(rt.tail);

        this.nodes[0] = new DescentTrieNode();
        this.nodes[0].link = rt.nodes[ROOT_NODE].base;
        this.nodes[0].check = 0;

        for (int i = 1; i < count; i++)
        {
            this.nodes[i] = new DescentTrieNode();
            this.nodes[i].isTerminal = rt.nodes[i].isTerminal;
            this.nodes[i].link = rt.nodes[i].base;
            if (rt.nodes[i].check == ReducedTrieNode.UNDEFINED)
            {
                this.nodes[i].check = DescentTrieNode.UNDEFINED;
            }
            else
            {
                int last = rt.nodes[i].check;
                int cod = i - rt.nodes[last].base;
                this.nodes[i].check = getChar(cod);
            }
        }
    }

    public DescentTrie()
    {
        super();
    }

    /** Verifica si existe una palabra en este trie
     * 
     * @param str Palabra a buscar
     * @return true si existe, false sino
     */
    public boolean exists(String str)
    {
        if (str == null || str.isEmpty())
        {
            return false;
        }

        int curNode = ROOT_NODE;
        int i = 0;
        String strTail;
        boolean end = false, find = false;
        while (!end && !find)
        {
            int curLink = this.nodes[curNode].link;
            if (curLink <= 0) // si la base es igual o menor a cero, busco en el arreglo de colas
            {
                int index = -curLink; // -curBase me daria donde deberia estar
                strTail = tailRetrieve(index);
                find = strTail.equals(str.substring(i));
                end = true;
            }
            else // sino, sigo recorriendo el trie
            {
                int nextNode = curLink + this.charCodes[str.charAt(i)];
                if (nextNode >= this.nodes.length || this.nodes[nextNode].check != str.charAt(i))
                {
                    end = true;
                }
                else
                {
                    i++;
                    curNode = nextNode;
                    if (i == str.length() && this.nodes[curNode].link > 0)
                    {
                        return this.nodes[curNode].isTerminal;
                    }
                }
            }
        }

        return find;
    }

    public long space()
    {
        long bytes = this.nodes.length * 4; // tamano de los punteros [nodes]
        bytes += this.nodes.length * DescentTrieNode.space(); // tamano de los contenidos de los punteros [nodes]
        bytes += tail.length(); // tamano de la cola [tail]
        return bytes;
    }


    /** Obtiene de la cola de texto el sufijo que comienza en la posici��n index y
     *  termina en el caracter de fin de palabra. Saltea escapeos.
     *
     * @param index
     * @return Cadena de texto que comienza
     * @see ReducedTrie#END_CHAR
     */
    private String tailRetrieve (int index)
    {
        int end = index - 1;
        do
        {
            // salteo escapeos "\|"
            end = this.tail.indexOf(END_CHAR, end + 1);
            // termino cuando encuentro un '|' no escapeado. sin el '\' que le proceda
        } while (end > 0 && this.tail.charAt(end - 1) == ESCAPE_CHAR);
        return unescapeString(this.tail.substring (index,end));
    }

    /** Escapeo la cadena de forma tal que si tiene | o \ les agrega un escape con
    con caracter \ */
    private String unescapeString (String word)
    {
        StringBuffer res = new StringBuffer (word.length());
        int i = 0,l = word.length();
        while (i < l)
        {
            char c = word.charAt(i);
            if (c == ESCAPE_CHAR)
            {
                i++;
                c = word.charAt(i);
            }

            res.append(c);
            i++;
        }
        return res.toString();
    }

    private char getChar(int code)
    {
        char c = 0;
        while (c < this.charCodes.length && code != this.charCodes[c])
        {
            c++;
        }

        return c;
    }

    /** Obtiene un cadena de texto de un diagrama de navegaci��n.
     * 
     * @return
     */
    public String list_nodes()
    {
        StringBuffer res = new StringBuffer(255);
        list_nodes_rec(res, ROOT_NODE);
        return res.toString();
    }

    private void list_nodes_rec(StringBuffer buf, int node)
    {

        Vector<Integer> nodos = new Vector<Integer>(10);
        buf.append("De Nodo: " + node + ". isLeaf = " + this.nodes[node].isTerminal + "\n");
        for (char c = 0; c < this.charCodes.length; c++)
        {
            if (this.nodes[node].link > 0)
            {
                int t = this.nodes[node].link + this.charCodes[c];
                if (t < this.nodes.length && this.nodes[t].check == c)
                {
                    buf.append("   Con " + c + " voy a nodo " + t + "\n");
                    nodos.add(t);
                }
            }
            else if (this.nodes[node].check != DescentTrieNode.UNDEFINED)
            {
                buf.append("   De este nodo obtengo: " + this.tailRetrieve(-this.nodes[node].link) + "\n");
                c = (char) this.charCodes.length;
            }

        }
        for (int i : nodos)
        {
            list_nodes_rec(buf, i);
        }
    }

    public boolean delete(String str)
    {
        return false;
    }

    public String[] getStrings()
    {
        return null;
    }

    public boolean insert(String str)
    {
        return false;
    }

    public long optimalSpace()
    {
        return this.space();
    }

    /** Construye a partir de un reduced trie **/
    public static DescentTrie reducedtoDescentTrie(ReducedTrie rd, int cant)
    {
        int children = 0, father = 0;
        Leaf leaves[] = new Leaf[rd.maxNode];
        ReducedTrieNode leaves_rd[] = new ReducedTrieNode[rd.maxNode];
        boolean eofcheck = false;

        /** Crear nuevo descent trie **/
        DescentTrie dt = copyTransformReducedTrie(rd);
        //Crear nueva tail
        StringBuffer tail = new StringBuffer();
        dt.tail = tail;
        //dt.tail.length() = 0;
        /** Obtener nodos hojas **/
        leaves = obtenerHojas(dt, rd, leaves_rd);
        /** ordenar por campo CHECK (puntero al padre) **/
        Arrays.sort(leaves);
        /** Primera Pasada , podar las hojas que tienen solo un hermano**/
        father = leaves[0].node.check;
        // Corte control por campo father
        for (int k = 0; k < leaves.length; k++)
        {
            children++;
            father = leaves[k].node.check;
            eofcheck = eofFather(cant, children, father, leaves, k);
            if (eofcheck)
            {
                if (children == cant)
                {
                    deleteSiblings(rd, father, leaves, dt, k);
                }
                //Resetar variables
                children = 0;
            }
        }
        /** Reacomodar links a sufijos **/
        //Recorrer todo el arbol descent y compararlo con el reduced
        for (int i = 0; i < dt.maxNode; i++)
        {

            //Comparar el link del descent con el de reduced
            //Si cambio significa que se deja como esto, sino
            //se appendea en la nueva tail
            if (dt.nodes[i].isLeaf == true && rd.nodes[i].isLeaf() == true &&
                    dt.nodes[i].link == rd.nodes[i].base &&
                    dt.nodes[i].link != ReducedTrieNode.UNDEFINED)
            {
                appendTail(rd.nodes[i].base, rd.tail, dt, i);

            }

        }

        return dt;
    }

    private static void appendTail(int base, StringBuffer tail_rd, DescentTrie dt, int i)
    {
        dt.nodes[i].link = dt.tail.length();
        dt.tail.append(getSuffix(base, tail_rd));
    }

    private static boolean eofFather(int cant, int children, int father, Leaf[] leaves, int k)
    {
        boolean eofcheck = false;
        if (k + 1 == leaves.length)
        {//es el ultimo
            eofcheck = true;
        }
        else if (father != leaves[k + 1].node.check)
        {//
            eofcheck = true;
        }
        return eofcheck;
    }

    private static DescentTrie copyTransformReducedTrie(ReducedTrie rd)
    {
        //Copiar del Reduced Trie
        DescentTrie dt = new DescentTrie();

        dt.charCodes = rd.charCodes;
        dt.maxNode = rd.maxNode;
        dt.nodes = new DescentTrieNode[dt.maxNode];
        // Transformar los nodos
        for (int k = 0; k < rd.maxNode; k++)
        {
            dt.nodes[k] = DescentTrieNode.toDescentTrieNode(rd.nodes[k]);

        }
        return dt;
    }

    private static Leaf[] obtenerHojas(DescentTrie dt, ReducedTrie rd, ReducedTrieNode leaves_rd[])
    {

        int i = 0, j = 0;
        Leaf leaves_aux[], leaves[] = new Leaf[rd.maxNode];
        //Recorrer lineal
        for (i = 0; i < dt.maxNode; i++)
        {
            //A los que son hojas, copiarlas y ordenarlas por padre (CHECK)
            if (dt.nodes[i].isLeaf)
            {
                leaves[j] = new Leaf(i, dt.nodes[i]);
                //tambien copiar la referencia a las hojas del reduced trie
                leaves_rd[j] = rd.nodes[i];
                j++;
            }
        }

        leaves_aux = new Leaf[j];
        for (int k = 0; k < leaves_aux.length; k++)
        {
            leaves_aux[k] = leaves[k];
        }
        return leaves_aux;
    }

    private static void deleteSiblings(ReducedTrie rd, int father, Leaf[] leaves, DescentTrie dt, int k)
    {
        //Crear el nuevo sufijo que contiene a ambos
        int idx1 = leaves[k].idx;
        int idx2 = leaves[k - 1].idx;
        dt.insertSplitSuffix(dt.nodes[father], leaves[k].node, idx1, leaves[k - 1].node, idx2, rd.tail);
        // Marcar como eliminados a los dos anteriores
        leaves[k].node.link = ReducedTrieNode.UNDEFINED;
        leaves[k - 1].node.link = ReducedTrieNode.UNDEFINED;
        //Marcar al padre como nueva hoja
        dt.nodes[father].isLeaf = true;

    }

    private int insertSplitSuffix(DescentTrieNode father,
            DescentTrieNode son1, int son1_idx,
            DescentTrieNode son2, int son2_idx, StringBuffer tail)
    {

        int link = 0;
        String suf1 = new String(),
                suf2 = new String(),
                common = null;

        //Primero se toma en cuenta el prefijo en comun de ambos,
        //si es que ambos estan completos
        if (son1 != null && son2 != null)
        {

            //Leer el sufijo del primer hijo
            suf1 = getSuffix(son1.link, tail);
            //Leer el sufijo del segundo hijo
            suf2 = getSuffix(son2.link, tail);

            //Obtener los arcos y tranformarlos en sufijos
            int arc1 = son1_idx - father.link;
            int arc2 = son2_idx - father.link;

            char last1 = (char) this.getCharfromCode(arc1);
            char last2 = (char) this.getCharfromCode(arc2);

            //Obtener y agregar a la cola el prefijo comun
            common = getCommonPrefix(suf1, suf2);
            father.link = this.tail.length();
            father.split = true;
            father.isLeaf = true;
            //appendear el common prefix
            this.tail.append(common);

            //apendear la longitud del primero, siempre sera uno
            this.tail.append("1");
            //appendear el primero y el segundo
            this.tail.append(last1);
            this.tail.append(last2);
            //appendear separador de palabra
            this.tail.append(END_CHAR);

        }
        return link;

    }

    static String getSuffix(int link, StringBuffer tail)
    {
        int end;
        String suf1 = null;
        end = tail.indexOf(END_CHAR, link);
        if (end >= 0)
        {
            suf1 = tail.substring(link, end + 1);
        }
        return suf1;
    }

    private static String getCommonPrefix(String s1, String s2)
    {
        int len = 0, i = 0;
        String sr = null;

        if (s1.length() < s2.length())
        {
            len = s1.length();
        }
        else
        {
            len = s2.length();
        }

        //		Obtener el prefijo comun

        while (i < len && (s1.charAt(i) == s2.charAt(i)))
        {
            i++;
        }

        if (i > 0)
        {
            sr = s1.substring(0, i);
        }

        return sr;

    }

    public int getCharfromCode(int code)
    {
        //busqueda secuencial del char code
        int aux = 0;
        for (int i = 0; i < this.charCodes.length; i++)
        {
            if (this.charCodes[i] == code)
            {
                aux = i;
                break;
            }
        }
        return aux;

    }
}
