package org.jfugue.guitar;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Johannes Reher
 */
public class GuitarJTable extends JTable {

    private DefaultTableModel model = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private GuitarTopComponent guitarTopComponent;
    private GuitarJTable table = this;
    private final int frets = 15;
    private final int strings = 6;
    private final GuitarSelectionListener selectionListener;

    public GuitarJTable(GuitarTopComponent gtc, InstanceContent ic) {
        guitarTopComponent = gtc;
        selectionListener = new GuitarSelectionListener(this, ic);
        setModel(model);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                table.setRowHeight(table.getHeight() / strings);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
                table.setRowHeight(table.getHeight() / strings);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });

        initializeGuitar();

    }
    
//    Lookup.Result<SingleStave> allStaves;
//
//    @Override
//    public void addNotify() {
//        super.addNotify();
//    }
//
//    @Override
//    public void removeNotify() {
//        super.removeNotify();
//    }

    public GuitarTopComponent getGuitarTopComponent() {
        return guitarTopComponent;
    }

    public int getFrets() {
        return frets;
    }

    public int getStrings() {
        return strings;
    }

    private void initializeGuitar() {
        table.setDefaultRenderer(Object.class, new GuitarCellRenderer());
        for (int i = 0; i < frets; i++) {
            model.addColumn("");
        }
        String[] row = {"", "", "", "", "", ""};
        for (int i = 0; i < strings; i++) {
            model.addRow(row);
        }

        //autoresizemode off, because need to change the column width
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowMargin(0);
        table.setShowGrid(false);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        this.getColumnModel().getColumn(0).setPreferredWidth(10);
        this.getSelectionModel().addListSelectionListener(selectionListener);
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    @Override
    public Border getBorder() {
        Border b = BorderFactory.createLineBorder(Color.GRAY, 0);
        return b;
//        return new Border() {
//
//            @Override
//            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
//            {
//                
//            }
//
//            @Override
//            public Insets getBorderInsets(Component c)
//            {
//                return new Insets(0,0,0,0);
//            }
//
//            @Override
//            public boolean isBorderOpaque()
//            {
//                return true;
//            }
//        };
    }
}
