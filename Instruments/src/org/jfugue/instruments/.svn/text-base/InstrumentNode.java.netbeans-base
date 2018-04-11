/*
 * InstrumentNode.java
 *
 * Created on 27 juli 2006, 18:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.jfugue.instruments;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.jfugue.Pattern;
import org.jfugue.Player;
import org.musician.api.CentralLookup;
import org.musician.api.Instrument;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Pierre Matthijs
 */
public class InstrumentNode extends AbstractNode {

    private Instrument instrument;

    /**
     * Creates a new instance of InstrumentNode
     */
    public InstrumentNode(Instrument key) {
        super(Children.LEAF);
        this.instrument = key;
    }

    public Image getIcon(int type) {
        return ImageUtilities.loadImage("org/netbeans/modules/musician/resources/ball16.png");
    }

    @Override
    public Action[] getActions(boolean popup) {
        return new Action[]{new SelectAction(), new PlaySample()};
    }

    @Override
    public Action getPreferredAction() {
        return new SelectAction();
    }

    private class SelectAction extends AbstractAction {

        public SelectAction() {
            putValue(NAME, "Select");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CentralLookup cl = CentralLookup.getDefault();
            Collection<? extends Instrument> all = cl.lookupAll(Instrument.class);
            for (Instrument instrument : all) {
                cl.remove(instrument);
            }
//            cl.lookup(Score.class);
            cl.add(instrument);
        }
    }

    private class PlaySample extends AbstractAction {

        public PlaySample() {
            putValue(NAME, "Sample");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Player player = new Player();
            Pattern pattern = new Pattern("I[" + instrument.getJfugueDescription() + "] " + "C5h E5h G5h C6h");
            player.play(pattern);
            player.close();
        }
    }
}
