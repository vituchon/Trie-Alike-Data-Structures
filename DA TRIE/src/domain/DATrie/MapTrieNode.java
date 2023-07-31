package domain.DATrie;

class MapTrieNode
{

    public static final int UNDEFINED = Integer.MIN_VALUE;
    public boolean isTerminal = false;
    public int base = 0;
    public int check = UNDEFINED;
    public Object data = null;

    static long space()
    {
        return 8; // dos enteros [base y check]
    }
}
