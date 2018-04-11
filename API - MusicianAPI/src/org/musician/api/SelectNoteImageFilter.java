/*
 * SelectNoteImageFilter.java
 *
 * Created on April 10, 2007, 11:40 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.musician.api;

import java.awt.image.RGBImageFilter;
/**
 *
 * @author michal fapso
 */
public class SelectNoteImageFilter extends RGBImageFilter {
    
    /** Creates a new instance of SelectNoteImageFilter */
    public SelectNoteImageFilter() {
        // The filter's operation does not depend on the
        // pixel's location, so IndexColorModels can be
        // filtered directly.
        canFilterIndexColorModel = true;
    }
    public int filterRGB(int x, int y, int rgb) {
        return ((rgb & 0xff000000) | 0xff0000);
    }
}
