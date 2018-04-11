/*
 * Score.java
 *
 * Created on 25 augustus 2006, 16:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.musician.api;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.HashMap;
import org.musician.api.Instrument;
import org.musician.api.InvalidNoteException;
import org.openide.windows.WindowManager;

/**
 *
 * @author Pierre Matthijs
 */
public class Score {

    protected String title;
    public static final String PROP_TITLE = "title";

    /**
     * Get the value of title
     *
     * @return the value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the value of title
     *
     * @param title new value of title
     */
    public void setTitle(String title) {
        String oldTitle = this.title;
        this.title = title;
        propertyChangeSupport.firePropertyChange(PROP_TITLE, oldTitle, title);
    }
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    private char clef = 'G';
    private int        tempo        = 240;    // pulses per quarter
    private int        volume       = 16383;  // maximum value
    private String     keySignature = "";
    private int        rythmCount   = 4;
    private int        rythmUnit    = 4;
    
    private int        keySignatureSharpsCount = 0;
    private int        keySignatureFlatsCount = 0;
    
    private List<String> scoreContent = new ArrayList<String>();
    
    private HashMap<String,Integer> note2value = new HashMap<String,Integer>();
//    private HashMap<Integer,String> value2note = new HashMap<Integer,String>();
    private String [] sharpNotesSequence = {"fis","cis","gis","dis","ais","eis","bis"};
    private String [] flatNotesSequence = {"bb","es","as","des","ges","ces","fes"};
    private String [] scaleC =      {"c","d","e","f","g","a","b"};
    private String [] scaleG =      {"c","d","e","fis","g","a","b"};
    private String [] scaleD =      {"cis","d","e","fis","g","a","b"};
    private String [] scaleA =      {"cis","d","e","fis","gis","a","b"};
    private String [] scaleE =      {"cis","dis","e","fis","gis","a","b"};
    private String [] scaleB =      {"cis","dis","e","fis","gis","ais","b"};
    private String [] scaleFis =    {"cis","dis","eis","fis","gis","ais","b"};
    private String [] scaleCis =    {"cis","dis","eis","fis","gis","ais","bis"};
    private String [] scaleF =      {"c","d","e","f","g","a","bb"};
    private String [] scaleBb =     {"c","d","es","f","g","a","bb"};
    private String [] scaleEb =     {"c","d","es","f","g","as","bb"};
    private String [] scaleAb =     {"c","des","es","f","g","as","bb"};
    private String [] scaleDb =     {"c","des","es","f","ges","as","bb"};
    private String [] scaleGb =     {"ces","des","es","f","ges","as","bb"};
    private String [] scaleCb =     {"ces","des","es","fes","ges","as","bb"};
    
    private String [] scale;
    
    private Vector<Integer> nativeNoteValues = new Vector<Integer>();
    private Vector<Integer> restoreNoteValues = new Vector<Integer>();
    private Vector<Integer> alteredNoteValues = new Vector<Integer>(); // according to sharpNotesSequence or flatNotesSequence
    
    private Vector<Note> notes = new Vector<Note>();
    
    private Vector<String> sharpKeySignatures = new Vector<String>();
    private Vector<String> flatKeySignatures = new Vector<String>();

    /** Creates a new instance of Score */
    public Score() {
        initNote2ValueVector();
        initSharpKeySignatureVector();
        initFlatKeySignatureVector();
    }

    private void initNote2ValueVector() {
        note2value.put("c",   0);
        note2value.put("cis", 1);
        note2value.put("des", 1);
        note2value.put("d",   2);
        note2value.put("dis", 3);
        note2value.put("es",  3);
        note2value.put("e",   4);
        note2value.put("fes", 4);
        note2value.put("eis", 5);
        note2value.put("f",   5);
        note2value.put("fis", 6);
        note2value.put("ges", 6);
        note2value.put("g",   7);
        note2value.put("gis", 8);
        note2value.put("as",  8);
        note2value.put("a",   9);
        note2value.put("ais", 10);
        note2value.put("bb",  10);
        note2value.put("b",   11);        
    }

    private void initSharpKeySignatureVector() {
        sharpKeySignatures.addElement(new String("C "));
        sharpKeySignatures.addElement(new String("G "));
        sharpKeySignatures.addElement(new String("D "));
        sharpKeySignatures.addElement(new String("E ")); 
        sharpKeySignatures.addElement(new String("A "));
        sharpKeySignatures.addElement(new String("B "));
        sharpKeySignatures.addElement(new String("Fi"));
        sharpKeySignatures.addElement(new String("Ci"));
    }
    
    private void initFlatKeySignatureVector() {
        flatKeySignatures.addElement(new String("F "));
        flatKeySignatures.addElement(new String("Bb"));
        flatKeySignatures.addElement(new String("Eb"));
        flatKeySignatures.addElement(new String("Ab"));
        flatKeySignatures.addElement(new String("Db"));
        flatKeySignatures.addElement(new String("Gb"));
        flatKeySignatures.addElement(new String("Cb"));
    }
    
    public Vector<String> getSharpKeySignatureVector() {
        return sharpKeySignatures;
    }
    
    public Vector<String> getFlatKeySignatureVector() {
        return flatKeySignatures;
    }
    
    public List<String> getScoreContent() {
        return scoreContent;
    }
    
    public void addCommand(String command) {
        scoreContent.add(command);
//        commands_instance.displayScore(this);
    }
    
    public Vector<Note> getNotes() {
        return notes;
    }
    
    public void removeNote(Note note) {
        notes.remove(note);
    }
    
    public void addNote(Note note){
        notes.add(note);
        addCommand(note.toString());
    }
    
    public void setInstrument(Instrument instrument){
        addCommand("I[" + instrument.getJfugueDescription() + "]");
    }
    
    public void setScoreContent(List<String> scoreContent) {
        this.scoreContent = scoreContent;
    }
    
    public char getClef() {
        return clef;
    }
    
    public void setClef(char clef) {
        this.clef = clef;
    }
    
    public int getTempo() {                     // returned as quarters per minute
        return tempo;
    }
    
    public void setTempo(int tempo) {
        this.tempo = tempo;
        addCommand("T" + this.tempo);
    }
    
    public int getVolume() {                    // returned as percent of the maximum
        return (volume/16383)*100;
    }
    
    public void setVolume(int volume) {
        this.volume = (volume/100)*16383;
        addCommand("X[Volume]=" + this.volume);
    }
    
    public String getKeySignature() {
        return keySignature;
    }
    // c d e f g a b
    // 0 1 2 3 4 5 6
    //{0,2,4,5,7,9,11}
    public void setKeySignature(String keySignature) {
        this.keySignature = keySignature;
        
        keySignatureSharpsCount = 0;
        keySignatureFlatsCount = 0;
        scale = scaleC;
        if (keySignature.compareTo("") == 0) {
        }
        else if (keySignature.substring(0,2) == "C ") {
            keySignatureSharpsCount = 0;
            scale = scaleC;
        // sharps
        } else if (keySignature.substring(0,2).compareTo("G ") == 0) {
            keySignatureSharpsCount = 1;
            scale = scaleG;
        } else if (keySignature.substring(0,2).compareTo("D ") == 0) {
            keySignatureSharpsCount = 2;
            scale = scaleD;
        } else if (keySignature.substring(0,2).compareTo("A ") == 0) {
            keySignatureSharpsCount = 3;
            scale = scaleA;
        } else if (keySignature.substring(0,2).compareTo("E ") == 0) {
            keySignatureSharpsCount = 4;
            scale = scaleE;
        } else if (keySignature.substring(0,2).compareTo("B ") == 0) {
            keySignatureSharpsCount = 5;
            scale = scaleB;
        } else if (keySignature.substring(0,4).compareTo("Fis ") == 0) {
            keySignatureSharpsCount = 6;
            scale = scaleFis;
        } else if (keySignature.substring(0,4).compareTo("Cis ") == 0) {
            keySignatureSharpsCount = 7;
            scale = scaleCis;
        // flats
        } else if (keySignature.substring(0,2).compareTo("F ") == 0) {
            keySignatureFlatsCount = 1;
            scale = scaleF;
        } else if (keySignature.substring(0,3).compareTo("Bb ") == 0) {
            keySignatureFlatsCount = 2;
            scale = scaleBb;
        } else if (keySignature.substring(0,3).compareTo("Eb ") == 0) {
            keySignatureFlatsCount = 3;
            scale = scaleEb;
        } else if (keySignature.substring(0,3).compareTo("Ab ") == 0) {
            keySignatureFlatsCount = 4;
            scale = scaleAb;
        } else if (keySignature.substring(0,3).compareTo("Db ") == 0) {
            keySignatureFlatsCount = 5;
            scale = scaleDb;
        } else if (keySignature.substring(0,3).compareTo("Gb ") == 0) {
            keySignatureFlatsCount = 6;
            scale = scaleGb;
        } else if (keySignature.substring(0,3).compareTo("Cb ") == 0) {
            keySignatureFlatsCount = 7;
            scale = scaleCb;
        }
        //System.err.println("keySignatureFlatsCount: "+keySignatureFlatsCount+" keySignatureSharpsCount:"+keySignatureSharpsCount+" substr:"+keySignature.substring(0,2));
        
        nativeNoteValues.clear();
        for (int i=0; i<scale.length; i++) {
            nativeNoteValues.add(note2value.get(scale[i]));
        }
        for (int i=0; i<keySignatureSharpsCount; i++) {
            //System.err.println("restoreNoteValues: " + sharpNotesSequence[i].substring(0,1) + " value:"+note2value.get(sharpNotesSequence[i].substring(0,1)));
            restoreNoteValues.add(note2value.get(sharpNotesSequence[i].substring(0,1)));
            alteredNoteValues.add(note2value.get(sharpNotesSequence[i]));
        }
        for (int i=0; i<keySignatureFlatsCount; i++) {
//            System.err.println("restoreNoteValues: " + sharpNotesSequence[i].substring(0,1) + " value:"+note2value.get(sharpNotesSequence[i].substring(0,1)));
            restoreNoteValues.add(note2value.get(flatNotesSequence[i].substring(0,1)));
            alteredNoteValues.add(note2value.get(flatNotesSequence[i]));
        }
    }
    
    public int getKeySignatureSharpsCount() {
        return keySignatureSharpsCount;
    }
    
    public int getKeySignatureFlatsCount() {
        return keySignatureFlatsCount;
    }
    
    public Vector<Integer> getNativeNoteValues() {
        return nativeNoteValues;
    }

    public Vector<Integer> getRestoreNoteValues() {
        return restoreNoteValues;
    }

    public Vector<Integer> getAlteredNoteValues() {
        return alteredNoteValues;
    }

    public int getRythmCount() {
        return rythmCount;
    }
    
    public void setRythmCount(int rythmCount) {
        this.rythmCount = rythmCount;
    }
    
    public int getRythmUnit() {
        return rythmUnit;
    }
    
    public void setRythmUnit(int rythmUnit) {
        this.rythmUnit = rythmUnit;
    }

    public String toString() {
        String s = "";
        for (int i=0;i<scoreContent.size();i++) {
            s += scoreContent.get(i) + " \n";
        }

/*        String previous_instrument = "";
        for (int i=0;i<notes.size();i++) {
            Note n = notes.get(i);
            if (previous_instrument != n.getInstrument()) {
                previous_instrument  = n.getInstrument();
                s += "I[" + n.getInstrument() + "]\n";
            }
            s += n.toString() + "\n";
        }        
 */
        return s;
    }

    public String toJFugue() {
        String s = "";
        for (int i=0;i<scoreContent.size();i++) {
            s += scoreContent.get(i) + " ";
        }        
/* 
        for (int i=0;i<notes.size();i++) {
            s += notes.get(i).toString() + " ";
        }
 */
        //System.out.println("JFugue pattern:"+s);
        return s;
    }


    public void setReplaceTempo(int tempo) {
        this.tempo = tempo;
        for (int i=0;i<scoreContent.size();i++) {
            if (scoreContent.get(i).startsWith("T")) {
                scoreContent.set(i,"T" + this.tempo);
            }
        }        
    }
            
}
