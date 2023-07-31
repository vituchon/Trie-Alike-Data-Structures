package domain.DATrie;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

/**
 * Arbol TrieTree para almanecar cadenas de texto.
 * NO se puede usar con el caracter "|" puesto que uso esta reservado, hasta
 * que se revise la implementaci��n y se consiga desprenderse de esta condici��n.
 * @see ReducedTrie#END_CHAR
 */
public class MapTrie implements TrieTree,Map<String,Object>
{

    MapTrieNode[] nodes;
    StringBuffer tail;
    int tailPos;
    int[] charCodes;
    int maxNode;
    int size;

    /** Caracter que indica el fin de palabra en el buffer de texto de colas */
    public static final String END_CHAR = "|";
    private static final char END_CHAR_C = END_CHAR.charAt(0);
    private static final char ESCAPE_CHAR = '\\';
    /** Cantidad de nodos inicial */
    private static final int INIT_CAPACITY = 5000;
    /** Nodo raiz */
    private static final int ROOT_NODE = 0;

    /**
     * Constructor
     */
    public MapTrie()
    {
        initCharCodes();

        this.nodes = new MapTrieNode[INIT_CAPACITY];
        for (int i = 0; i < INIT_CAPACITY; i++)
        {
            this.nodes[i] = new MapTrieNode();
        }
        this.nodes[ROOT_NODE].base = 1;
        this.nodes[ROOT_NODE].check = 0;

        this.tail = new StringBuffer(INIT_CAPACITY);
        this.tailPos = 0;
        this.size    = 0;
    }

    /** Inserta una palabra en este trie, si no existe previamente.
     * 
     * @param str Palabra a insertar
     * @return true si fue insertado, sino retorna false.
     */
    public boolean insert(String str)
    {
        boolean inserted = true;
        if (str != null && !do_exist(str))
        {
            size++;
            do_insert(str,null);
        }
        else
            inserted = false;
        return inserted;
    }

    /** Borra una palabra en este trie, si exist��a previamente.
     * 
     * @param str Palabra a insertar
     * @return  true si fue borrado, sino retorna false.
     */
    public boolean delete(String str)
    {
        boolean delete = true;
        if (str != null && exists(str))
        {
            size--;
            do_delete(str);
        }
        else
            delete = false;
        return delete;
    }

    /** Verifica si existe una palabra en este trie
     * 
     * @param str Palabra a buscar
     * @return true si existe, false sino
     */
    public boolean exists(String str)
    {
        if (str == null)
            return false;

        return do_exist(str);
    }

    /** Calcula en forma aproximada el espacio ocupado en bytes de la estructura */
    public long space()
    {
        long bytes = 8; // [tail_pos] + [maxNode] (dos enteros)
        bytes += this.nodes.length * 4; // tama��o de los punteros [nodes]
        bytes += this.nodes.length * MapTrieNode.space(); // tama��o de los contenidos de los punteros [nodes]
        bytes += tail.length(); // tama��o de la cola
        return bytes;
    }

    /** Calcula en forma aproximada el espacio minimo necesario en bytes de la estructura con los datos actuales */
    public long optimalSpace()
    {
        // considero hasta el nodo m��ximo
        long bytes = 8; // [tail_pos] + [maxNode] (dos enteros)
        bytes += this.maxNode * 4; // tama��o de los punteros [nodes]
        bytes += this.maxNode * MapTrieNode.space(); // tama��o de los contenidos de los punteros [nodes]
        bytes += tail.length(); // tama��o de la cola
        return bytes;
    }

    private boolean do_exist(String str)
    {
        int curNode = ROOT_NODE;
        int i = 0, len = str.length();
        String strTail;
        boolean end = false;
        boolean find = false;

        while (!end)
        {
            int curBase = this.nodes[curNode].base;
            if (i == len)
            {
                find = this.nodes[curNode].isTerminal;
                end = true;
            }

            if (curBase <= 0 && !find)
            {
                int index = -curBase; // -curBase me daria donde deberia estar
                strTail = tailRetrieve(index);
                find = strTail.equals(str.substring(i));
                end = true;
            }
            else if (i < len)
            {
                int nextNode = curBase + this.charCodes[str.charAt(i)];
                if (this.nodes[nextNode].check != curNode)
                    end = true;
                else
                {
                    curNode = nextNode;
                    i++;
                }
            }
            else
                end = true;

        }
        return find;
    }

    private void do_insert(String str,Object data)
    {
        int curNode = ROOT_NODE, i = 0;
        boolean end = false;
        while (!end)
        {
            int curBase = this.nodes[curNode].base;
            if (curBase <= 0)
            {
                // INSERCION CON COLISION EN COLA 
                insert_2(str,data, i, curNode, curBase);
                end = true;
            }
            else if (str.length() == i)
            {
                // INSERCION IMPLICITA
                this.nodes[curNode].isTerminal = true;
                end = true;
            }
            else
            {
                int nextNode = curBase + this.charCodes[str.charAt(i)];
                if (this.nodes[nextNode].check != curNode)
                    if (this.nodes[nextNode].check == MapTrieNode.UNDEFINED)
                    {
                        // INSERSION SIN COLISION
                        insert_1(str,data, i, curNode, nextNode);
                        end = true;
                    }
                    else
                    {
                        // INSERCI��N CON COLISION EN EL ARBOL
                        insert_3(str,data, i, curNode, nextNode);
                        end = true;
                    }
                else
                {
                    i++;
                    curNode = nextNode;
                }
            }
        }
    }

    private void insert_1(String str,Object data, int i, int curNode, int newNode)
    {
        //System.out.println ("TRIE. INSERCI��N SIN COLISI��N DE: " + str);
        this.nodes[newNode].base = -tailPos;
        this.nodes[newNode].check = curNode;
        this.nodes[newNode].isTerminal = false;
        this.nodes[newNode].data       = data;

        String strTail = escapeString(str.substring(i + 1));
        this.tail.append(strTail + END_CHAR);
        tailPos += strTail.length() + 1;

        if (newNode > maxNode)
            maxNode = newNode;
    }

    private void insert_2(String str,Object data, int i, int curNode, int curBase)
    {
        //System.out.println ("TRIE. INSERCI��N CON COLISI��N EN COLA DE: " + str);
        String strTail = tailRetrieve(-curBase);
        String[] sep = separate(strTail, str.substring(i));
        char[] commomPrefix = sep[2].toCharArray();
        int temp = -curBase, q = 1, newNode = curNode, lastNode = curNode,origNode = curNode;
        Object origNodeData = this.nodes[origNode].data;

        // Inserto el prefijo comun como nodos (saco de la cola la parte comun)
        for (char c : commomPrefix)
        {
            q = x_check(String.valueOf(c));
            this.nodes[curNode].base = q;
            this.nodes[curNode].isTerminal = false;
            lastNode = curNode;
            newNode = q + this.charCodes[c];
            this.nodes[newNode].check = curNode;
            this.nodes[newNode].isTerminal = false;
            this.nodes[newNode].data       = null;
            curNode = newNode;
        }
        // ESBLESCO LOS NUEVOS TERMINALES
        // Si el nuevo string no comprende del todo  
        // ej: Ingreso badge y existiendo bachelor.
        if (sep[0].length() > 0 && sep[1].length() > 0)
        {
            char ch1 = sep[0].charAt(0);
            char ch2 = sep[1].charAt(0);
            q = x_check(ch1 + "" + ch2);
            int node1 = q + this.charCodes[ch1];
            int node2 = q + this.charCodes[ch2];

            this.nodes[newNode].base = q;
            this.nodes[newNode].isTerminal = false;
            this.nodes[newNode].data       = null; // delete

            this.nodes[node1].base = -temp;
            this.nodes[node1].check = newNode;
            this.nodes[node1].isTerminal = false;
            this.nodes[node1].data       = origNodeData;
            this.nodes[origNode].data    = null;

            this.nodes[node2].base = -this.tailPos;
            this.nodes[node2].check = newNode;
            this.nodes[node2].isTerminal = false;
            this.nodes[node2].data  = data;

            sep[0] = escapeString(sep[0].substring(1));
            sep[1] = escapeString(sep[1].substring(1));
            this.tail.replace(temp, temp + sep[0].length() + 1, sep[0] + END_CHAR);

            this.tail.insert(this.tailPos, sep[1] + END_CHAR);
            this.tailPos += sep[1].length() + 1;

            // este nodo era la hoja apuntaba a la cadena con la cual colisono, como se lo
            // hace nodo m��s, deja de ser hoja y isTerminal se vuelve false para ��l.
            // en el ejemplo ser��a la "b" del bachelor.
            //this.nodes[origNode].isTerminal = false;
        }
        // Si el nuevo string comprende a uno que existe
        // ej: ingreso cadena, existiendo cad.
        else if (sep[1].length() > 0)
        {
            insert_2_bis(sep[1],origNodeData,data, curNode, origNode, this.tailPos);

            sep[1] = escapeString(sep[1].substring(1));
            this.tail.insert(this.tailPos, sep[1] + END_CHAR);
            this.tailPos += sep[1].length() + 1;

        }
        // Si el nuevo string esta comprendido por uno que existe
        // ej: ingreso cad, existiendo cadena.
        else
        {
            insert_2_bis(sep[0],data,origNodeData, curNode,origNode, temp);

            sep[0] = escapeString(sep[0].substring(1));
            this.tail.replace(temp, temp + sep[0].length() + 1, sep[0] + END_CHAR);
        }

    }

    private void insert_2_bis(String str,Object curNodeData,Object newNodeData, int curNode,int origNode, int tempBase)
    {
        char ch = str.charAt(0);
        int q = x_check(String.valueOf(ch));
        int node2 = q + this.charCodes[ch];

        // el nodo actual pasa a ser terminal pero tambien referencia a otro posterior
        this.nodes[curNode].base = q;
        this.nodes[curNode].isTerminal = true;
        this.nodes[curNode].data       = curNodeData;
        if (curNode != origNode)
            this.nodes[origNode].data      = null;
        
        // se establece como terminal el nuevo nodo
        this.nodes[node2].base = -tempBase;
        this.nodes[node2].check = curNode;
        this.nodes[node2].isTerminal = false;
        this.nodes[node2].data  = newNodeData;
    }

    private void insert_3(String str,Object data, int i, int curNode, int colisionNode)
    {
        //System.out.println ("TRIE. INSERCI��N CON COLISION DE INCONSISTENCIA DE: " + str);
        int tempNode1 = colisionNode, tempNode2;
        int lastNode = this.nodes[colisionNode].check;
        String chars_checknode_fail = list(lastNode);
        String strTail;

        int TEMP_BASE = this.nodes[lastNode].base;
        int q = x_check(chars_checknode_fail);
        this.nodes[lastNode].base = q;
        for (char ch : chars_checknode_fail.toCharArray())
        {
            tempNode1 = TEMP_BASE + this.charCodes[ch];
            tempNode2 = this.nodes[lastNode].base + this.charCodes[ch];

            this.nodes[tempNode2].base = this.nodes[tempNode1].base;
            this.nodes[tempNode2].check = this.nodes[tempNode1].check;
            this.nodes[tempNode2].isTerminal = this.nodes[tempNode1].isTerminal;
            this.nodes[tempNode2].data = this.nodes[tempNode1].data;
            if (this.nodes[tempNode1].base > 0)
            {
                int k = this.nodes[tempNode1].base + 1;
                while (k - this.nodes[tempNode1].base < 255 && k < this.nodes.length)
                {
                    if (this.nodes[k].check == tempNode1)
                        this.nodes[k].check = tempNode2;
                    k++;
                }
            }
            if (curNode != lastNode && tempNode1 == curNode)
                curNode = tempNode2;
            this.nodes[tempNode1].base = 0;
            this.nodes[tempNode1].check = MapTrieNode.UNDEFINED;
            this.nodes[tempNode1].isTerminal = false;
            this.nodes[tempNode1].data = null;
        }

        strTail = str.substring(i);
        int temp_node = this.nodes[curNode].base + this.charCodes[strTail.charAt(0)];
        this.nodes[temp_node].base = -this.tailPos;
        this.nodes[temp_node].check = curNode;
        this.nodes[temp_node].data = data;

        str = escapeString(strTail.substring(1));
        this.tail.append(str + END_CHAR);
        this.tailPos += str.length() + 1;
    }

    private boolean do_delete(String str)
    {
        int curNode = ROOT_NODE;
        int i = 0, len = str.length();
        boolean end = false, find = false;
        boolean checkMaxNode = false;
        String strTail;

        while (!end)
        {
            if (curNode == maxNode)
                checkMaxNode = true;

            int curBase = this.nodes[curNode].base;
            int lastNode = this.nodes[curNode].check;
            if (i == len)
            {
                find = this.nodes[curNode].isTerminal;
                end = true;
                if (find)
                {
                    this.nodes[curNode].isTerminal = false;
                    this.nodes[curNode].data = null;
                }
            }

            if (curBase <= 0 && !find)
            {
                int index = -curBase;
                strTail = tailRetrieve(index);
                find = strTail.equals(str.substring(i));
                end = true;
                if (find)
                    if (this.nodes[curNode].isTerminal)
                    {
                        this.nodes[curNode].base = -tailPos;
                        this.tail.append(END_CHAR);
                        this.tailPos += 1;
                        this.nodes[curNode].isTerminal = false;
                        this.nodes[curNode].data = null;
                    }
                    else
                    {
                        this.nodes[curNode].check = ReducedTrieNode.UNDEFINED;
                        this.nodes[curNode].base = 0;
                        this.nodes[curNode].data = null;
                        do_back_delete(lastNode);
                    }
            }
            else if (!end)
            {
                int nextNode = curBase + this.charCodes[str.charAt(i)];
                if (this.nodes[nextNode].check != curNode)
                    end = true;
                else
                {
                    curNode = nextNode;
                    i++;
                }
            }
            else
                end = true;

        }
        // si borro el nodo que esta en la maxima posici��n del arreglo
        if (checkMaxNode)
        {
            // busco el nuevo maximo
            i = maxNode;
            while (i > 0 && this.nodes[i].check == ReducedTrieNode.UNDEFINED)
            {
                i--;
            }
            maxNode = i;
        }

        return find;
    }

    private void do_back_delete(int node)
    {
        // voy borrando hacia atras
        int last = node;
        while (node != ROOT_NODE && !this.nodes[node].isTerminal && !have_children(node))
        {
            last = this.nodes[node].check;
            this.nodes[node].check = MapTrieNode.UNDEFINED;
            this.nodes[node].base = 0;
            this.nodes[node].data  = null;
            node = last;
        }

        // si llege hasta el nodo raiz y no tiene hijos, entonces queda vacio
        if (node == ROOT_NODE && !have_children(node))
        {
            this.nodes[node].check = 0;
            this.nodes[node].base = 1;
            this.nodes[node].data  = null;
        }
    }

    /** Busca un lugar en el arreglo donde pueda poner nodos que se accedan con los
     * simbolos que estan que estan la lista.
     * @param list Listado de simbolos.
     * @return Devuelve la base con la que tiene que referenciar el nuevo nodo a
     * estos.
     */
    private int x_check(String list)
    {
        char[] chars = list.toCharArray();
        int q = 1, i = 0, t;
        boolean find = true;
        do
        {
            i = 0;
            find = true;
            while (i < chars.length && find == true)
            {
                t = q + this.charCodes[chars[i]];
                // control de maximo nodo tomado, para incrementar el tama��o
                if (t > this.maxNode)
                    this.maxNode = t;

                find = (this.nodes[t].check == MapTrieNode.UNDEFINED);
                i++;
            }
            if (find == false)
                q++;
        }
        while (!find);

        size_check();
        return q;
    }

    /** Verifica el indice del maximo y determina si se agranda o no el arreglo de nodos
     * Agranda s�� el mayor nodo + el maximo numero de codigo (256) (= cantidad de codigos)
     * supera o iguala a la cantidad.
     */
    private void size_check()
    {
        if (this.maxNode >= this.nodes.length - this.charCodes.length)
            increment_size(this.nodes.length * 3 / 2);
    }

    /** Obtiene todos los caracteres que salen de este nodo
     * @param node Nodo a investigar.
     * @return Simbolos que tienen un arco saliente de este nodo.
     */
    private String list(int node)
    {
        StringBuffer res = new StringBuffer(10);
        for (int i = 0; i < this.charCodes.length; i++)
        {
            int t = this.nodes[node].base + this.charCodes[i];
            if (this.nodes[t].check == node)
                res.append((char) i);
        }
        return res.toString();
    }

    /** Obtiene todos los caracteres que salen de este nodo
     * @param node Nodo a investigar.
     * @return Simbolos que tienen un arco saliente de este nodo.
     */
    private boolean have_children(int node)
    {
        boolean has = false;
        int i = 0;
        while (!has && i < this.charCodes.length)
        {
            int t = this.nodes[node].base + this.charCodes[i];
            if (this.nodes[t].check == node)
                has = true;
            i++;
        }
        return has;
    }

    /** Incrementa el tama��o del arreglo de nodos en una cantidad inc.
     *
     * @param inc Cantidad de nodos a agregar
     */
    private void increment_size(int inc)
    {
        int lenght = this.nodes.length;
        MapTrieNode[] temp = this.nodes;
        this.nodes = new MapTrieNode[lenght + inc];
        System.arraycopy(temp, 0, this.nodes, 0, lenght);
        for (int i = lenght; i < this.nodes.length; i++)
        {
            this.nodes[i] = new MapTrieNode();
        }
    }

    /** Obtiene de la cola de texto el sufijo que comienza en la posici��n index y 
     *  termina en el caracter de fin de palabra. Saltea escapeos.
     * 
     * @param index
     * @return Cadena de texto que comienza
     * @see MapTrie#END_CHAR
     */
    private String tailRetrieve(int index)
    {
        int end = index - 1;
        do
        {
            // salteo escapeos "\|"
            end = this.tail.indexOf(END_CHAR, end + 1);
            // termino cuando encuentro un '|' no escapeado. sin el '\' que le proceda
        }
        while (end > 0 && this.tail.charAt(end - 1) == ESCAPE_CHAR);
        return unescapeString(this.tail.substring(index, end));
    }

    /**
     * Obtiene de dos cadenas, el prefijo comun, y luego los dos sufijos exclusivos
     * de cada uno.
     * 
     * @param s1 Primera cadena
     * @param s2 Segunda cadena
     * @return Una arreglo de cadenas. 
     * El elemento 0 contiene el sufijo de s1
     * El elemento 1 contiene el sufijo de s1
     * El elemento 2 contiene el prefijo comun
     */
    private String[] separate(String s1, String s2)
    {
        String[] res = new String[3];
        int minLength = Math.min(s1.length(), s2.length());
        int i = 0;
        while ((i < minLength) && (s1.charAt(i) == s2.charAt(i)))
        {
            i++;
        }
        res[0] = s1.substring(i);
        res[1] = s2.substring(i);
        res[2] = s1.substring(0, i);
        return res;
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

    /** * Devuelve todos las cadenas insertadas.
     *
     * @return Arreglo de cadenas insertadas
     */
    public String[] getStrings()
    {
        Vector<String> res = new Vector<String>();
        get_strings_rec("", res, ROOT_NODE);
        String[] r = new String[res.size()];
        for (int i = 0; i < r.length; i++)
        {
            r[i] = res.get(i);
        }
        return r;
    }

    /** Limpia la basura de la cola de elementos disminuyendo el tamaño*/
    public void trim()
    {
        StringBuffer buffer = new StringBuffer(this.tail.length() / 2);
        copy_strings(ROOT_NODE, buffer);
        this.tail = buffer;
    }

    private void copy_strings(int node, StringBuffer buffer)
    {
        Vector<Integer> nodos = new Vector<Integer>(10);
        for (int i = 0; i < this.charCodes.length; i++)
        {
            if (this.nodes[node].base > 0)
            {
                int t = this.nodes[node].base + this.charCodes[i];
                if (this.nodes[t].check == node)
                    nodos.add(t);
            }
            else if (this.nodes[node].check != MapTrieNode.UNDEFINED)
            {
                String str = this.tailRetrieve(-this.nodes[node].base);
                str = escapeString(str);
                this.nodes[node].base = -(buffer.length());
                buffer.append(str + END_CHAR);
                i = this.charCodes.length;
            }

        }
        for (int i : nodos)
        {
            copy_strings(i, buffer);
        }
    }

    private void list_nodes_rec(StringBuffer buf, int node)
    {

        Vector<Integer> nodos = new Vector<Integer>(10);
        buf.append("De Nodo: " + node + ". isLeaf = " + this.nodes[node].isTerminal + " datos = " + this.nodes[node].data + "\n");
        for (int i = 0; i < this.charCodes.length; i++)
        {
            if (this.nodes[node].base > 0)
            {
                int t = this.nodes[node].base + this.charCodes[i];
                if (this.nodes[t].check == node)
                {
                    buf.append("   Con " + (char) i + " voy a nodo " + t + "\n");
                    nodos.add(t);
                }
            }
            else if (this.nodes[node].check != MapTrieNode.UNDEFINED)
            {
                buf.append("   De este nodo obtengo: " + this.tailRetrieve(-this.nodes[node].base) + "\n");
                i = this.charCodes.length;
            }

        }
        for (int i : nodos)
        {
            list_nodes_rec(buf, i);
        }
    }

    private void get_strings_rec(String common_prefix, Vector<String> res, int node)
    {
        for (int j = 0; j < this.charCodes.length; j++)
        {
            if (this.nodes[node].base > 0)
            {
                int t = this.nodes[node].base + this.charCodes[j];
                if (t > 0)
                    if (this.nodes[t].check == node)
                    {
                        if (this.nodes[t].isTerminal && this.nodes[t].base > 0)
                            res.add(common_prefix + (char) j + ", data = " + this.nodes[t].data);
                        get_strings_rec(common_prefix + (char) j, res, t);
                    }
            }
            else if (this.nodes[node].check != MapTrieNode.UNDEFINED)
            {
                res.add(common_prefix + this.tailRetrieve(-this.nodes[node].base) + ", data = " + this.nodes[node].data);
                j = this.charCodes.length; // ac�� termina
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.nodes.length; i++)
        {
            buf.append("Nodo[" + i + "] = (" + this.nodes[i].base + ", " + this.nodes[i].check + ", " + this.nodes[i].data + ", " + this.nodes[i].isTerminal + " )\n");
        }

        buf.append("\n Tail = " + tail);
        buf.append("\n TailPos = " + tailPos);
        buf.append("\n Max Nodo = " + maxNode);
        return buf.toString();
    }

    /** Devuelve una cadena de texto para mostrar los codigos asignados a cada caracter
     * 
     * @return
     */
    public String getCharCodes()
    {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.charCodes.length; i++)
        {
            buf.append("Caracter " + i + " ( " + (char) i + " ) -> " + this.charCodes[i] + "\n");
        }

        return buf.toString();
    }
    private static final int ASCII_a = 97;
    private static final int ASCII_A = 65;
    private static final int ASCII_CHARS = 26;
    private static final int ASCII_SYMBOL = 33; // Simbolos desde el ! hasta el @, incluyendo numeros
    private static final int ASCII_SYMBOL_LENGHT = 32;

    private void initCharCodes()
    {
        int i = 0, j = 1; // No puedo usar el codigo 0

        charCodes = new int[256];

        for (i = 0; i < this.charCodes.length; i++)
        {
            this.charCodes[i] = -1;
        }

        for (i = 0; i < ASCII_CHARS; i++)
        {
            charCodes[ASCII_a + i] = j++;
        }
        for (i = 0; i < ASCII_CHARS; i++)
        {
            charCodes[ASCII_A + i] = j++;
        }
        charCodes['á'] = j++;
        charCodes['é'] = j++;
        charCodes['í'] = j++;
        charCodes['ó'] = j++;
        charCodes['ú'] = j++;
        for (i = 0; i < ASCII_SYMBOL_LENGHT; i++)
        {
            charCodes[ASCII_SYMBOL + i] = j++;
        }
        charCodes[0] = j++;
        charCodes[' '] = j++;
        for (i = 0; i < this.charCodes.length; i++)
        {
            if (this.charCodes[i] == -1)
                this.charCodes[i] = j++;
        }
    }

    /** Escapeo la cadena de forma tal que si tiene | o \ les agrega un escape con
    con caracter \ */
    private String escapeString(String word)
    {
        StringBuffer res = new StringBuffer(100);
        int i = 0, l = word.length();
        while (i < l)
        {
            char c = word.charAt(i);
            if (c == END_CHAR_C)
                res.append(ESCAPE_CHAR);
            if (c == ESCAPE_CHAR)
                res.append(ESCAPE_CHAR);
            res.append(c);
            i++;
        }
        return res.toString();
    }

    /** Escapeo la cadena de forma tal que si tiene | o \ les agrega un escape con
    con caracter \ */
    private String unescapeString(String word)
    {
        StringBuffer res = new StringBuffer(word.length());
        int i = 0, l = word.length();
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

    public int size()
    {
        return this.size;
    }

    public boolean isEmpty()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean containsKey(Object key)
    {
        String str = (String) key;
        if (str == null)
            return false;

        return do_exist(str);
    }

    public boolean containsValue(Object value)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object get(Object key)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Object put(String str, Object data)
    {
        boolean inserted = true;
        if (str != null && !do_exist(str))
            do_insert(str,data);
        else
            inserted = false;
        return inserted;
    }

    public Object remove(Object key)
    {
        String str = (String) key;
        boolean delete = true;
        if (str != null && exists(str))
            do_delete(str);
        else
            delete = false;
        return delete;
    }

    public void putAll(Map<? extends String, ? extends Object> m)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Set<String> keySet()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<Object> values()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Set<Entry<String, Object>> entrySet()
    {
        throw new UnsupportedOperationException();
    }
}


