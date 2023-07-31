package domain.DATrie;

public class UnifiedSufixes
{

    private StringBuffer tail;

    public UnifiedSufixes(StringBuffer tail)
    {
        this.tail = tail;
    }

    /**  Agrega a la cola de un trie un sufijo **
     * Pre  Asume que el string viene con el fin de palabra
     **/
    public int addSuffix(String suffix)
    {
        int link = 0;

        //Busca si ya se encuentra
        link = this.tail.indexOf(suffix);
        if (link < 0)
        {
            //Si no est� lo agrega
            link = this.tail.length();
            this.tail.append(suffix);
        }

        return link;

    }

    /**  Agrega a una cola un sufijo **
     * Pre  Asume que el string viene con el fin de palabra
     **/
    static int addSuffix(StringBuffer tail, String suffix)
    {
        int link = 0;

        //Busca si ya se encuentra
        link = tail.indexOf(suffix);
        if (link < 0)
        {
            //Si no est� lo agrega
            link = tail.length();
            tail.append(suffix);
        }

        return link;

    }

    /** Recorrer el arbol cambiando los links **/
    static void uniffySuffixes(DescentTrie t)
    {
        String suffix = null;

        //Crear nueva cola
        StringBuffer tail = new StringBuffer();
        //Recorrer el arbol
        for (int i = 0; i < t.nodes.length; i++)
        {
            if (t.nodes[i].isLeaf())
            {
                //Obtener el sufijo
                suffix = DescentTrie.getSuffix(t.nodes[i].link, t.tail);
                //Agregar link a sufijo
                t.nodes[i].link = addSuffix(tail, suffix);
            }
        }//for
        //Asignar nueva cola
        t.tail = tail;
    }
}
