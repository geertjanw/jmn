/*
 * Piano.java
 *
 * Created on August 17, 2007, 8:37 PM
 *
 * Created on February 24, 2007, 9:30 AM
 *
 * Last Mods:   September 2, 2007, 10:09 PM
 *              by: m valeri
 *
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import org.musician.api.Note;
import org.musician.api.NoteWrapper;
import org.musician.api.noteInputEnums;
import org.openide.util.lookup.InstanceContent;

public class Piano extends javax.swing.JPanel implements MouseListener, Observer {

    public final static int KEY_HEIGHT = 120;               // 6 inches
    public final static int BLACK_KEY_HEIGHT = 80;          // 4 inches
    public final static int KEY_WIDTH = 32;
    public final static int WIDTH_SCALE = 29;
    public final static int BLACK_KEY_OVERLAP = 8;
    public final static int DEFAULT_NUM_OCTAVES = 4;
    public final static int DEFAULT_TRANSPOSE = 36;         // pitch C2
    public final static int PITCH_C0 = 12;
    public final static int PITCH_C1 = 24;
    public final static int PITCH_C2 = 36;
    public final static int PITCH_C3 = 48;
    public final static int DEFAULT_SIX_OCTAVE_START = 0 * KEY_WIDTH;
    public final static int DEFAULT_FOUR_OCTAVE_START = 7 * KEY_WIDTH;
    public final static int DEFAULT_TWO_OCTAVE_START = 14 * KEY_WIDTH;
    public final int ON = 0;
    public final int OFF = 1;
    public final static Color KEY_BLUE = new Color(204, 204, 255);
    public final Color KEY_PINK = new Color(255, 175, 175);
    public int num_octaves = DEFAULT_NUM_OCTAVES;
    public int transpose = DEFAULT_TRANSPOSE;           // start at C2
    public int key_start = DEFAULT_FOUR_OCTAVE_START;
    private boolean showAllKeyNames = false;
    private boolean showAllMidiKeyNames = false;
    private Key theKey;
    private Key prevKey;
    public Vector keys = new Vector();
    private Vector whiteKeys = new Vector();
    private Vector blackKeys = new Vector();
    private JCheckBoxMenuItem showUs;
    private JCheckBoxMenuItem computerIO;
    private JCheckBoxMenuItem midiAIO;
    private JCheckBoxMenuItem midiBIO;
    private ValeriKeyboardTopComponent parent = null;
//    private SingleStave stave;
    private final InstanceContent newNoteContent;
    private double newDuration;

    /**
     * Creates new form Piano
     */
    public Piano(ValeriKeyboardTopComponent parent, InstanceContent newNoteContent) {
        this.parent = parent;
        this.newNoteContent = newNoteContent;
        initComponents();                             // init auto generated components
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH_SCALE * KEY_WIDTH + 1, KEY_HEIGHT + 1));
        setOpaque(true);

        buildKeyBoard();

        addMouseMotionListener(new MouseMotionAdapter() {

            public void mouseMoved(MouseEvent e) {
                if (isMouseOverKB()) {
                    Key key = getKey(e.getPoint());
                    if (prevKey != null && prevKey != key) {
                        prevKey.off();
                    }
                    if (key != null && prevKey != key) {
                        key.on();
                    }
                    prevKey = key;
                    repaint();
                }
            }
        });

        addMouseListener(this);

    }

    public void mousePressed(MouseEvent e) {
        prevKey = getKey(e.getPoint());
        if (prevKey != null) {
            int newPitch = prevKey.getPitch();
            Note newNote = new Note();
//            if (currentScoreTopComponent != null) {
//                SingleStave theStave = (SingleStave)currentScoreTopComponent.getStave();
//                Score cScore = currentScoreTopComponent.getScore();
//
//            stave.validateNewNote(newNote, newPitch, noteType);
            newNote.setNoteSource(noteInputEnums.virtual_keyboardClick);
            newNote.setValidNote(true);
            
            NoteWrapper nw = new NoteWrapper(newNote, newPitch);

            newNoteContent.set(Collections.singleton(nw), null);
//            newPitchContent.set(Collections.singleton(newPitch), null);
//            content.add(newNote);

//            Collection<? extends Object> lookupAll = parent.getLookup().lookupAll(Object.class);
//            for (Object object : lookupAll) {
//                JOptionPane.showMessageDialog(null,"2: " + newNote);
//            }

//                parent.getLookup().lookup(InstanceContent.class).add(newNote);

//                theStave.addNote(newNote);
//            }            
            prevKey.on();
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (prevKey != null) {
            prevKey.off();
            repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
        if (prevKey != null) {
            prevKey.off();
            repaint();
            prevKey = null;
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void midiKeyPressed(int midiPitch) {
        prevKey = getKey(midiPitch);
        if (prevKey != null) {
            prevKey.on();
            repaint();
        }
    }

    public void midiKeyReleased(int midiPitch) {
        prevKey = getKey(midiPitch);
        if (prevKey != null) {
            prevKey.off();
            repaint();
        }
    }

    public void setNumberOfOctaves(int octaves) {
        num_octaves = octaves;
        switch (octaves) {
            case 2:
                key_start = DEFAULT_TWO_OCTAVE_START;
                transpose = PITCH_C3;                           // Start pitch at C3 in
                break;
            case 4:
                key_start = DEFAULT_FOUR_OCTAVE_START;
                transpose = PITCH_C2;                           // Start pitch at C2 in
                break;
            case 6:
                key_start = DEFAULT_SIX_OCTAVE_START;
                transpose = PITCH_C1;                           // Start pitch at C1 in
                break;
            default:
                setDefaultNumberOfOctaves();
        }

        buildKeyBoard();
        repaint();

    }

    public void setDefaultNumberOfOctaves() {
        num_octaves = DEFAULT_NUM_OCTAVES;
        key_start = DEFAULT_FOUR_OCTAVE_START;
        transpose = PITCH_C2;                           // Start pitch at C2 in
        buildKeyBoard();
        repaint();
    }

    public Key getKey(Point point) {
        // Identify the key that was clicked.
        for (int i = 0; i < keys.size(); i++) {
            if (((Key) keys.get(i)).contains(point)) {
                return (Key) keys.get(i);
            }
        }
        return null;
    }

    public Key getKey(int midiPitch) {
        // Identify the key that was clicked.

        for (int i = 0; i < whiteKeys.size(); i++) {
            Key key = (Key) whiteKeys.get(i);
            if (key.isMidiPitchAvailable(midiPitch)) {
                return key;
            }
        }

        for (int i = 0; i < blackKeys.size(); i++) {
            Key key = (Key) blackKeys.get(i);
            if (key.isMidiPitchAvailable(midiPitch)) {
                return key;
            }
        }

        return null;
    }

    public void buildKeyBoard() {

        keys.clear();
        whiteKeys.clear();
        blackKeys.clear();

        int[] whiteIDs = {0, 2, 4, 5, 7, 9, 11};
        int[] blackIDs = {1, 3, 6, 8, 10};
        int lastKeyWidth = 0;

        for (int i = 0, x = key_start; i < num_octaves; i++) {
            for (int j = 0; j < 7; j++, x += KEY_WIDTH) {
                int keyNum = i * 12 + whiteIDs[j] + transpose;
                whiteKeys.add(new Key(x, 0, KEY_WIDTH, KEY_HEIGHT, keyNum));
            }
            lastKeyWidth = x;
        }

        // Add One Last C Key to End OCTAVE
        {
            int keyNum = num_octaves * 12 + whiteIDs[0] + transpose;
            whiteKeys.add(new Key(lastKeyWidth, 0, KEY_WIDTH, KEY_HEIGHT, keyNum));
        }

        for (int i = 0, x = key_start; i < num_octaves; i++, x += KEY_WIDTH) {
            int keyNum = i * 12 + transpose;

            blackKeys.add(new Key((x += KEY_WIDTH) - BLACK_KEY_OVERLAP, 0, KEY_WIDTH / 2,
                    BLACK_KEY_HEIGHT, keyNum + blackIDs[0]));
            blackKeys.add(new Key((x += KEY_WIDTH) - BLACK_KEY_OVERLAP, 0, KEY_WIDTH / 2,
                    BLACK_KEY_HEIGHT, keyNum + blackIDs[1]));
            x += KEY_WIDTH;

            blackKeys.add(new Key((x += KEY_WIDTH) - BLACK_KEY_OVERLAP, 0, KEY_WIDTH / 2,
                    BLACK_KEY_HEIGHT, keyNum + blackIDs[2]));
            blackKeys.add(new Key((x += KEY_WIDTH) - BLACK_KEY_OVERLAP, 0, KEY_WIDTH / 2,
                    BLACK_KEY_HEIGHT, keyNum + blackIDs[3]));
            blackKeys.add(new Key((x += KEY_WIDTH) - BLACK_KEY_OVERLAP, 0, KEY_WIDTH / 2,
                    BLACK_KEY_HEIGHT, keyNum + blackIDs[4]));
        }

        keys.addAll(blackKeys);
        keys.addAll(whiteKeys);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();

        g2.setBackground(getBackground());
        g2.clearRect(0, 0, d.width, d.height);

        g2.setColor(Color.white);
        g2.fillRect(0, 0, WIDTH_SCALE * KEY_WIDTH, KEY_HEIGHT);

        for (int i = 0; i < whiteKeys.size(); i++) {
            Key key = (Key) whiteKeys.get(i);
            if (key.isNoteOn()) {
                g2.setColor(KEY_BLUE);
                g2.fill(key);
            }

            if (key.kNum == 60) {
                g2.setColor(KEY_PINK);
                g2.fill(key);

                if (key.isNoteOn()) {
                    g2.setColor(KEY_BLUE);
                    g2.fill(key);
                }
            }

            g2.setColor(Color.black);
            g2.draw(key);

            if ((key.kNum == 60) || showAllKeyNames) {
                g2.setColor(Color.black);
                g2.drawChars(key.kName, 0, 3, key.x + (KEY_WIDTH / 4), ((key.y + KEY_HEIGHT) * 7) / 8);
            }

            if (showAllMidiKeyNames) {
                g2.setColor(Color.blue);
                g2.drawChars(key.kMidiName, 0, 3, key.x + (KEY_WIDTH / 4), ((key.y + KEY_HEIGHT) * 7) / 6);
            }
        }

        for (int i = 0; i < blackKeys.size(); i++) {
            Key key = (Key) blackKeys.get(i);
            if (key.isNoteOn()) {
                g2.setColor(KEY_BLUE);
                g2.fill(key);

                g2.setColor(Color.black);
                g2.draw(key);
            } else {
                g2.setColor(Color.black);
                g2.fill(key);
            }
        }
    }

    public void initLocalComponents() {
//        JMidiInstruments_instance.findInstance().initLocalComponents();
//        midiConfigDialog.setExternalGUI(showUs, computerIO, midiAIO, midiBIO);
//        JMidiInstruments_instance.findInstance().midiOpen();
    }

//    public void setMidiInstrumentConfigurationDialog(JMidiInstrumentsDialog configDialog) {
//        midiConfigDialog = configDialog;
//        for (int i = 0; i < whiteKeys.size(); i++) {
//            Key key = (Key) whiteKeys.get(i);
//            key.midiConfigurationDialog = configDialog;
//        }
//        
//        for (int i = 0; i < blackKeys.size(); i++) {
//            Key key = (Key) blackKeys.get(i);
//            key.midiConfigurationDialog = configDialog;
//        }
//    }
//    public void setConfigVisible(boolean show) {
//        midiConfigDialog.setVisible(show);
//    }
//    public void setExternalGUI(JCheckBoxMenuItem showDialog,
//            JCheckBoxMenuItem ComputerIO,
//            JCheckBoxMenuItem MidiPortAIO,
//            JCheckBoxMenuItem MidiPortBIO) {
//        showUs = showDialog;
//        computerIO = ComputerIO;
//        midiAIO = MidiPortAIO;
//        midiBIO = MidiPortBIO;
//    }
    // Variables declaration - do not modify                     
    // End of variables declaration                   
    public void update(Observable o, Object arg) {
        String keyStatePitch = (String) arg;
        String[] parts = keyStatePitch.split(":", 4);
        int pitch = Integer.valueOf(parts[1]).intValue();
        int velocity = Integer.valueOf(parts[3]).intValue();
        String cmd = parts[0];
        System.out.println(cmd);
        boolean tmp = (cmd == "note On");
        if (cmd.compareTo("note On") == 0) {
            midiKeyPressed(pitch);
        } else if (cmd.compareTo("note Off") == 0) {
            midiKeyReleased(pitch);
        }
//        if (velocity > 0) {
//          midiKeyPressed(pitch);  
//        } else {
//          midiKeyReleased(pitch);  
//        }

    }

    public void setMouseOverKB(boolean mouseHover) {
        parent.setSelectedMouseOverKB(mouseHover);
    }

    public boolean isMouseOverKB() {
        return parent.isMouseOverKB();
    }

    public void showAllNames(boolean on) {
        showAllKeyNames = on;
        repaint();
    }

    public void showAllMidiNames(boolean on) {
        showAllMidiKeyNames = on;
        repaint();
    }

    /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
