package domain.DATrie;

import java.util.Vector;

/**
 * Arbol Trie para almanecar cadenas de texto. 
 * NO se puede usar con el caracter "|" puesto que uso esta reservado, hasta
 * que se revise la implementación y se consiga desprenderse de esta condición.
 * @see ReducedTrie#END_CHAR
 */
public class SuffixTrie implements TrieTree
{
    private SuffixTrieNode[] nodes;
    private int[]      charCodes;
    private int        maxNode;

    /** Cantidad de nodos inicial */
    private static final int INIT_CAPACITY  = 5000;
    /** Nodo raiz */
    private static final int ROOT_NODE      = 0;
    /** Valor para marcar nodos indefinidos */
    
    /**
     * Constructor
     */
    public SuffixTrie ()
    {
        initCharCodes ();
        
        this.nodes = new SuffixTrieNode [INIT_CAPACITY];
        for (int i = 0;i < INIT_CAPACITY;i++)
        {
            this.nodes[i] = new SuffixTrieNode ();
        }
        this.nodes[ROOT_NODE].base  = 1;
        this.nodes[ROOT_NODE].check = 0;
        this.maxNode = 0;
    }
    
    /** Inserta una palabra en este trie, si no existe previamente.
     * 
     * @param str Palabra a insertar
     * @return true si fue insertado, sino retorna false.
     */
    public boolean insert (String str)
    {    
        boolean inserted = true;
        if (str != null && !str.isEmpty () && !exists(str))
            do_insert (str);
        else
            inserted = false;
        return inserted;
    }
     
    /** Borra una palabra en este trie, si existía previamente.
     * 
     * @param str Palabra a insertar
     * @return  true si fue borrado, sino retorna false.
     */
    public boolean delete (String str)
    {
        boolean delete = true;
        if (str != null && !str.isEmpty ()  && exists(str))
            do_delete (str);
        else
            delete = false;
        return delete;    
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
            int curBase = this.nodes[curNode].base;
            if (curBase <= 0) // si la base es igual o menor a cero, busco en el arreglo de colas
            {
                strTail = this.nodes[curNode].suffix;
                find = strTail != null && strTail.equals (str.substring (i));
                end = true;
            }
            else // sino, sigo recorriendo el trie
            {
                int nextNode = curBase + this.charCodes[str.charAt (i)];
                if (this.nodes[nextNode].check != curNode)
                    end = true;
                else
                {
                    i++;
                    curNode = nextNode;
                    if (i == str.length () && this.nodes[curNode].base > 0)
                        return this.nodes[curNode].isTerminal;
                }
            }
        }
        
        return find;
    }
    
    /** Calcula en forma aproximada el espacio ocupado en bytes de la estructura de datos */
    public long space ()
    {
        long bytes = 4; // tamaño de maxNode [maxNode]
        bytes += this.nodes.length * 4; // tamaño de los punteros de [nodes]
        for (SuffixTrieNode trieNode : nodes)
        {
            bytes += trieNode.space(); // tamaño de los contenidos de los punteros [nodes]
        }
        return bytes;
    }
    
    /** Calcula en forma aproximada el espacio ocupado en bytes de la estructura de datos */
    public long optimalSpace ()
    {
        // considero hasta el nodo máximo
        long bytes = 4; // tamaño de maxNode
        bytes += this.maxNode * 4;// tamaño de los punteros de nodes
        for (int i = 0;i < this.maxNode;i++)
            bytes += this.nodes[i].space();// tamaño de los contenidos de los punteros
        return bytes;
    } 
    
     private void do_insert(String str)
    {
        int curNode = ROOT_NODE, i = 0;
        boolean end = false;
        while (!end)
        {
            int curBase = this.nodes[curNode].base;
            if (curBase <= 0)
            {
                // INSERCION CON COLISION EN COLA
                insert_2(str, i, curNode, curBase);
                end = true;
            }
            else if (str.length() == i)
            {
                // INSERCION IMPLICITA
                //System.out.println ("TRIE. INSERC��ON IMPLICITA DE: " + str);
                this.nodes[curNode].isTerminal = true;
                end = true;
            }
            else
            {
                int nextNode = curBase + this.charCodes[str.charAt(i)];
                if (this.nodes[nextNode].check != curNode)
                    if (this.nodes[nextNode].check == ReducedTrieNode.UNDEFINED)
                    {
                        // INSERSION SIN COLISION
                        insert_1(str, i, curNode, nextNode);
                        end = true;
                    }
                    else
                    {
                        // INSERCI��N CON COLISION EN EL ARBOL
                        insert_3(str, i, curNode, nextNode);
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
     
    private void insert_1 (String str, int i, int curNode,int newNode)
    {
        //System.out.println ("TRIE. INSERCIÓN SIN COLISIÓN DE: " + str);
        String strTail = str.substring (i + 1);
        
        this.nodes[newNode].base    = -1;
        this.nodes[newNode].check   = curNode;
        this.nodes[newNode].isTerminal  = true;
        this.nodes[newNode].suffix  = strTail;
        
        if (newNode > maxNode)
            maxNode = newNode;
    }
    
    private void insert_2 (String str,int i,int curNode,int curBase)
    {
        //System.out.println ("TRIE. INSERCIÓN CON COLISIÓN EN COLA DE: " + str);
        String strTail = this.nodes[curNode].suffix;
        String[] sep = separate (strTail,str.substring (i));
        char[] commomPrefix = sep[2].toCharArray ();
        int q = 1,newNode = curNode,lastNode = curNode;
        // Inserto el prefijo comun como nodos (saco de la cola la parte comun)
        for (char c : commomPrefix)
        {
            q = x_check (String.valueOf (c));
            this.nodes[curNode].base   = q;
            this.nodes[curNode].isTerminal = false;
            this.nodes[curNode].suffix = null;
            lastNode = curNode;
            newNode = q + this.charCodes[c];
            this.nodes[newNode].check = curNode;
            this.nodes[newNode].isTerminal = false;
            curNode = newNode;
        }
        // ESBLESCO LOS NUEVOS TERMINALES
        // Si el nuevo string no comprende del todo  
        // ej: Ingreso badge y existiendo bachelor.
        if (sep[0].length () > 0 && sep[1].length () > 0)
        {
            char ch1 = sep[0].charAt (0);
            char ch2 = sep[1].charAt (0);
            q = x_check (ch1 + "" + ch2);
            int node1 = q + this.charCodes[ch1];
            int node2 = q + this.charCodes[ch2]; 

            this.nodes[newNode].base = q;
            this.nodes[newNode].isTerminal = false;
            this.nodes[newNode].suffix = null;

            sep[0] = sep[0].substring (1);
            sep[1] = sep[1].substring (1);
            
            this.nodes[node1].base   = -1;
            this.nodes[node1].check  = newNode;
            this.nodes[node1].isTerminal = true;
            this.nodes[node1].suffix = sep[0];
            
            this.nodes[node2].base   = -1;
            this.nodes[node2].check  = newNode;
            this.nodes[node2].isTerminal = true;
            this.nodes[node2].suffix = sep[1]; 
            
            // este nodo era la hoja apuntaba a la cadena con la cual colisono, como se lo
            // hace nodo más, deja de ser hoja y isTerminal se vuelve false para él.
            // en el ejemplo sería la "b" del bachelor.
            //this.nodes[origNode].isTerminal = false;
        }
        // Si el nuevo string comprende a uno que existe
        // ej: ingreso cadena, existiendo cad.
        else if (sep[1].length () > 0)
        {
            insert_2_bis(sep[1],curNode);
        } 
        // Si el nuevo string esta comprendido por uno que existe
        // ej: ingreso cad, existiendo cadena.
        else
        {   
            insert_2_bis(sep[0],curNode);
        }

    }

    private void insert_2_bis(String str, int curNode)
    {
        char ch = str.charAt(0);
        int q = x_check(String.valueOf(ch));
        int node2 = q + this.charCodes[ch];

        // el nodo actual pasa a ser terminal pero tambien referencia a otro posterior
        this.nodes[curNode].base = node2 - this.charCodes[ch];
        this.nodes[curNode].isTerminal = true;
        this.nodes[curNode].suffix = null;

        // se establece como terminal el nuevo nodo
        this.nodes[node2].base = -1;
        this.nodes[node2].check = curNode;
        this.nodes[node2].isTerminal = true;
        this.nodes[node2].suffix = str.substring(1);
    }

    private void insert_3 (String str,int i,int curNode,int colisionNode)
    {
        //System.out.println ("TRIE. INSERCIÓN CON COLISION DE INCONSISTENCIA DE: " + str);
        int tempNode1 = colisionNode,tempNode2;
        int lastNode = this.nodes[colisionNode].check;
        String chars_checknode_fail = list (lastNode);
        String strTail;
        
        int TEMP_BASE = this.nodes[lastNode].base;
        int q = x_check (chars_checknode_fail);
        this.nodes[lastNode].base = q;
        for (char ch : chars_checknode_fail.toCharArray ())
        {                              
            tempNode1 = TEMP_BASE + this.charCodes[ch];
            tempNode2 = this.nodes[lastNode].base + this.charCodes[ch];

            this.nodes[tempNode2].base = this.nodes[tempNode1].base;
            this.nodes[tempNode2].check = this.nodes[tempNode1].check;
            this.nodes[tempNode2].isTerminal = this.nodes[tempNode1].isTerminal;
            this.nodes[tempNode2].suffix = this.nodes[tempNode1].suffix;
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
            this.nodes[tempNode1].check = SuffixTrieNode.UNDEFINED;
            this.nodes[tempNode1].isTerminal = false;
            this.nodes[tempNode1].suffix = null;
        }

        strTail = str.substring (i);
        int temp_node = this.nodes[curNode].base + this.charCodes[strTail.charAt (0)];
        this.nodes[temp_node].base   = -1;
        this.nodes[temp_node].check  = curNode;
        this.nodes[temp_node].suffix = strTail.substring (1);
    }
    
    private void do_delete (String str)
    {
        int curNode = ROOT_NODE;
        int i = 0;
        boolean end = false,checkMaxNode = false;
        while (!end)
        {
            if (curNode == maxNode)
                checkMaxNode = true;
            
            int curBase = this.nodes[curNode].base;
            if (curBase <= 0) // si la base es igual o menor a cero, busco en el arreglo de colas
            {
                // borro un nodo hoja
                int lastNode = this.nodes[curNode].check;
                this.nodes[curNode].isTerminal = false;
                this.nodes[curNode].base = 0;
                this.nodes[curNode].check = SuffixTrieNode.UNDEFINED;
                this.nodes[curNode].suffix = null;
                do_back_delete (lastNode);
                end = true;
            }
            else // sino, sigo recorriendo el trie
            {
                // borro un nodo no hoja
                int nextNode = curBase + this.charCodes[str.charAt (i)];
                i++;
                curNode = nextNode;
                if (i == str.length () && this.nodes[curNode].base > 0)
                {
                    this.nodes[curNode].isTerminal = false;
                    this.nodes[curNode].suffix = null;
                    do_back_delete (curNode);
                    end = true;
                }
            }
        }

        // si borro el nodo que esta en la maxima posición del arreglo
        if (checkMaxNode)
        {
            // busco el nuevo maximo
            i = maxNode;
            while (i > 0 && this.nodes[i].check == SuffixTrieNode.UNDEFINED)
                i--;
            maxNode = i;
        }
    }
    
    private void do_back_delete (int node)
    {
        // voy borrando hacia atras
        int last = node;
        while (node != ROOT_NODE && !this.nodes[node].isTerminal && !have_children(node))
        {
            last = this.nodes[node].check;
            this.nodes[node].check = SuffixTrieNode.UNDEFINED;
            this.nodes[node].base  = 0;
            this.nodes[node].suffix = null;
            node = last;
        }
        
        // si llege hasta el nodo raiz y no tiene hijos, entonces queda vacio
        if (node == ROOT_NODE && !have_children (node))
        {
            this.nodes[node].check = 0;
            this.nodes[node].base  = 1;
        }
    }
    
    /** Busca un lugar en el arreglo donde pueda poner nodos que se accedan con los
     * simbolos que estan que estan la lista.
     * @param list Listado de simbolos.
     * @return Devuelve la base con la que tiene que referenciar el nuevo nodo a
     * estos.
     */
    private int x_check (String list)
    {
        char[] chars = list.toCharArray ();
        int q = 1, i = 0,t;
        boolean find = true;
        do
        {
            i = 0;
            find = true;
            while (i < chars.length && find == true)
            {
                t = q + this.charCodes[chars[i]];
                // control de maximo nodo tomado, para incrementar el tamaño
                if (t > this.maxNode)
                    this.maxNode = t;
                
                find = (this.nodes[t].check == SuffixTrieNode.UNDEFINED);
                i++;
            }
            if (find == false)
                q++;
        } while(!find);
        
        size_check ();
        return q;
    }
    
    /** Verifica el indice del maximo y determina si se agranda o no el arreglo de nodos
     * Agranda sí el mayor nodo + el maximo numero de codigo (256) (= cantidad de codigos)
     * supera o iguala a la cantidad.
     */
    private void size_check ()
    {
        if (this.maxNode >= this.nodes.length - this.charCodes.length)
        {
            increment_size (this.nodes.length * 3 / 2);
            //System.out.println ("TRIE: INCREMENTO TAMAÑO : " + (this.nodes.length * 3 / 2) + "\n");
        }
    }
    
    /** Obtiene todos los caracteres que salen de este nodo
     * @param node Nodo a investigar.
     * @return Simbolos que tienen un arco saliente de este nodo.
     */    
    private String list (int node)
    {
        StringBuffer res = new StringBuffer (10);
        for (int i=0;i < this.charCodes.length;i++)
        {
            int t = this.nodes[node].base + this.charCodes[i];
            if (this.nodes[t].check == node)
                res.append ((char)i);
        }
        return res.toString ();
    } 
    
    /** Obtiene todos los caracteres que salen de este nodo
     * @param node Nodo a investigar.
     * @return Simbolos que tienen un arco saliente de este nodo.
     */    
    private boolean have_children (int node)
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
    
    /** Incrementa el tamaño del arreglo de nodos en una cantidad inc.
     *
     * @param inc Cantidad de nodos a agregar
     */
    private void increment_size (int inc)
    {
        int lenght = this.nodes.length;
        SuffixTrieNode[] temp = this.nodes;
        this.nodes = new SuffixTrieNode [lenght + inc];
        System.arraycopy (temp, 0, this.nodes, 0,lenght);
        for (int i = lenght;i < this.nodes.length;i++)
            this.nodes[i] = new SuffixTrieNode ();
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
    private String[] separate (String s1,String s2)
    {
        String[] res = new String[3];
        int minLength = Math.min (s1.length (), s2.length ());
        int i = 0;
        while ((i < minLength) && (s1.charAt (i) == s2.charAt (i)))
            i++;
        res[0] = s1.substring (i);
        res[1] = s2.substring (i);
        res[2] = s1.substring (0,i);
        return res;
    }
   
    
    /** Obtiene un cadena de texto de un diagrama de navegación.
     * 
     * @return
     */
    public String list_nodes ()
    {
        StringBuffer res = new StringBuffer (255);
        list_nodes_rec (res,ROOT_NODE);
        return res.toString ();
    }    
   
    /** * Devuelve todos las cadenas insertadas.
     *
     * @return Arreglo de cadenas insertadas
     */
    public String[] getStrings ()
    {
        Vector<String> res = new Vector<String> ();
        get_strings_rec ("",res,ROOT_NODE);
        String[] r = new String [res.size ()];
        for (int i = 0;i < r.length;i++)
            r[i] = res.get (i);
        return r;
    }
    
    private void list_nodes_rec (StringBuffer buf,int node)
    {
        
        Vector<Integer> nodos = new Vector<Integer> (10);
        buf.append ("De Nodo: " + node + ". isLeaf = " + this.nodes[node].isTerminal + "\n");
        for (int i=0;i < this.charCodes.length;i++)
        {
            if (this.nodes[node].base > 0)
            {
                int t = this.nodes[node].base + this.charCodes[i];
                if (this.nodes[t].check == node)
                {
                    buf.append ("   Con " + (char)i + " voy a nodo " + t + "\n");
                    nodos.add (t);
                }                
            }
            else if (this.nodes[node].check != SuffixTrieNode.UNDEFINED)
            {
                buf.append ("   De este nodo obtengo: " + (this.nodes[node].suffix) + "\n");
                i = this.charCodes.length;
            }

        }
        for (int i : nodos)
        {
            list_nodes_rec (buf,i);
        }
    }       

    
    private void get_strings_rec (String common_prefix,Vector<String> res,int node)
    {
        for (int j=0;j < this.charCodes.length;j++)
        {
            if (this.nodes[node].base > 0)
            {
                int t = this.nodes[node].base + this.charCodes[j];
                if (t > 0)
                {
                    if (this.nodes[t].check == node)
                    {
                        if (this.nodes[t].isTerminal && this.nodes[t].base > 0)
                        {
                            res.add (common_prefix + (char)j);
                        }                    
                        get_strings_rec (common_prefix + (char)j,res,t);
                    }
                }            
            }
            else if (this.nodes[node].check != SuffixTrieNode.UNDEFINED)
            {
                res.add (common_prefix + this.nodes[node].suffix);
                j = this.charCodes.length; // acá termina
            }
        }
    }
    

    @Override
    public String toString ()
    {
        StringBuffer buf = new StringBuffer ();
        for (int i=0;i < this.nodes.length;i++)
            buf.append ("Nodo[" + i + "] = (" + this.nodes[i].base + ", " + this.nodes[i].check + ", " + this.nodes[i].isTerminal  +" )\n");

        buf.append ("\n Max Nodo = " + maxNode);
        return buf.toString ();        
    }
    
    /** Devuelve una cadena de texto para mostrar los codigos asignados a cada caracter
     * 
     * @return
     */
    public String getCharCodes ()
    {
        StringBuffer buf = new StringBuffer ();
        for (int i=0;i < this.charCodes.length;i++)
        {
            buf.append ("Caracter " + i + " ( " + (char)i + " ) -> " + this.charCodes[i] + "\n");
        }
        
        return buf.toString ();
    }
    
    
    private static final int ASCII_a = 97; 
    private static final int ASCII_A = 65;
    private static final int ASCII_CHARS = 26;
    private static final int ASCII_SYMBOL = 33; // Simbolos desde el ! hasta el @, incluyendo numeros
    private static final int ASCII_SYMBOL_LENGHT = 32;

    private void initCharCodes ()
    {
        int i = 0,j = 1; // No puedo usar el codigo 0
        
        charCodes = new int[256];
        
        for (i = 0; i < this.charCodes.length; i++)
                this.charCodes[i] = -1;
        
        for (i = 0; i < ASCII_CHARS; i++)
            charCodes[ASCII_a + i] = j++;
        for (i = 0; i < ASCII_CHARS; i++)
            charCodes[ASCII_A + i] = j++;
        charCodes['á'] = j++;
        charCodes['é'] = j++;
        charCodes['í'] = j++;
        charCodes['ó'] = j++;
        charCodes['ú'] = j++;
        for (i = 0;i < ASCII_SYMBOL_LENGHT;i++)
            charCodes[ASCII_SYMBOL + i] = j++;
        charCodes[0] = j++;
        charCodes[' '] = j++;
        for (i = 0; i < this.charCodes.length; i++)
            if (this.charCodes[i] == -1)
                this.charCodes[i] = j++;
    }
}


