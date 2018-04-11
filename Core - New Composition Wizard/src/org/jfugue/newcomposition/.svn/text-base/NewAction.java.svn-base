package org.jfugue.newcomposition;

import java.awt.Component;
import java.awt.Dialog;
import java.text.MessageFormat;
import javax.swing.JComponent;
import org.jfugue.score.ScoreTopComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.*;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.util.actions.CallableSystemAction;

@ActionID(id = "org.netbeans.modules.musician.actions.NewAction", category = "System")
@ActionRegistration(displayName = "#CTL_NewAction")
@ActionReferences(value = {@ActionReference(path = "Ribbon/TaskBar")})
@NbBundle.Messages("CTL_NewAction=New")
public final class NewAction extends CallableSystemAction {
    
    private WizardDescriptor.Panel[] panels;
    
    public void performAction() {
        WizardDescriptor wizardDescriptor = new WizardDescriptor(getPanels());
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wizardDescriptor.putProperty("WizardPanel_image", Utilities.loadImage("org/netbeans/modules/musician/resources/banner.png", true));
        wizardDescriptor.setTitleFormat(new MessageFormat("{0}"));
        wizardDescriptor.setTitle("New Composition");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(wizardDescriptor);
        dialog.setVisible(true);
        dialog.toFront();
        boolean cancelled = wizardDescriptor.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            ScoreTopComponent scoreComponent = new ScoreTopComponent(
                    (String)wizardDescriptor.getProperty("title"),
                    (Character)wizardDescriptor.getProperty("clef"),
                    (String)wizardDescriptor.getProperty("keysignature"),
                    (Integer)wizardDescriptor.getProperty("rythmcount"),
                    (Integer)wizardDescriptor.getProperty("rythmunit"),
                    (Integer)wizardDescriptor.getProperty("volume"),
                    (Integer)wizardDescriptor.getProperty("tempo"));
            scoreComponent.open();
            scoreComponent.requestActive();
            StatusDisplayer.getDefault().setStatusText("New composition created...");
        }
    }

    /**
     * Initialize panels representing individual wizard's steps and sets
     * various properties for them influencing wizard appearance.
     */
    private WizardDescriptor.Panel[] getPanels() {
        if (panels == null) {
            panels = new WizardDescriptor.Panel[] {
                new CompositionWizardPanel1()
            };
            String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) { // assume Swing components
                    JComponent jc = (JComponent) c;
                    // Sets step number of a component
                    jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                    // Sets steps names for a panel
                    jc.putClientProperty("WizardPanel_contentData", steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty("WizardPanel_autoWizardStyle", Boolean.TRUE);
                    // Show steps on the left side with the image on the background
                    jc.putClientProperty("WizardPanel_contentDisplayed", Boolean.TRUE);
                    // Turn on numbering of all steps
                    jc.putClientProperty("WizardPanel_contentNumbered", Boolean.FALSE);
                }
            }
        }
        return panels;
    }
    
    public String getName() {
        return NbBundle.getMessage(NewAction.class, "CTL_NewAction");
    }
    
    protected String iconResource() {
        return "org/netbeans/modules/musician/resources/New16.gif";
    }
    
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
    
    protected boolean asynchronous() {
        return false;
    }
    
}
