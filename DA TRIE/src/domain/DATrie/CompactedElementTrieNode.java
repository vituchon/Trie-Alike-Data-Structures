package domain.DATrie;

class CompactedElementTrieNode
{

    protected boolean isLeaf    = false;
    protected int link          = 0;
    protected char lcheck       = 0;

    public static char UNDEFINED = 0xFFFF;
    
    static long space()
    {
        return 5; // un entero [link] y un char [lcheck]
    }
}
