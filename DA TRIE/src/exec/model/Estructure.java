package exec.model;

public class Estructure
{
    private Parameter[] parameters;
    private String name;

    public Estructure (String name)
    {
        this.name = name;
        ParameterType p[] = ParameterType.values();
        this.parameters = new Parameter[p.length];
        for (int i = 0; i < this.parameters.length; i++)
        {
            this.parameters[i] = new Parameter(p[i].name);
            this.parameters[i].setValue(-1);
        }
    }


    public String getName()
    {
        return this.name;
    }

    public long getParamValue(ParameterType param)
    {
        return this.parameters[param.index].getValue();
    }

    public long getParamValue(int index)
    {
        return this.parameters[index].getValue();
    }

    public void setParamValue(ParameterType param, long value)
    {
        this.parameters[param.index].setValue(value);
    }
}
