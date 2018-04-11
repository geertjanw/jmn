/*
 * Note.java
 *
 * Created on 28 juli 2006, 16:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.musician.api;

import java.io.Serializable;
import org.jfugue.Parser;
import org.musician.api.InvalidNoteException;
import org.musician.api.noteInputEnums;

/**
 *
 * @author Pierre Matthijs
 */
public class Note implements Serializable {
    
    private final String noteSymbols = "CDEFGABR";
    
    private final String durationSymbols = "whqis";
    
    private static Parser parser = new Parser();
    
    private int value =               60;       // absolute value; 0 through 127 ; rest=-1
    private String noteSymbol =      "C";       // A, B, C, D, E, F, G and R (rest)
    private String alterSymbol =      "";       // # or b
    private int octave =               5;       // 0 through 9
    private double duration =       0.25;       // 1 = whole, 0.5 = half, ...
    private String durationSymbol =  "q";       // w, h, q, i, s, t, x, n
    private boolean dotted =       false;       // a dotted note (or rest) is 1.5 times the duration
    private boolean sharp =        false;       // half a tone higher (#)
    private boolean flat =         false;       // half a tone lower (b)
    private boolean restore =      false;       // restore
    private int attackVelocity =      64;       // a0 through a127
    private int decayVelocity =       64;       // d0 through d127
    private boolean selected =     false;       // is the note selected by user?
    private boolean barlineBeforeNote = false;  // true if barline exists before note
    private boolean barlineAfterNote =  false;  // true if barline exists after note
    private int barlineType =               0;  // 0 = single, 1 = double, 2 = final
    private boolean drawNote =           true;  // true show note on staff
    private boolean validNote =         false;  //
    private noteInputEnums noteSource = noteInputEnums.composition_staffClick;        // did the note come from
    
    /** Creates a new instance of Note */
    public Note() {
    }
    
    public Note(String note) throws InvalidNoteException {
      //  if ( ! parser.isValidToken(note) )
      //      throw new InvalidNoteException();
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) throws InvalidNoteException {
        if ( value == -1 ) {
            this.value = value;
            noteSymbol = "R";
            octave = 0;
            sharp = false;
            flat = false;
            return;
        }
        if ( value < 0 || value > 127 )
            throw new InvalidNoteException();
        this.value = value;
        octave = value / 12;
        int r = value % 12;
        //System.out.println("setValue(): value="+value+" r="+r);
        switch ( r ) {
            case 0:
                noteSymbol = "C";
                alterSymbol = "";
                break;
            case 1:
                noteSymbol = "C";
                alterSymbol = "#";
                break;
            case 2:
                noteSymbol = "D";
                alterSymbol = "";
                break;
            case 3:
                noteSymbol = "D";
                alterSymbol = "#";
                break;
            case 4:
                noteSymbol = "E";
                alterSymbol = "";
                break;
            case 5:
                noteSymbol = "F";
                alterSymbol = "";
                break;
            case 6:
                noteSymbol = "F";
                alterSymbol = "#";
                break;
            case 7:
                noteSymbol = "G";
                alterSymbol = "";
                break;
            case 8:
                noteSymbol = "G";
                alterSymbol = "#";
                break;
            case 9:
                noteSymbol = "A";
                alterSymbol = "";
                break;
            case 10:
                noteSymbol = "A";
                alterSymbol = "#";
                break;
            case 11:
                noteSymbol = "B";
                alterSymbol = "";
        }
    }
    
    public String getNoteSymbol() {
        return noteSymbol;
    }
    
    public void setNoteSymbol(String noteSymbol) throws InvalidNoteException {
        int i = noteSymbols.indexOf(noteSymbol);
        if ( i == -1 )
            throw new InvalidNoteException();
        this.noteSymbol = noteSymbol;
        value = octave * 12;
        switch ( i ) {
            case 0:
                break;
            case 1:
                value += 2;
                break;
            case 2:
                value += 4;
                break;
            case 3:
                value += 5;
                break;
            case 4:
                value += 7;
                break;
            case 5:
                value += 9;
                break;
            case 6:
                value += 11;
        }
    }
    
    public String getAlterSymbol() {
        return alterSymbol;
    }
    
    public void setAlterSymbol(String newsym) {
        alterSymbol = newsym;
    }
        
    public int getOctave() {
        return octave;
    }
    
    public void setOctave(int octave) throws InvalidNoteException {
        if ( octave < 0 || octave > 9 )
            throw new InvalidNoteException();
        this.octave = octave;
        value = ( value % 12 ) + octave * 12;
    }
    
    public double getDuration() {
        return duration;
    }
    
    public void setDuration(double duration) throws InvalidNoteException {
        if ( duration < 0 || duration > 1 )
            throw new InvalidNoteException();
        this.duration = duration;
        int i = ((Double)(duration * 16)).intValue();
        switch ( i ) {
            case 1:
                durationSymbol = "s";
                break;
            case 2:
                durationSymbol = "i";
                break;
            case 4:
                durationSymbol = "q";
                break;
            case 8:
                durationSymbol = "h";
                break;
            case 16:
                durationSymbol = "w";
        }
    }
    
    public String getDurationSymbol() {
        return durationSymbol;
    }
    
    public void setDurationSymbol(String durationSymbol) throws InvalidNoteException {
        int i = durationSymbols.indexOf(durationSymbol);
        if ( i == -1 )
            throw new InvalidNoteException();
        this.durationSymbol = durationSymbol;
        switch ( i ) {
            case 0:
                duration = 1;
                break;
            case 1:
                duration = 1 / 2;
                break;
            case 2:
                duration = 1 / 4;
                break;
            case 3:
                duration = 1 / 8;
                break;
            case 4:
                duration = 1 / 16;
        }
    }
    
    public boolean isDotted() {
        return dotted;
    }
    
    public void setDotted(boolean dotted) {
        this.dotted = dotted;
        if ( dotted )
            duration = 1.5 * duration;
    }
    
    public boolean isSharp() {
        return sharp;
    }
    
    public void setSharp(boolean sharp) {
        this.sharp = sharp;
    }
    
    public boolean isFlat() {
        return flat;
    }
    
    public void setFlat(boolean flat) {
        this.flat = flat;
    }

    public boolean isRestore() {
        return restore;
    }
    
    public void setRestore(boolean restore) {
        this.restore = restore;
    }
    
    public int getAttackVelocity() {
        return attackVelocity;
    }
    
    public void setAttackVelocity(int attackVelocity) throws InvalidNoteException {
        if ( attackVelocity < 0 || attackVelocity > 127 )
            throw new InvalidNoteException();
        this.attackVelocity = attackVelocity;
    }
    
    public int getDecayVelocity() {
        return decayVelocity;
    }
    
    public void setDecayVelocity(int decayVelocity) throws InvalidNoteException {
        if ( decayVelocity < 0 || decayVelocity > 127 )
            throw new InvalidNoteException();
        this.decayVelocity = decayVelocity;
    }
    
    public String toString() {
        String s = noteSymbol;
        //System.out.println("toString() value:"+value);
/*
        if ( sharp )
            s += "#";
        if ( flat )
            s += "b";
 */
        s += alterSymbol;
        if ( ! s.equals("R") )
            s += octave;
        s += durationSymbol;
        if ( dotted )
            s += ".";
        if ( attackVelocity != 64 )
            s += "a" + attackVelocity;
        if ( decayVelocity != 64 )
            s += "d" + decayVelocity;
        return s;
        
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void setBarlineBeforeNote(boolean barline) {
        barlineBeforeNote = barline;
    }
    
    public boolean getBarlineBeforeNote() {
        return barlineBeforeNote;
    }
    
    public void setBarlineAfterNote(boolean barline) {
        barlineAfterNote = barline;
    }
    
    public boolean getBarlineAfterNote() {
        return barlineAfterNote;
    }
    
    public void setBarlineType(int type) {
        barlineType = type;
    }
    
    public int getBarlineType() {
        return barlineType;
    }
    
    public void setNoteSource(noteInputEnums src) {
        noteSource = src;
    }
    
    public noteInputEnums getNoteSource() {
        return noteSource;
    }
    
    public void setDrawNote(boolean showNote) {
        drawNote = showNote;
    }
    
    public boolean getDrawNote() {
        return drawNote;
    }
    
    public boolean isDrawNote() {
        return drawNote;
    }
    
    public void setValidNote(boolean state) {
        validNote = state;
    }
    
    public boolean getValidNote() {
        return validNote;
    }
    
    public boolean isNoteValid() {
        return validNote;
    }
    
    public void printNoteInfo() {
        
        System.out.println("New Note name = "+getNoteSymbol()+getAlterSymbol());
        System.out.println("    Note Symbol         = "+getNoteSymbol());
        System.out.println("    Alter Symbol        = '"+getAlterSymbol()+"'");
        System.out.println("    Octave              = "+getOctave());
        System.out.println("    Midi Value          = "+getValue());
        System.out.println("    Duration            = "+getDuration());
        System.out.println("    Duration Symbol     = "+getDurationSymbol());
        System.out.println("    Barline before      = "+getBarlineBeforeNote());
        System.out.println("    Barline After       = "+getBarlineAfterNote());
        System.out.println("    Flat                = "+isFlat());
        System.out.println("    Sharp               = "+isSharp());
        System.out.println("    Restore             = "+isRestore());
        System.out.println("    Dotted              = "+isDotted());
        System.out.println("    barlineType         = "+getBarlineType());
        System.out.println("    drawNote            = "+getDrawNote());
        System.out.println("    noteSource          = "+getNoteSource().toString());
        System.out.println("    noteValid           = "+getValidNote());
    }
    
}
