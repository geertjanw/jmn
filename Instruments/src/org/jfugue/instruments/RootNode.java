/*
 * RootNode.java
 *
 * Created on 29 juli 2006, 20:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jfugue.instruments;

import java.awt.Image;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.Utilities;

/**
 *
 * @author Pierre Matthijs
 */
public class RootNode extends AbstractNode {
    
    /** Creates a new instance of RootNode */
    public RootNode(Children children) {
        super(children);
    }
    
    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/netbeans/modules/musician/resources/right-rectangle.png");
    }

    public Image getOpenedIcon(int type) {
        return ImageUtilities.loadImage("org/netbeans/modules/musician/resources/down-rectangle.png");
    }
    
}
