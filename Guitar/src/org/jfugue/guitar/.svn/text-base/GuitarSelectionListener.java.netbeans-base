package org.jfugue.guitar;

import java.util.Collections;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jfugue.Player;
import org.musician.api.*;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Johannes Reher
 */
public class GuitarSelectionListener implements ListSelectionListener {

    private Player player = new Player();
    private GuitarJTable table;
//    private SingleStave stave;
    private Score score;
    
    private InstanceContent ic;
    
    public GuitarSelectionListener(GuitarJTable t, InstanceContent ic) {
        table = t;
        this.ic = ic;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

//        ScoreTopComponent stc = CentralLookup.getDefault().lookup(ScoreTopComponent.class);

//        stave = (SingleStave) stc.getStave();
//        score = stc.getScore();
        if (!e.getValueIsAdjusting()) {
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();
            if (row >= 0 && col >= 0) {
                Note newNote = new Note();
//                score.validateNewNote(newNote, 47+5*(table.getStrings()-row) + col + ((row <= 1)?-1:0), 0.25);
                newNote.setNoteSource(noteInputEnums.virtual_keyboardClick);
                newNote.setValidNote(true);
//                CentralLookup.getDefault().add(newNote);
//                stave.addNote(newNote);
                NoteWrapper nw = new NoteWrapper(newNote, 47+5*(table.getStrings()-row) + col + ((row <= 1)?-1:0));
                ic.set(Collections.singleton(nw), null);
                String note = "I[Guitar] " + newNote.getNoteSymbol() + newNote.getAlterSymbol() + newNote.getOctave() + newNote.getDurationSymbol();
                player.play(note);
                table.clearSelection();
            }
        }
    }
}
