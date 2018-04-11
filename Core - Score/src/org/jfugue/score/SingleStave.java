/*
 * SingleStave.java
 *
 * Created on 5 augustus 2006, 2:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.jfugue.score;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.FilteredImageSource;
import java.util.Vector;
import javax.swing.Timer;
import org.musician.api.Instrument;
import org.musician.api.InvalidNoteException;
import org.musician.api.Note;
import org.musician.api.SelectNoteImageFilter;
import org.openide.awt.StatusDisplayer;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Pierre Matthijs
 */
public class SingleStave extends Stave {

    private final int WIDTH = 335, HEIGHT = 70;
    private final int DELAY = 100;
    private Color color1, color2;
    private static Timer timer;
    private int x1, x2, moveX, moveY;
    private final int y1 = 0, y2 = HEIGHT;
    private static final Image GClef = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/G-clef.png", true);
    private static final Image FClef = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/F-clef.png", true);
    private static final Image CClef = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/C-clef.png", true);
    private static final Image imgFlat = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/flat24.png", true);
    private static final Image imgSharp = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/sharp24.png", true);
    private static final Image imgRestore = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/restore24.png", true);
    private Image imgClef;
    private char clef;
    private int clefCenter;
    private Image symbol;
    private Image symbolPrefix; // sharp, flat or restore
    private int[] noteOffsetG = {0, 0, interspace / 2, interspace / 2, interspace, (interspace / 2) * 3, (interspace / 2) * 3, interspace * 2, interspace * 2, (interspace / 2) * 5, (interspace / 2) * 5, interspace * 3};
    private int[] noteOffsetF = {0, 0, interspace / 2, interspace / 2, interspace, (interspace / 2) * 3, (interspace / 2) * 3, interspace * 2, interspace * 2, (interspace / 2) * 5, (interspace / 2) * 5, interspace * 3};
    private int[] noteOffsetC = {0, 0, interspace / 2, interspace / 2, interspace, (interspace / 2) * 3, (interspace / 2) * 3, interspace * 2, interspace * 2, (interspace / 2) * 5, (interspace / 2) * 5, interspace * 3};
    private int[] noteOffsetT = {0, 0, interspace / 2, interspace / 2, interspace, (interspace / 2) * 3, (interspace / 2) * 3, interspace * 2, interspace * 2, (interspace / 2) * 5, (interspace / 2) * 5, interspace * 3};
    private int[] noteOffset;
    private boolean isExtended = false;
    private int posInterval = 200;
    private double measure = 0.0;
    private double measureLength = 0.0;
    private int firstNotePos = 0;
    private final InstanceContent content;
    private Instrument instrument;

    public SingleStave(ScoreTopComponent parent, int width, InstanceContent content, Instrument instrument) {
        super(parent);
        this.parent = parent;
        this.content = content;
        this.instrument = instrument;
        setWidth(width);
        this.clef = parent.getScore().getClef();
        switch (clef) {
            case 'G':
                imgClef = GClef;
                clefCenter = 60;
                noteOffset = noteOffsetG;
                break;
            case 'F':
                imgClef = FClef;
                clefCenter = 40;
                noteOffset = noteOffsetF;
                break;
            case 'C':
            case 'A':
                imgClef = CClef;
                clefCenter = 40;
                noteOffset = noteOffsetC;
                break;
            case 'T':
                imgClef = CClef;
                clefCenter = 50;
                noteOffset = noteOffsetT;
                break;
        }
        measureLength = (1.0 / parent.getScore().getRythmUnit()) * parent.getScore().getRythmCount();
        timer = new Timer(DELAY, new LineListener());
        color1 = Color.red;
        x1 = WIDTH / 2;
        x2 = WIDTH / 2;
        moveX = moveY = 3;
//        setPreferredSize(new Dimension(WIDTH, HEIGHT));
//        setBackground(Color.BLACK);
    }

    public static void startTimer() {
        timer.start();
    }

    public static void stopTimer() {
        timer.stop();

    }

    private class LineListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            x1 += moveX;
            x2 += moveX;
//            if (x1 <= 0 || x2 >= WIDTH) {
//                moveX = moveX * -1;
//            }
//            if ((x1 + 35) >= WIDTH / 2) {
//                color1 = Color.blue;
//            } else {
//                color1 = Color.red;
//            }
            repaint();
        }
    }

    public void validateNewNote(Note newNote, int newPitch, double newDuration) {
        try {
            if (newPitch != -1) {
                newNote.setValue(newPitch);
            }

            String keySignature = parent.getScore().getKeySignature();
            Vector<Integer> nativeNoteValues = parent.getScore().getNativeNoteValues();
            Vector<Integer> restoreNoteValues = parent.getScore().getRestoreNoteValues();
            Vector<Integer> alteredNoteValues = parent.getScore().getAlteredNoteValues();
            Vector<String> sharpKeySignatures = parent.getScore().getSharpKeySignatureVector();
            Vector<String> flatKeySignatures = parent.getScore().getFlatKeySignatureVector();

            Vector<Note> notes = parent.getScore().getNotes();
            Note previousNote = null;
            int previousScaleDegree = -1;

            int newNoteScaleDegree = newPitch % 12;
            int currentOldNoteScaleDegree = -1;

            boolean measureStart = isMeasureAtBegining();
            int beginingBarlineIndex = -1;
            boolean exitLoop = false;
            boolean scaleDegreeFound = false;   // true if scale degree found in measure

            Vector<Note> notesBetweenbarsLines = new Vector<>();

            if (notes.size() > 0) {
                for (int i = notes.size() - 1; exitLoop == false; i--) {
                    currentOldNoteScaleDegree = notes.get(i).getValue() % 12;
                    if (notes.get(i).getBarlineAfterNote() == true) {
                        exitLoop = true;
                    } else if (notes.get(i).getBarlineBeforeNote() == true) {
                        exitLoop = true;
                        beginingBarlineIndex = i;
                    }
                    notesBetweenbarsLines.addElement(notes.get(i));
                }
            }

            if (sharpKeySignatures.contains(keySignature.substring(0, 2))) {
                if (!nativeNoteValues.contains(newNoteScaleDegree)) {
                    if (restoreNoteValues.contains(newNoteScaleDegree)) {
                        newNote.setRestore(true);
                    } else {
                        newNote.setSharp(true);
                    }
                }

                if (notes.size() > 0) {
                    boolean stopCheck = false;
                    for (int i = 0; i < notesBetweenbarsLines.size(); i++) {
                        previousScaleDegree = notesBetweenbarsLines.get(i).getValue() % 12;
                        String previousNoteSymbol = notesBetweenbarsLines.get(i).getNoteSymbol();

                        if ((newNote.getNoteSymbol() == previousNoteSymbol) && !stopCheck) {
                            if (previousScaleDegree > newNoteScaleDegree) {
                                newNote.setRestore(true);
                                newNote.setSharp(false);
                                newNote.setFlat(false);
                            } else if (previousScaleDegree < newNoteScaleDegree) {
                                newNote.setRestore(false);
                                newNote.setSharp(true);
                                newNote.setFlat(false);
                            } else if ((previousScaleDegree == newNoteScaleDegree) && !measureStart) {
                                newNote.setRestore(false);
                                newNote.setSharp(false);
                                newNote.setFlat(false);
                            }
                            stopCheck = true;
                        }

                    }
                }

            } else if (flatKeySignatures.contains(keySignature.substring(0, 2))) {
                if (!nativeNoteValues.contains(newNoteScaleDegree)) {
                    if (restoreNoteValues.contains(newNoteScaleDegree)) {
                        newNote.setRestore(true);
                    } else {
                        newNote.setFlat(true);
                    }
                }

                if (notes.size() > 0) {
                    boolean stopCheck = false;
                    for (int i = 0; i < notesBetweenbarsLines.size(); i++) {
                        previousScaleDegree = notesBetweenbarsLines.get(i).getValue() % 12;
                        String previousNoteSymbol = notesBetweenbarsLines.get(i).getNoteSymbol();

                        if ((newNote.getNoteSymbol() == previousNoteSymbol) && !stopCheck) {
                            if (previousNote.getValue() < newNoteScaleDegree) {
                                newNote.setRestore(true);
                                newNote.setSharp(false);
                                newNote.setFlat(false);
                            } else if (previousScaleDegree > newNoteScaleDegree) {
                                newNote.setRestore(false);
                                newNote.setSharp(false);
                                newNote.setFlat(true);
                            } else if ((previousScaleDegree == newNoteScaleDegree) && !measureStart) {
                                newNote.setRestore(false);
                                newNote.setSharp(false);
                                newNote.setFlat(false);
                            }
                            stopCheck = true;
                        }
                    }
                }
            }
            newNote.setDuration(newDuration);
        } catch (InvalidNoteException ex) {
            ex.printStackTrace();
        }
    }

    public void setWidth(int width) {
        setSize(width, header + 4 * interspace + footer);
    }

    public int getDeltaPos(int posInterval, double noteDuration) {
        int deltaPos = ((Double) (posInterval * noteDuration)).intValue();
        if (deltaPos > 30) {
            deltaPos = 30;
        }
        if (deltaPos < 20) {
            deltaPos = 20;
        }
        return deltaPos;
    }

    @Override
    public Note getNoteAtPos(int x, int y) {
        Note note = null;
        int pos = firstNotePos;
        measure = 0.0;
        for (int i = 0; i < getNotes().size(); i++) {
            note = (Note) getNotes().elementAt(i);
            double noteDuration = note.getDuration();

            pos += getDeltaPos(posInterval, noteDuration);

            if (pos >= x) {
                //System.err.println("getNoteAtPos():"+i);
                return note;
            }

            measure += noteDuration;
            if (measure >= measureLength) {
                pos += getDeltaPos(posInterval, noteDuration);
                measure = 0.0;
            }
        }
        return null;
    }

    public String addNoteCheck(Note newNote) {

        String message = "";
        double totalDuration = measure + newNote.getDuration();
        if (totalDuration > measureLength) {
            message = "Exceeded number of beats per measure";
        }
        StatusDisplayer.getDefault().setStatusText(message);
        return message;
    }

    public void addNoteBarlineCheck(Note newNote) {
        // Note:  This method will modify the newNote Object
        //        currently is only uses setBarLineBeforeNote
        //        and the setBarLineAfterNote methods.

        double totalDuration = measure + newNote.getDuration();
        if (totalDuration <= measureLength) {
            if (measure == 0.0) {
                newNote.setBarlineBeforeNote(true);
            }

            if (totalDuration == measureLength) {
                newNote.setBarlineAfterNote(true);
            }
        }
    }

    public boolean isMeasureAtBegining() {
        return (measure == 0.0);
    }

    @Override
    public void paint(Graphics g) {

        int notePitch, noteCenter, octave, pitchPos;
        double noteDuration;

        int imgPrefixCenter = 12;
        int imgPrefixWidth = 7;
        boolean upsideDown;

        Graphics2D g2 = (Graphics2D) g;

        g2.clearRect(0, 0, getWidth(), getHeight());

        g2.setColor(Color.BLACK);

        int pos = 120;

        // draw lines
        for (int i = 0; i < 5; i++) {
            g2.draw(new Line2D.Double(leftMargin, header + i * interspace, getWidth() - rightMargin, header + i * interspace));
        }

        // draw Clef
        g2.drawImage(imgClef, leftMargin, (header + 3 * interspace) - clefCenter, this);

        // draw key signature
        int sharpsCount = parent.getScore().getKeySignatureSharpsCount();
        int flatsCount = parent.getScore().getKeySignatureFlatsCount();

        switch (clef) {
            case 'G':
                // fis
                if (sharpsCount > 0) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 0.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // cis
                if (sharpsCount > 1) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // gis
                if (sharpsCount > 2) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter - 0.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // dis
                if (sharpsCount > 3) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ais
                if (sharpsCount > 4) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // eis
                if (sharpsCount > 5) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 0.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // his
                if (sharpsCount > 6) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }

                // bb
                if (flatsCount > 0) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // es
                if (flatsCount > 1) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 0.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // as
                if (flatsCount > 2) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // des
                if (flatsCount > 3) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 1.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ges
                if (flatsCount > 4) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 3.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ces
                if (flatsCount > 5) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // fes
                if (flatsCount > 6) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 3.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                break;

            case 'F':
                // fis
                if (sharpsCount > 0) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // cis
                if (sharpsCount > 1) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // gis
                if (sharpsCount > 2) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 0.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // dis
                if (sharpsCount > 3) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ais
                if (sharpsCount > 4) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 3.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // eis
                if (sharpsCount > 5) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // his
                if (sharpsCount > 6) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 3.0 * interspace), this);
                    pos += imgPrefixWidth;
                }

                // bb
                if (flatsCount > 0) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 3.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // es
                if (flatsCount > 1) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // as
                if (flatsCount > 2) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 3.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // des
                if (flatsCount > 3) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ges
                if (flatsCount > 4) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 4.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ces
                if (flatsCount > 5) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // fes
                if (flatsCount > 6) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 4.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                break;

            case 'A':
            case 'C':
                // fis
                if (sharpsCount > 0) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 0.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // cis
                if (sharpsCount > 1) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // gis
                if (sharpsCount > 2) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter - 0.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // dis
                if (sharpsCount > 3) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ais
                if (sharpsCount > 4) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 3.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // eis
                if (sharpsCount > 5) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // his
                if (sharpsCount > 6) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }

                // bb
                if (flatsCount > 0) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // es
                if (flatsCount > 1) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 1.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // as
                if (flatsCount > 2) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 3.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // des
                if (flatsCount > 3) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ges
                if (flatsCount > 4) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 3.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ces
                if (flatsCount > 5) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // fes
                if (flatsCount > 6) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 4.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                break;

            case 'T':
                // fis
                if (sharpsCount > 0) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 3.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // cis
                if (sharpsCount > 1) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // gis
                if (sharpsCount > 2) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // dis
                if (sharpsCount > 3) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 0.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ais
                if (sharpsCount > 4) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // eis
                if (sharpsCount > 5) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 0.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // his
                if (sharpsCount > 6) {
                    g2.drawImage(imgSharp, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }

                // bb
                if (flatsCount > 0) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 1.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // es
                if (flatsCount > 1) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 0.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // as
                if (flatsCount > 2) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // des
                if (flatsCount > 3) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 0.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ges
                if (flatsCount > 4) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 2.5 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // ces
                if (flatsCount > 5) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 1.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                // fes
                if (flatsCount > 6) {
                    g2.drawImage(imgFlat, pos, (int) (header - imgPrefixCenter + 3.0 * interspace), this);
                    pos += imgPrefixWidth;
                }
                break;

        }

        pos += 10;

        // draw time signature
        int gNumberCentre = 8;
        g2.drawImage(getNumber(parent.getScore().getRythmCount()), pos, header, this);
        g2.drawImage(getNumber(parent.getScore().getRythmUnit()), pos, header + 20, this);
        pos += 30;

        firstNotePos = pos;

        // draw notes
        if (getNotes().size() > 0) {
            measure = 0.0;
            int flatPitch = parent.getScore().getKeySignatureFlatsCount() > 0 ? 1 : 0;
            for (int i = 0; i < getNotes().size(); i++) {
                Note note = (Note) getNotes().elementAt(i);
                notePitch = note.getValue();

                if (note.isFlat()) {
                }
                noteDuration = note.getDuration();
                upsideDown = false;
                if (notePitch != -1) {
                    selectNoteSymbol(noteDuration);
                    noteCenter = 27;
                    octave = notePitch / 12;
                    switch (clef) {
                        case 'G':
                            pitchPos = header - noteCenter + (interspace / 2) * 3 + ((6 - octave) * ((interspace / 2) * 7)) - noteOffset[notePitch % 12];
                            break;
                        case 'F':
                            pitchPos = header - noteCenter - interspace + ((5 - octave) * ((interspace / 2) * 7)) - noteOffset[notePitch % 12];
                            break;
                        case 'A':
                        case 'C':
                            pitchPos = header - noteCenter + interspace * 2 + ((5 - octave) * ((interspace / 2) * 7)) - noteOffset[notePitch % 12];
                            break;
                        case 'T':
                            pitchPos = header - noteCenter + interspace + ((5 - octave) * ((interspace / 2) * 7)) - noteOffset[notePitch % 12];
                            break;
                        default:
                            pitchPos = header - noteCenter + ((6 - octave) * ((interspace / 2) * 7)) - noteOffset[notePitch % 12];
                            break;
                    }
                } else {
                    // rest
                    selectRestSymbol(noteDuration);
                    if (noteDuration == 1) {
                        noteCenter = 12;
                    } else {
                        if (noteDuration == 0.5) {
                            noteCenter = 18;
                        } else {
                            noteCenter = 14;
                        }
                    }
                    pitchPos = header + interspace * 2 - noteCenter;
                }

                // draw helping lines
                if (pitchPos <= -7) {
                    for (int j = -7; j >= pitchPos; j = j - 10) {
                        int y = header - ((Math.abs(j) / 10) + 1) * interspace;
                        g2.draw(new Line2D.Double(pos - 2, y, pos + 18, y));
                    }
                } else if (pitchPos >= 53) {
                    for (int j = 53; j <= pitchPos; j = j + 10) {
                        int y = header + 4 * interspace + ((Math.abs(j - 53) / 10) + 1) * interspace;
                        g2.draw(new Line2D.Double(pos - 4, y, pos + 16, y));
                    }
                }

                // upside-down note
                if (pitchPos <= 23 && noteDuration < 1.0) {
                    selectNoteSymbolDown(noteDuration);
                    upsideDown = true;
                }

                // altering note position in case of flat note
                if (note.isFlat()
                        || (parent.getScore().getKeySignatureFlatsCount() > 0
                        && parent.getScore().getAlteredNoteValues().contains(note.getValue() % 12))) {
                    try {
                        pitchPos -= noteOffset[notePitch % 12 + 1] - noteOffset[notePitch % 12];
                    } catch (ArrayIndexOutOfBoundsException rx) {
                        pitchPos -= 5;
                    }
                }

//                System.out.println("note sharp:"+note.isSharp()+" flat:"+note.isFlat()+" restore:"+note.isRestore());

                // selecting the prefix symbol (sharp, flat, restore)
                if (note.getValue() != -1) {
                    int v = note.getValue() % 12;
                    if (note.isFlat()) {
                        symbolPrefix = imgFlat;
                    } else if (note.isSharp()) {
                        symbolPrefix = imgSharp;
                    } else if (note.isRestore()) {
                        symbolPrefix = imgRestore;
                    } else {
                        symbolPrefix = null;
                    }
                }


                // if the note is selected, filter the note's image (change it's color to red)
                if (note.isSelected()) {
                    symbol = createImage(new FilteredImageSource(symbol.getSource(), new SelectNoteImageFilter()));
                    if (symbolPrefix != null) {
                        symbolPrefix = createImage(new FilteredImageSource(symbolPrefix.getSource(), new SelectNoteImageFilter()));
                    }
                }

                drawNote(g2, pitchPos, pos, upsideDown);

                measure += noteDuration;
                if (measure >= measureLength) {
                    pos += getDeltaPos(posInterval, noteDuration);
                    g2.draw(new Line2D.Double(pos, header, pos, header + interspace * 4));
                    measure = 0.0;
                }

                pos += getDeltaPos(posInterval, noteDuration);
                if (pos > getWidth() - rightMargin - 22) {
                    if (!isExtended) {
                        isExtended = true;
                        parent.addStave();
                    }
                    break;
                }
            }
            
            g2.drawString(instrument.getDescription(), 55, 25);
//            g2.drawString("i", 10, 43);
//            g2.drawString("a", 10, 53);
//            g2.drawString("n", 10, 63);
//            g2.drawString("o", 10, 73);

            g2.setPaint(new GradientPaint(
                    new Point2D.Double(WIDTH / 2, 0),
                    Color.red,
                    new Point2D.Double(WIDTH / 2 + 0.0001, 0),
                    Color.blue));
            g2.drawLine(x1, y1, x2, y2);
//        g2.setColor(Color.pink);
//        g2.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

        }
    }

    private void drawNote(Graphics2D g2, int y, int x, boolean upsideDown) {
        if (symbolPrefix != null) {
            g2.drawImage(symbolPrefix, x - 6, (int) (y + 1.5 * interspace), this);
            x += 3;
        }
        g2.drawImage(symbol, x, upsideDown ? y + 23 : y, this);
    }

    private void selectNoteSymbol(double duration) {

        if (duration == 1.0) {
            symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/whole-note.png", true);
        } else {
            if (duration == 0.5) {
                symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/half-note.png", true);
            } else {
                if (duration == 0.25) {
                    symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/quarter-note.png", true);
                } else {
                    if (duration == 0.125) {
                        symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/eighth-note.png", true);
                    } else {
                        if (duration == 1.0 / 16) {
                            symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/sixteenth-note.png", true);
                        }
                    }
                }
            }
        }
    }

    private void selectNoteSymbolDown(double duration) {

        if (duration == 0.5) {
            symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/half-note-down.png", true);
        } else {
            if (duration == 0.25) {
                symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/quarter-note-down.png", true);
            } else {
                if (duration == 0.125) {
                    symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/eighth-note-down.png", true);
                } else {
                    if (duration == 1.0 / 16) {
                        symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/sixteenth-note-down.png", true);
                    }
                }
            }
        }
    }

    private void selectRestSymbol(double duration) {
        if (duration == 1.0) {
            symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/rectangle-rest.png", true);
        } else {
            if (duration == 0.5) {
                symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/rectangle-rest.png", true);
            } else {
                if (duration == 0.25) {
                    symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/quarter-rest.png", true);
                } else {
                    if (duration == 0.125) {
                        symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/eighth-rest.png", true);
                    } else {
                        if (duration == 1.0 / 16) {
                            symbol = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/sixteenth-rest.png", true);
                        }
                    }
                }
            }
        }
    }

//    private void selectNotePrefixSymbol(Note note) {
//        if (note.getValue() == -1) return; // rest
//        
//        int v = note.getValue() % 12;
//        if (note.isFlat()) {
//            symbolPrefix = imgFlat;
//        } else if(note.isSharp()) {
//            symbolPrefix = imgSharp;
//        } else if(parent.getScore().getRestoreNoteValues().contains(v)) {
//            symbolPrefix = imgRestore;
//        } else {
//            symbolPrefix = null;
//        }
///*  
//        
//        if (parent.getScore().getNativeNoteValues().contains(v)) {
//            symbolPrefix = null;
//        } else if (parent.getScore().getRestoreNoteValues().contains(v)) {
//            symbolPrefix = imgRestore;
//        } else if (parent.getScore().getKeySignatureSharpsCount() > 0) {
//            symbolPrefix = imgSharp;
//        } else if (parent.getScore().getKeySignatureFlatsCount() > 0) {
//            symbolPrefix = imgFlat;
//        }
// **/
//    }
    private Image getNumber(int number) {

        Image image = null;

        switch (number) {
            case 1:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/one.png", true);
                break;
            case 2:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/two.png", true);
                break;
            case 3:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/three.png", true);
                break;
            case 4:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/four.png", true);
                break;
            case 5:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/five.png", true);
                break;
            case 6:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/six.png", true);
                break;
            case 8:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/eight.png", true);
                break;
            case 9:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/nine.png", true);
                break;
            case 12:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/twelve.png", true);
                break;
            case 16:
                image = ImageUtilities.loadImage("org/netbeans/modules/musician/resources/sixteen.png", true);
                break;
        }

        return image;
    }
}
