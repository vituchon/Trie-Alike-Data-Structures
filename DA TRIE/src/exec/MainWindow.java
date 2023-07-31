/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainWindow.java
 *
 * Created on 19-ago-2009, 17:48:47
 */

package exec;

import exec.model.Builder;
import exec.model.Builder.NotifyEvent;
import exec.model.FileInformation;
import exec.model.Results;
import exec.model.ResultsTableModel;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;

/**
 *
 * @author Victor
 */
public class MainWindow extends javax.swing.JFrame
{
    Builder builder = null;
    ResultsTableModel resultsTableModel = new ResultsTableModel();
    String filename;
    boolean opEnabled = true;
    
    public MainWindow()
    {
        initComponents();
        initListeners();
        setup();
        disableOperation();
    }


    private void initListeners()
    {
        this.btnSelFile.addActionListener(new btnSelListener());
        this.menuItemExit.addActionListener(new menuItemExitListener());
        this.btnAction.addActionListener(new btnActionListener());
        this.btnSaveResults.addActionListener(new btnSaveListener());
        this.menuItemCloseResults.addActionListener(new menuItemCloseResultsListener());
    }

    private void setup ()
    {
        btnSaveResults.setEnabled(false);
    }

    public void disableOperation ()
    {
        this.btnAction.setText("Comenzar");
        this.pgState.setValue(0);
        this.pgState.setString("");
        if (opEnabled)
        {
            int tabCount = this.tabPanOperation.getTabCount();
            for (int i = 0;i < tabCount;i++)
            {
                Component comp = this.tabPanOperation.getComponentAt(i);
                if (comp == panProcess || comp == panResults)
                {
                    this.tabPanOperation.removeTabAt(i);
                    i--;
                    tabCount--;
                }
            }
            this.tabPanOperation.insertTab("Proceso",null, this.panNull,null,0);
            this.btnAction.setEnabled(false);
            this.txFile.setText("");
            opEnabled = false;
        }
    }

    public void enableOperation ()
    {
        this.btnAction.setText("Comenzar");
        this.pgState.setValue(0);
        this.pgState.setString("");
        if (!opEnabled)
        {
            int tabCount = this.tabPanOperation.getTabCount();
            for (int i = 0;i < tabCount;i++)
            {
                Component comp = this.tabPanOperation.getComponentAt(i);
                if (comp == panNull)
                {
                    this.tabPanOperation.removeTabAt(i);
                    i--;
                    tabCount--;
                }
            }
            this.tabPanOperation.insertTab("Proceso",null,this.panProcess,null,0);
            this.tabPanOperation.insertTab("Resultados",null,this.panResults,null,1);
            this.btnAction.setText("Comenzar");
            this.btnAction.setSelected(false);
            this.btnAction.setEnabled(true);
            opEnabled = true;
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFile = new javax.swing.JPanel();
        txFile = new javax.swing.JTextField();
        btnSelFile = new javax.swing.JButton();
        panOperation = new javax.swing.JPanel();
        pgState = new javax.swing.JProgressBar();
        btnAction = new javax.swing.JToggleButton();
        tabPanOperation = new javax.swing.JTabbedPane();
        panProcess = new javax.swing.JPanel();
        scPanState = new javax.swing.JScrollPane();
        txAreaState = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        panResults = new javax.swing.JPanel();
        scPanTable = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        labTitleSize = new javax.swing.JLabel();
        labTitleKeyWords = new javax.swing.JLabel();
        labSize = new javax.swing.JLabel();
        labKeyWords = new javax.swing.JLabel();
        labTitleTotalWords = new javax.swing.JLabel();
        labTotalWords = new javax.swing.JLabel();
        btnSaveResults = new javax.swing.JButton();
        panNull = new javax.swing.JPanel();
        lab1 = new javax.swing.JLabel();
        lab2 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuPrincipal = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuItemCloseResults = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        menuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arboles Trie");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        panFile.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Archivo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(0, 0, 0))); // NOI18N

        txFile.setEditable(false);
        txFile.setFont(new java.awt.Font("Tahoma", 0, 10));

        btnSelFile.setFont(new java.awt.Font("Tahoma", 0, 10));
        btnSelFile.setText("Elegir...");

        javax.swing.GroupLayout panFileLayout = new javax.swing.GroupLayout(panFile);
        panFile.setLayout(panFileLayout);
        panFileLayout.setHorizontalGroup(
            panFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFileLayout.createSequentialGroup()
                .addComponent(btnSelFile, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txFile, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
        );
        panFileLayout.setVerticalGroup(
            panFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFileLayout.createSequentialGroup()
                .addGroup(panFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelFile)
                    .addComponent(txFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        panOperation.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operación", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(0, 0, 0))); // NOI18N

        btnAction.setFont(new java.awt.Font("Tahoma", 0, 10));
        btnAction.setText("Comenzar");
        btnAction.setMaximumSize(new java.awt.Dimension(83, 21));
        btnAction.setMinimumSize(new java.awt.Dimension(83, 21));
        btnAction.setPreferredSize(new java.awt.Dimension(83, 21));

        tabPanOperation.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        scPanState.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txAreaState.setColumns(20);
        txAreaState.setEditable(false);
        txAreaState.setFont(new java.awt.Font("Tahoma", 0, 10));
        txAreaState.setRows(5);
        txAreaState.setFocusable(false);
        scPanState.setViewportView(txAreaState);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Estado:");

        javax.swing.GroupLayout panProcessLayout = new javax.swing.GroupLayout(panProcess);
        panProcess.setLayout(panProcessLayout);
        panProcessLayout.setHorizontalGroup(
            panProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panProcessLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scPanState, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panProcessLayout.setVerticalGroup(
            panProcessLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panProcessLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scPanState, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPanOperation.addTab("Proceso", panProcess);

        panResults.setEnabled(false);

        table.setFont(new java.awt.Font("Tahoma", 1, 11));
        table.setModel(resultsTableModel);
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        scPanTable.setViewportView(table);

        labTitleSize.setFont(new java.awt.Font("Tahoma", 1, 10));
        labTitleSize.setText("Tamaño Archivo :");

        labTitleKeyWords.setFont(new java.awt.Font("Tahoma", 1, 10));
        labTitleKeyWords.setText("Palabras Clave :");

        labSize.setFont(new java.awt.Font("Tahoma", 0, 10));
        labSize.setText("                ");
        labSize.setMaximumSize(new java.awt.Dimension(87, 13));
        labSize.setMinimumSize(new java.awt.Dimension(87, 13));
        labSize.setPreferredSize(new java.awt.Dimension(87, 13));

        labKeyWords.setFont(new java.awt.Font("Tahoma", 0, 10));
        labKeyWords.setText("          ");

        labTitleTotalWords.setFont(new java.awt.Font("Tahoma", 1, 10));
        labTitleTotalWords.setText("Palabras Totales :");

        labTotalWords.setFont(new java.awt.Font("Tahoma", 0, 10));
        labTotalWords.setText("          ");

        btnSaveResults.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnSaveResults.setText("Guardar en nueva pestaña");

        javax.swing.GroupLayout panResultsLayout = new javax.swing.GroupLayout(panResults);
        panResults.setLayout(panResultsLayout);
        panResultsLayout.setHorizontalGroup(
            panResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panResultsLayout.createSequentialGroup()
                        .addGroup(panResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labTitleKeyWords)
                            .addComponent(labTitleSize))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labKeyWords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labTitleTotalWords)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labTotalWords, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSaveResults))
                    .addComponent(scPanTable, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE))
                .addContainerGap())
        );
        panResultsLayout.setVerticalGroup(
            panResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labTitleKeyWords)
                    .addComponent(labKeyWords)
                    .addComponent(labTitleTotalWords)
                    .addComponent(labTotalWords)
                    .addComponent(btnSaveResults))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labTitleSize)
                    .addComponent(labSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scPanTable, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabPanOperation.addTab("Resultados", panResults);

        lab1.setFont(new java.awt.Font("Tahoma", 1, 10));
        lab1.setText("No hay archivo seleccionado");

        lab2.setFont(new java.awt.Font("Tahoma", 0, 10));
        lab2.setText("Para habilitar el panel de operación se tiene que seleccionar un archivo");

        javax.swing.GroupLayout panNullLayout = new javax.swing.GroupLayout(panNull);
        panNull.setLayout(panNullLayout);
        panNullLayout.setHorizontalGroup(
            panNullLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panNullLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panNullLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lab1)
                    .addComponent(lab2))
                .addContainerGap(250, Short.MAX_VALUE))
        );
        panNullLayout.setVerticalGroup(
            panNullLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panNullLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lab1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lab2)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        tabPanOperation.addTab("", panNull);

        javax.swing.GroupLayout panOperationLayout = new javax.swing.GroupLayout(panOperation);
        panOperation.setLayout(panOperationLayout);
        panOperationLayout.setHorizontalGroup(
            panOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOperationLayout.createSequentialGroup()
                .addComponent(btnAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pgState, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
            .addGroup(panOperationLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(tabPanOperation, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE))
        );
        panOperationLayout.setVerticalGroup(
            panOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOperationLayout.createSequentialGroup()
                .addGroup(panOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pgState, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabPanOperation, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
        );

        menuPrincipal.setText("Principal");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Importar Resultados a Excel...");
        menuPrincipal.add(jMenuItem2);

        menuItemCloseResults.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        menuItemCloseResults.setText("Cerrar todos los resultados");
        menuPrincipal.add(menuItemCloseResults);
        menuPrincipal.add(jSeparator1);

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemExit.setText("Salir");
        menuPrincipal.add(menuItemExit);

        menuBar.add(menuPrincipal);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panFile, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panOperation, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnAction;
    private javax.swing.JButton btnSaveResults;
    private javax.swing.JButton btnSelFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lab1;
    private javax.swing.JLabel lab2;
    private javax.swing.JLabel labKeyWords;
    private javax.swing.JLabel labSize;
    private javax.swing.JLabel labTitleKeyWords;
    private javax.swing.JLabel labTitleSize;
    private javax.swing.JLabel labTitleTotalWords;
    private javax.swing.JLabel labTotalWords;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItemCloseResults;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenu menuPrincipal;
    private javax.swing.JPanel panFile;
    private javax.swing.JPanel panNull;
    private javax.swing.JPanel panOperation;
    private javax.swing.JPanel panProcess;
    private javax.swing.JPanel panResults;
    private javax.swing.JProgressBar pgState;
    private javax.swing.JScrollPane scPanState;
    private javax.swing.JScrollPane scPanTable;
    private javax.swing.JTabbedPane tabPanOperation;
    private javax.swing.JTable table;
    private javax.swing.JTextArea txAreaState;
    private javax.swing.JTextField txFile;
    // End of variables declaration//GEN-END:variables


    // End of variables declaration

    class btnSelListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            /*directorio del archivos del sistema System.getProperty ("user.dir")*/
            JFileChooser fc = new JFileChooser ("./files");

            // Show open dialog; this method does not return until the dialog is closed
            fc.showOpenDialog (MainWindow.this);
            File inputFile = fc.getSelectedFile ();
            if (inputFile != null)
            {
                enableOperation();
                MainWindow.this.txFile.setText(inputFile.getPath());
                filename = inputFile.getName();
            }
            else
            {
                filename = "";
                disableOperation();
            }
        }
    }

    class menuItemExitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            MainWindow.this.setVisible (false);
            MainWindow.this.dispose ();
            System.exit (0);
        }
    }

    class menuItemCloseResultsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int tabCount = MainWindow.this.tabPanOperation.getTabCount();
            for (int i = 0;i < tabCount;i++)
            {
                Component comp = MainWindow.this.tabPanOperation.getComponentAt(i);
                if (comp != panProcess && comp != panResults && comp != panNull)
                {
                    MainWindow.this.tabPanOperation.removeTabAt(i);
                    i--;
                    tabCount--;
                }
            }
        }
    }

    class btnActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JToggleButton btn = MainWindow.this.btnAction;
            if (btn.isSelected())
            {
                btn.setText ("Cancelar");
                builder = new Builder(new actionTaskListener(),txFile.getText());
                builder.start();
            }
            else
            {
                btn.setEnabled(false);
                btn.setText("Deteniendo");
                builder.abort();
            }
        }
    }


    private JPanel clonePanelResults ()
    {
        JPanel res                  = new JPanel ();
        JScrollPane _scPanTable     = new JScrollPane();
        Font fntBold                = new Font("Tahoma", 1, 10);
        Font fntSimple              = new Font("Tahoma", 0, 10);
        Font fntTable               = new Font("Tahoma", 1, 11);
        JTable _table               = new JTable();
        JLabel _labTitleSize        = new JLabel ();
        JLabel _labTitleKeyWords    = new JLabel ();
        JLabel _labTitleTotalWords  = new JLabel ();
        JLabel _labSize             = new JLabel ();
        JLabel _labKeyWords         = new JLabel ();
        JLabel _labTotalWords       = new JLabel ();
        Results _results = builder.getResults().clone();
        ResultsTableModel _resultsTableModel = new ResultsTableModel(_results);

        res.setEnabled(false);

        _table = new JTable ();
        _table.setFont(fntTable);
        _table.setModel(_resultsTableModel);
        _table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        _scPanTable.setViewportView(_table);

        _labTitleSize.setFont(fntBold);
        _labTitleSize.setText("Tamaño Archivo :");

        _labTitleKeyWords.setFont(fntBold);
        _labTitleKeyWords.setText("Palabras Clave :");

        _labSize.setFont(fntSimple);
        _labSize.setMaximumSize(new java.awt.Dimension(87, 13));
        _labSize.setMinimumSize(new java.awt.Dimension(87, 13));
        _labSize.setPreferredSize(new java.awt.Dimension(87, 13));
        _labSize.setText(labSize.getText());

        _labKeyWords.setFont(fntSimple);
        _labKeyWords.setMaximumSize(new java.awt.Dimension(87, 13));
        _labKeyWords.setMinimumSize(new java.awt.Dimension(87, 13));
        _labKeyWords.setPreferredSize(new java.awt.Dimension(87, 13));
        _labKeyWords.setText(labKeyWords.getText());

        _labTitleTotalWords.setFont(fntBold);
        _labTitleTotalWords.setText("Palabras Totales :");

        _labTotalWords.setFont(fntSimple);
        _labTotalWords.setMaximumSize(new java.awt.Dimension(87, 13));
        _labTotalWords.setMinimumSize(new java.awt.Dimension(87, 13));
        _labTotalWords.setPreferredSize(new java.awt.Dimension(87, 13));
        _labTotalWords.setText(labTotalWords.getText());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(res);
        res.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(_labTitleKeyWords)
                            .addComponent(_labTitleSize))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(_labKeyWords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(_labSize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(_labTitleTotalWords)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(_labTotalWords, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addComponent(_scPanTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_labTitleKeyWords)
                    .addComponent(_labKeyWords)
                    .addComponent(_labTitleTotalWords)
                    .addComponent(_labTotalWords)
                    )
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(_labTitleSize)
                    .addComponent(_labSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(_scPanTable, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );
        return res;
    }

    class btnSaveListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            JPanel panel = clonePanelResults ();
            tabPanOperation.addTab("[RUN] " + filename, panel);
            tabPanOperation.setTabComponentAt(tabPanOperation.getTabCount()-1,new ButtonTabComponent(tabPanOperation));
        }
    }

    class actionTaskListener implements Builder.BuilderListener
    {
        public void notifyEvent(NotifyEvent event, Date date, long interval, String descrip,Object data)
        {
            switch (event)
            {
                case START:
                    btnSaveResults.setEnabled(false);
                    txAreaState.append("---------Proceso Iniciado---------\n");
                    resultsTableModel.setResults(builder.getResults());
                    resultsTableModel.update();
                    break;
                case DATA_RETRIEVED:
                    FileInformation fi = (FileInformation) data;
                    
                    long keyWords = fi.getKeyWords();
                    if (keyWords == -1)
                        labKeyWords.setText("Desconocido");
                    else
                        labKeyWords.setText(String.valueOf(keyWords));

                    labTotalWords.setText(String.valueOf(fi.getTotalWords()));

                    float kb = fi.getSize() / 1024f;
                    if (kb > 1)
                        labSize.setText(String.valueOf(kb) + " KB");
                    else
                        labSize.setText(String.valueOf(fi.getSize()) + " Bytes");
                    break;
                case WORKING:
                    txAreaState.append(descrip + "...\n");
                    resultsTableModel.update();
                    break;
                case ABORTED:
                    pgState.setString("Abortado : " + descrip);
                    btnAction.setText("Comenzar");
                    btnAction.setEnabled(true);
                    break;
                case END:
                    btnSaveResults.setEnabled(true);
                    btnAction.setSelected(false);
                    btnAction.setText("Reiniciar");
                    txAreaState.append("---------Proceso Finalizado---------\n");
                    resultsTableModel.update();
                    break;
            }
        }

        public void notifyInit(int total)
        {
            pgState.setMaximum(total);
            pgState.setValue(0);
            pgState.setStringPainted(true);
            pgState.setString(" 0 / " + total);
        }

        public void notifyProgress(int step)
        {
            pgState.setValue(step);
            pgState.setString(step+ " / " + pgState.getMaximum());
        }

    }

}
