/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exec.model;

/**
 *
 * @author Victor
 */
public class Results implements Cloneable
{

    Estructure[] estructures;
    public Results ()
    {
        this.estructures = new Estructure[EstructureType.values().length];
        clean ();
    }

    public void clean ()
    {
        EstructureType[] e = EstructureType.values();
        for (int i = 0; i < this.estructures.length; i++)
        {
            this.estructures[i] = new Estructure(e[i].name);
            for (ParameterType param : ParameterType.values())
            {
                this.estructures[i].setParamValue(param,-1);
            }
        }
    }

    public void setValue (EstructureType estructure,ParameterType param,long value)
    {
        this.estructures[estructure.index].setParamValue(param, value);
    }

    public long getValue (EstructureType estructure,ParameterType param)
    {
        return this.estructures[estructure.index].getParamValue(param);
    }

    public long getValue (int row,int col)
    {
        return this.estructures[row].getParamValue(col);
    }
    
    @Override
    public Results clone ()
    {
        Results res = new Results();
        for (int i = 0; i < res.estructures.length; i++)
        {
            for (ParameterType param : ParameterType.values())
            {
                res.estructures[i].setParamValue(param,this.estructures[i].getParamValue(param));

            }
        }
        return res;
    }
}
