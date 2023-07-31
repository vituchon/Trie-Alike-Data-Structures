package exec.model;

public class Parameter
{
    private final String name;
    private long value;
    

    Parameter(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public long getValue()
    {
        return value;
    }

    public void setValue(long value)
    {
        this.value = value;
    }
}
