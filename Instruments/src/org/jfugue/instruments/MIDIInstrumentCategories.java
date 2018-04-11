/*
 * MIDIInstrumentCategories.java
 *
 * Created on 26 juli 2006, 23:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jfugue.instruments;

import org.musician.api.InstrumentCategory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Pierre Matthijs
 */
public class MIDIInstrumentCategories extends Children.Keys {
    
    private String[] instrumentCategories = new String[]{
        "Piano",
        "Chromatic",
        "Organ",
        "Guitar",
        "Bass",
        "Strings",
        "Ensemble",
        "Brass",
        "Reed",
        "Pipe",
        "Synth Lead",
        "Synth Pad",
        "Synth Effects",
        "Ethnic",
        "Percussive",
        "Sound Effects"};
    
    
    /**
     * Creates a new instance of MIDIInstrumentCategories
     */
    public MIDIInstrumentCategories() {
    }
    
    protected Node[] createNodes(Object key) {
        InstrumentCategory obj = (InstrumentCategory) key;
        AbstractNode result = new AbstractNode(new MIDIInstruments(obj), Lookups.singleton(obj));
        result.setDisplayName(obj.getInstrumentCategory());
        result.setIconBaseWithExtension("org/netbeans/modules/musician/resources/java-note-18.png");
        return new Node[] { result };
    }
    
    protected void addNotify() {
        super.addNotify();
        InstrumentCategory[] objs = new InstrumentCategory[instrumentCategories.length];
        for (int i = 0; i < objs.length; i++) {
            InstrumentCategory cat = new InstrumentCategory();
            cat.setInstrumentCategory(instrumentCategories[i]);
            objs[i] = cat;
        }
        setKeys(objs);
    }
    
}
