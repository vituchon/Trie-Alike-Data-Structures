/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package exec;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * Clase util para modelos de tablas que implementa una interfase para
 * para obtener elementos de la formación de datos del modelo y para poder 
 * actualizar la vista del mismo ante inserciones y borrados.
 */
public abstract class ExtendedAbstractTableModel extends AbstractTableModel implements TableModelListener
{
    public ExtendedAbstractTableModel ()
    {
        this.addTableModelListener (this);
    }
    
    /** Retorna en elemento <code>row</code> de la formación de datos del modelo que referencia */
    public abstract Object get (int row);

    /** Actualiza la vista de la tabla */
    public final void update ()
    {
        fireTableDataChanged();
    }
    
    public final void tableChanged (TableModelEvent e)
    {
        
    }
}
