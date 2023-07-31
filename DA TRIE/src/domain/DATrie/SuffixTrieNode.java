package domain.DATrie;

class SuffixTrieNode
{

    public static final int UNDEFINED = Integer.MIN_VALUE;
    public boolean isTerminal = false;
    public int base = 0;
    public int check = UNDEFINED;
    public String suffix = null;

    long space()
    {
        // dos enteros [base] y [check] + el puntero a suffix [suffix] y su contenido (eventualmente)
        return 8 + 4 + ((suffix == null) ? 0 : suffix.length());
    }
}
