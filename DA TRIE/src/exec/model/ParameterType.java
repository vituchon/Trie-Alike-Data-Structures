package exec.model;

public enum ParameterType
{

    TIME_ADD(0, "Agregado [MiliSeg]"),
    REAL_SPACE(1, "Espacio Real [Bytes]"),
    OPTIMAL_SPACE(2, "Espacio Óptimo [Bytes]"),
    TIME_SEARCH(3, "Búsqueda [MiliSeg]");
    final int index;
    final String name;

    private ParameterType(int index, String name)
    {
        this.index = index;
        this.name = name;
    }
}
