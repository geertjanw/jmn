package org.jfugue.guitar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.openide.util.ImageUtilities;

/**
 * CellRenderer for the GuitarJTable.
 * @author Johannes Reher
 */
public class GuitarCellRenderer extends JPanel implements TableCellRenderer
{
    private JLabel redDot;
    private Image bgImage;

    public GuitarCellRenderer()
    {
        //This dot is for selection of a cell
        String path = "org/jfugue/guitar/ball16.png";
        Image image = ImageUtilities.loadImage(path);
        redDot = new JLabel(new ImageIcon(image));
        redDot.setHorizontalAlignment(JLabel.CENTER);
        
        
        //preloading the image for the wooden pattern
        bgImage = ImageUtilities.loadImage("org/jfugue/guitar/wood128.png");
        
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        //TODO make a nice design for the guitar panel :-)
        if(column == 0)
        {
            //first column for the basic string note it's different designed
            setForeground(Color.red);
        } else {
            //TODO add string image with different stringwidth on the top of the cell.
            //TODO add white dots between the middlecells of the 3rd, 5th, 7th frets and so on..for each cell a half dot.
            setBackground(Color.DARK_GRAY);
        }
        
		
		//TODO add those graphics without conflicting the redDots
		if(hasDotTop(column,row))
		{
			//add a half white circle to the top
		} else if(hasDotBottom(column,row))
		{
			//add a half white circle to the bottom
		} else if(hasDotCenter(column,row))
		{
			//add a full white circle in the center
		}
		
        if(isSelected && hasFocus)
        {
            this.add(redDot);
        } else {
            this.remove(redDot);
        }
        return this;
    }
	
	private boolean hasDotTop(int x, int y)
	{
		return ((x == 3 && y == 2) ||
				(x == 5 && y == 2) ||
				(x == 7 && y == 2) ||
				(x == 9 && y == 2) ||
				(x == 15 && y == 2) ||
				(x == 17 && y == 2) ||
				(x == 19 && y == 2) ||
				(x == 21 && y == 2) 
				);
	}
	
	private boolean hasDotBottom(int x, int y)
	{
		return ((x == 3 && y == 3) ||
				(x == 5 && y == 3) ||
				(x == 7 && y == 3) ||
				(x == 9 && y == 3) ||
				(x == 15 && y == 3) ||
				(x == 17 && y == 3) ||
				(x == 19 && y == 3) ||
				(x == 21 && y == 3) 
				);
	}
	
	private boolean hasDotCenter(int x, int y)
	{
		return ((x == 12 && y == 1) ||
				(x == 12 && y == 4)
				);
	}

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //Resizing the wooden pattern to cellsize
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        
        BufferedImage scaledImage = (BufferedImage) bgImage;
        int pWidth = this.getWidth();
        int pHeight = this.getHeight();
        
        int iWidth = bgImage.getWidth(null);
        int iHeight = bgImage.getHeight(null);
        
        double scale = 1.0;
        if(iWidth > pWidth || iHeight > pHeight)
        {
            scale = 1/Math.min((double)iWidth/pWidth,(double)iHeight/pHeight);
        } else if(iWidth < pWidth && iHeight < pHeight)
        {
            scale = Math.max((double)pWidth/iWidth,(double)pHeight/iHeight);
        }
        
        double xPos = (pWidth - scale * iWidth)/2;
        double yPos = (pHeight - scale * iHeight)/2;
 
        AffineTransform affineTrans = AffineTransform.getTranslateInstance(xPos, yPos);
        affineTrans.scale(scale, scale);
        g2.drawRenderedImage(scaledImage, affineTrans);
    }
    
    
}
