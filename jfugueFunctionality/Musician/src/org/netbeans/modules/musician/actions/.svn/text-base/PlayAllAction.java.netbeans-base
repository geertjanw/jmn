package org.netbeans.modules.musician.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.jfugue.Pattern;
import org.jfugue.Player;
import org.musician.api.Score;
//import org.jfugue.score.SingleStave;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(id = "org.netbeans.modules.musician.actions.PlayAllAction", category = "System")
@ActionRegistration(asynchronous=true, displayName = "#CTL_PlayAllAction", iconBase = "org/netbeans/modules/musician/actions/Play16.gif")
@ActionReferences(value = {
    @ActionReference(path = "Ribbon/TaskBar")})
@Messages("CTL_PlayAllAction=Play All")
public final class PlayAllAction implements ActionListener {

    private final List<Score> context;

    public PlayAllAction(List<Score> context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
//        SingleStave.startTimer();
        StringBuilder sb = new StringBuilder();
        for (Score score : context) {
            sb.append(score.toJFugue());
        }
        Player player = new Player();
        player.play(new Pattern(sb.toString()));
//        SingleStave.stopTimer();
    }
}
