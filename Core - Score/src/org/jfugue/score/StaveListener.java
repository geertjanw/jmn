package org.jfugue.score;

import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ListIterator;
import java.util.Vector;
import org.musician.api.InvalidNoteException;
import org.musician.api.InvalidNoteException;
import org.musician.api.Note;
import org.musician.api.Note;
import org.musician.api.noteInputEnums;
import org.musician.api.noteInputEnums;
import org.jfugue.score.Stave;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;

public class StaveListener implements MouseListener, MouseMotionListener, KeyListener {

    private Stave theStave;
    private int refPitch = 72;
    private double refPos;
    private int pitch;
    private int[] blackNotesAbove = {0, 1, 2, 2, 3, 4, 5, 5, 6, 7, 7, 8, 9, 10, 10};
    private int[] blackNotesBeneath = {0, 0, 1, 2, 3, 3, 4, 5, 5, 6, 7, 8, 8, 9, 10};
    private Vector<Note> selectedNotes = new Vector<>();

    public StaveListener(Stave stave) {
        theStave = stave;
        switch (theStave.parent.getScore().getClef()) {
            case 'G':
                refPitch = 72;
                refPos = theStave.interspace * 1.5;
                break;
            case 'F':
                refPitch = 48;
                refPos = theStave.interspace * 2.5;
                break;
            case 'C':
                refPitch = 60;
                refPos = theStave.interspace * 2;
                break;
            case 'T':
                refPitch = 60;
                refPos = theStave.interspace;
                break;
        }
    }

    public void mouseClicked(MouseEvent e) {
        theStave.requestFocus();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {

        //System.out.println("getModifiers:"+e.getModifiers()+" CTRL:"+MouseEvent.CTRL_MASK);

        if ((e.getModifiers() & MouseEvent.CTRL_MASK) != MouseEvent.CTRL_MASK) {
            theStave.parent.clearSelectedNotes();
        }
        Note selectedNote = theStave.getNoteAtPos(e.getX(), e.getY());
        if (selectedNote != null) {
            //System.out.println("selected note's value:"+selectedNote.getValue());
            theStave.parent.addSelectedNote(selectedNote);
        }

        if (theStave.parent.isRest()) {
            pitch = -1;
        } else {
            if (e.getY() < 0 || e.getY() > (theStave.header + theStave.interspace * 4 + theStave.footer)) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            // find note
            Double dpitch = refPitch + (theStave.header + refPos - e.getY()) / (theStave.interspace / 2);
            pitch = dpitch.intValue();

            if (pitch > refPitch) {
                int devPitch = pitch - refPitch;
                if (devPitch < 15) // size of Array blackNotes
                {
                    pitch += blackNotesAbove[devPitch];
                } else {
                    StatusDisplayer.getDefault().setStatusText("Out of range");
                }
            } else {
                int devPitch = refPitch - pitch;
                if (devPitch < 15) // size of Array blackNotes
                {
                    pitch -= blackNotesBeneath[devPitch];
                } else {
                    StatusDisplayer.getDefault().setStatusText("Out of range");
                }
            }
        }

        if (theStave.parent.getSelectedNotes().size() > 0) {
            theStave.parent.repaint();
        } else {
            // deselect all notes
            theStave.parent.clearSelectedNotes();
            // create a new note
            Note n = new Note();
            n.setNoteSource(noteInputEnums.composition_staffClick);

            //System.out.println("new Note()");
            if (theStave.parent.getScore().getRestoreNoteValues().contains(pitch % 12)) {
                if (theStave.parent.getScore().getKeySignatureSharpsCount() > 0) {
                    pitch++;
                } else if (theStave.parent.getScore().getKeySignatureFlatsCount() > 0) {
                    pitch--;
                }
            }
            //System.out.println("sharp:"+theStave.parent.isSharp()+" flat:"+theStave.parent.isFlat()+" restore:"+theStave.parent.isRestore());
            if (theStave.parent.isSharp()) {
                pitch++;
                n.setSharp(true);
            } else if (theStave.parent.isFlat()) {
                pitch--;
                n.setFlat(true);
            } else if (theStave.parent.isRestore()) {
                n.setRestore(true);
            }
            //System.out.println("note sharp:"+n.isSharp()+" flat:"+n.isFlat()+" restore:"+n.isRestore());
            try {
                n.setValue(pitch);
            } catch (InvalidNoteException ex) {
                Exceptions.printStackTrace(ex);
            }
            try {
                n.setDuration(theStave.parent.getDuration());
            } catch (InvalidNoteException ex) {
                Exceptions.printStackTrace(ex);
            }
            theStave.addNote(n);
        }
    }

    public void keyTyped(KeyEvent e) {
        //System.out.println("keyTyped:"+e.getKeyChar());
    }

    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_DELETE:

                    for (int i = 0; i < theStave.parent.getSelectedNotes().size(); i++) {
                        Note note = (Note) theStave.parent.getSelectedNotes().elementAt(i);
                        String note_old_jfugue = note.toString();
                        // CHANGING PITCH
                        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                            Integer new_value = note.getValue() + (e.getKeyCode() == KeyEvent.VK_UP ? 1 : -1);
                            note.setValue(new_value);
                            if (theStave.parent.getScore().getNativeNoteValues().contains(new_value % 12)) {
                                //System.out.println("native note");
                                note.setSharp(false);
                                note.setFlat(false);
                                note.setRestore(false);
                            } else if (theStave.parent.getScore().getRestoreNoteValues().contains(new_value % 12)) {
                                //System.out.println("restore note");
                                note.setRestore(true);
                            } else {
                                //System.out.println("non-native note");
                                if (theStave.parent.getScore().getKeySignatureSharpsCount() >= 0) {
                                    note.setSharp(true);
                                } else if (theStave.parent.getScore().getKeySignatureFlatsCount() > 0) {
                                    note.setFlat(true);
                                }
                            }
                        } // CHANGING LENGTH
                        else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                            double multiplier = (e.getKeyCode() == KeyEvent.VK_RIGHT ? 2 : 0.5);
                            double new_duration = note.getDuration() * multiplier;
                            if (new_duration < 1.0 / 16) {
                                new_duration = 1.0 / 16;
                            }
                            if (new_duration > 1) {
                                new_duration = 1;
                            }
                            note.setDuration(new_duration);
                        }
                        // PROJECT THE CHANGE IN THE JFUGUE COMMAND WINDOW
                        ListIterator<String> command_i = theStave.parent.getScore().getScoreContent().listIterator();
                        int note_i = 0;
                        while (true) {
                            if (!command_i.hasNext()) {
                                break;
                            }
                            String command = command_i.next();
                            // have we found the selected note's command?
                            if ((Note) theStave.parent.getScore().getNotes().elementAt(note_i) == note
                                    && command.equals(note_old_jfugue)) {
                                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                                    command_i.remove();
                                } else {
                                    command_i.set(note.toString());
                                    note_i++;
                                }
                                break;
                            } // otherwise move along the vector of notes if the pair command/note matches
                            else if (command.equals(((Note) theStave.parent.getScore().getNotes().elementAt(note_i)).toString())) {
                                note_i++;
                            }
                        }
                        // REMOVING NOTE
                        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                            theStave.parent.getScore().removeNote(note);
                            theStave.getNotes().remove(note);
                        }
                    }
                    theStave.parent.repaint();
                    break;
                /*
                 * case KeyEvent.VK_DELETE: for (int i=0;
                 * i<theStave.parent.getSelectedNotes().size(); i++) { Note note
                 * = (Note) theStave.parent.getSelectedNotes().elementAt(i);
                 * theStave.parent.getScore().removeNote(note);
                 * theStave.getNotes().remove(note); }
                 * theStave.parent.repaint(); break;
                 */
                case KeyEvent.VK_1:
                    // when 64th notes get implemented
                    break;

                case KeyEvent.VK_2:
                    // when 32nd note get implemented
                    break;

                case KeyEvent.VK_3:
                    theStave.parent.doClickBtnCompositionSixteenthNote();
                    break;

                case KeyEvent.VK_4:
                    theStave.parent.doClickBtnCompositionEighthNote();
                    break;

                case KeyEvent.VK_5:
                    theStave.parent.doClickBtnCompositionQuarterNote();
                    break;

                case KeyEvent.VK_6:
                    theStave.parent.doClickBtnCompositionHalfNote();
                    break;

                case KeyEvent.VK_7:
                    
                    theStave.parent.doClickBtnCompositionWholeNote();
                    break;

            }
        } catch (Exception ex) {
        }
    }

    public void keyReleased(KeyEvent e) {
        //System.out.println("keyReleased:"+e.getKeyChar());
    }
}
