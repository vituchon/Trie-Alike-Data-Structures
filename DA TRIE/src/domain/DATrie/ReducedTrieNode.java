package domain.DATrie;

class ReducedTrieNode
{

    public static final int UNDEFINED = Integer.MIN_VALUE;
    public boolean isTerminal = false;
    public int base = 0;
    public int check = UNDEFINED;

    static long space()
    {
        return 8; // dos enteros [base y check]
    }

    public boolean isLeaf()
    {
        if (this.base < 0 && this.base != UNDEFINED)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
