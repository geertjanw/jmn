package org.jfugue.guitar;


import com.guigarage.jgrid.JGrid;
import com.guigarage.jgrid.renderer.GridCellRenderer;
import com.guigarage.jgrid.renderer.GridCellRendererManager;
import java.awt.Component;
import javax.swing.DefaultListModel;

/**
 *
 */
public class GuitarJGrid extends JGrid
{
    private DefaultListModel model = new DefaultListModel();
    private final int frets = 12;
    private final int strings = 6;
    /**
     * Default constructor for a guitar jgrid which has a preseted size.
     */
    public GuitarJGrid()
    {
        setModel(model);        
    }
    
    public void setGuitarUI()
    {
        
        for(int i=0; i < 12*6; i++)
        {
            model.addElement("("+(i/12)%6+","+i%12+")");
        }
        
        this.setHorizonztalMargin(0);
        this.setVerticalMargin(0);
        
        
        
        while(getUI().getColumnCount() > frets && getUI().getColumnCount() > 0)
        {
            System.out.println("Columns: "+getUI().getColumnCount());
            System.out.println("Size: "+getFixedCellDimension());
            setFixedCellDimension(getFixedCellDimension()+1);
        }
        
        while(getUI().getColumnCount() < frets && getUI().getColumnCount() < 20)
        {
            System.out.println("Columns: "+getUI().getColumnCount());
            System.out.println("Size: "+getFixedCellDimension());
            setFixedCellDimension(getFixedCellDimension()-1);
        }
    }
}
