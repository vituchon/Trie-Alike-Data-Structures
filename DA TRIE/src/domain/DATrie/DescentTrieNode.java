package domain.DATrie;

class DescentTrieNode
{

    public boolean isLeaf = false;
    public boolean split = false;
    protected boolean isTerminal = false;
    protected int link = 0;
    protected int check = 0;
    public static char UNDEFINED = 0xFFFF;

    static long space()
    {
        return 8; // dos enteros [base y check]
    }

    public int compareTo(Object node)
    {

        if (node == null)
        {
            return 1;
        }
        else
        {
            DescentTrieNode n = (DescentTrieNode) node;
            if (n.check == this.check)
            {
                return 0;
            }
            else if (n.check > this.check)
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }
    }

    public boolean isLeaf()
    {
        if (this.link < 0 && this.link != UNDEFINED)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    static public DescentTrieNode toDescentTrieNode(ReducedTrieNode rdn)
    {

        DescentTrieNode dtn = new DescentTrieNode();

        dtn.check = rdn.check;
        dtn.isLeaf = rdn.isLeaf();
        dtn.link = rdn.base;
        dtn.split = false;

        return dtn;

    }
}
