/*
 * Key.java
 *
 * Created on August 17, 2007, 8:39 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 *
 * Key.java
 *
 * Created on March 5, 2007, 10:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/* Code modified from Sun MidiSynth.java SoundDemo
 */

/*
 * @(#)MidiSynth.java	1.15	99/12/03
 *
 * Copyright (c) 1999 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

package org.jfugue.keyboard;

import java.awt.Rectangle;
//import org.netbeans.modules.musician.gui.jmidiinstruments.JMidiInstrumentsTopComponent;

/**
 * Black and white keys or notes on the piano.
 */

public class Key extends Rectangle {

    final int ON = 0, OFF = 1;
    int noteState = OFF;
    int kNum;
    int kNess;
    char kName[] = new char[3];
    char kMidiName[] = new char[3];
    
    private final String keyNames = "C D EF G A B";
    
//    private static JMidiInstrumentsTopComponent JMidiInstruments_instance;
    //private static ValeriKeyboardTopComponent keyboardTC_instance;
    
    public Key(int x, int y, int width, int height, int num) {
       super(x, y, width, height);
       kNum = num;
       kNess = num % 12;
       
       kName[0] = keyNames.charAt(kNess);
              
       if (kName[0] != ' ') {
           Integer t = (Integer)num / 12 - 1;
           kName[1] = t.toString().charAt(0);
           kName[2] = ' ';
       } else {
           kName[0] = keyNames.charAt(kNess-1);
           Integer t = (Integer)num / 12 - 1;
           kName[1] = '#';
           kName[2] = t.toString().charAt(0);           
       }
       
       Integer v = (Integer)num;
       kMidiName[0] = v.toString().charAt(0);
       kMidiName[1] = v.toString().charAt(1);
       kMidiName[2] = ' ';
    }
        
    public boolean isNoteOn() {
       return noteState == ON;
    }
    
    public void on() {
       setNoteState(ON);
       ValeriKeyboardTopComponent.noteOn(kNum);
    }
        
    public void off() {
       setNoteState(OFF);
       ValeriKeyboardTopComponent.noteOff(kNum);
    }
        
    public void setNoteState(int state) {
       noteState = state;
    }
    
    public int getNoteState() {
        return noteState;
    }
    
    public int getPitch() {
        return kNum;
    }
    
    public boolean isMidiPitchAvailable(int midiPitch) {
        boolean keyFound = (midiPitch == kNum);
        return keyFound;        
    }

} // End class Key



