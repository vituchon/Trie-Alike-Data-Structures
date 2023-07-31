package exec.model;

import exec.*;


public class ResultsTableModel extends ExtendedAbstractTableModel
{
    Results results;

    public ResultsTableModel ()
    {
        this.results = null;
        this.addTableModelListener(this);
    }

    public ResultsTableModel (Results results)
    {
        this.results = results;
        this.addTableModelListener(this);
    }

    public void setResults (Results results)
    {
        this.results = results;
    }
    
    public int getColumnCount() 
    {
        return EstructureType.values().length + 1;
    }
    
    public int getRowCount() 
    {
        return ParameterType.values().length;
    }

    
    @Override
    public String getColumnName(int col) 
    {
        if (col == 0)
            return "Parametros";
        else
            return EstructureType.values()[col - 1].name;
    }


    public Object getValueAt(int row, int col) 
    {   
        switch (col)
        {
            case 0:
            {
                return ParameterType.values()[row].name;
            }
            default:
            {
                long value = -1;
                if (results != null)
                    value = results.getValue(col-1,row);
                
                if (value == -1)
                    return "-";
                else
                    return value;
            }
        }
    }
    
    @Override
    public Class getColumnClass(int c) 
    {
        return java.lang.String.class;
    }
   
    @Override
    public boolean isCellEditable (int row, int col) 
    {
        return false;
    }

    
    @Override
    public Object get (int row)
    {
        return null;
    }
    
}