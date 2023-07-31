package exec.model;

public enum EstructureType
{

    REDUCED_TRIE(0,"Reduced Trie"),
    DESCENT_TRIE(1,"Descent Trie"),
    SUFFIX_TRIE (2,"Suffix Trie"),
    LIST_TRIE(3,"List Trie"),
    INDEXED_ARRAY(4,"Indexed Array");
    final int index;
    final String name;

    private EstructureType(int index, String name)
    {
        this.index = index;
        this.name = name;
    }
}
