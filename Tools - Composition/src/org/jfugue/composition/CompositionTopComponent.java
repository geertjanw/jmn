/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jfugue.composition;

import java.awt.BorderLayout;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.ActionMap;
import javax.swing.text.DefaultEditorKit;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.LookupEvent;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.netbeans.api.settings.ConvertAsProperties;
import org.musician.api.CentralLookup;
import org.musician.api.Score;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.BeanNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.util.Lookup.Result;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(dtd = "-//org.jfugue.composition//Composition//EN",
autostore = false)
@TopComponent.Description(preferredID = "CompositionTopComponent",
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = true)
@ActionID(category = "Window", id = "org.jfugue.composition.CompositionTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(displayName = "#CTL_CompositionAction",
preferredID = "CompositionTopComponent")
public final class CompositionTopComponent extends TopComponent 
implements ExplorerManager.Provider, LookupListener {

    private ExplorerManager em = new ExplorerManager();

    public CompositionTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(CompositionTopComponent.class, "CTL_CompositionTopComponent"));
        setToolTipText(NbBundle.getMessage(CompositionTopComponent.class, "HINT_CompositionTopComponent"));
        setDisplayName("Composition");
        setLayout(new BorderLayout());

        BeanTreeView btv = new BeanTreeView();
        btv.setRootVisible(false);

        Children kids = Children.create(new ScoreChildFactory(), true);

        em.setRootContext(new AbstractNode(kids));
        
        add(btv, BorderLayout.CENTER);

        ActionMap map = getActionMap();
        map.put(DefaultEditorKit.copyAction, ExplorerUtils.actionCopy(em));
        map.put(DefaultEditorKit.cutAction, ExplorerUtils.actionCut(em));
        map.put(DefaultEditorKit.pasteAction, ExplorerUtils.actionPaste(em));
        map.put("delete", ExplorerUtils.actionDelete(em, true));

        associateLookup(ExplorerUtils.createLookup(em, map));

    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    private class ScoreChildFactory extends ChildFactory.Detachable<Score> implements LookupListener {

        Result<Score> currentScores;

        @Override
        protected void addNotify() {
            currentScores = CentralLookup.getDefault().lookupResult(Score.class);
            currentScores.addLookupListener(this);
        }

        @Override
        protected void removeNotify() {
            currentScores.removeLookupListener(this);
        }

        @Override
        protected boolean createKeys(List<Score> list) {
            for (Score score : currentScores.allInstances()) {
                list.add(score);
            }
            return true;
        }

        @Override
        protected Node createNodeForKey(Score key) {
            OneScoreNode node = null;
            try {
                node = new OneScoreNode(key);
            } catch (IntrospectionException ex) {
                Exceptions.printStackTrace(ex);
            }
            return node;
        }

        @Override
        public void resultChanged(LookupEvent le) {
            refresh(true);
        }
    }

    public class OneScoreNode extends BeanNode implements PropertyChangeListener {

        private final InstanceContent ic;

        public OneScoreNode(Score score) throws IntrospectionException {
            this(score, new InstanceContent());
        }

        public OneScoreNode(Score score, InstanceContent ic) throws IntrospectionException {
            super(score, Children.LEAF, new AbstractLookup(ic));
            this.ic = ic;
            setDisplayName(score.getTitle());
            score.addPropertyChangeListener(this);
            ic.add(score);
        }
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("title")) {
                setDisplayName(evt.getNewValue().toString());
            }
        }

//        @Override
//        public Action[] getActions(boolean context) {
//            List<? extends Action> scoreActions = Utilities.actionsForPath("Actions/Score");
//            return scoreActions.toArray(new Action[scoreActions.size()]);
//        }

        @Override
        public boolean canRename() {
            return true;
        }
        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    Result<Score> scoreResult = null;
    
    @Override
    public void componentOpened() {
        scoreResult = Utilities.actionsGlobalContext().lookupResult(Score.class);
        scoreResult.addLookupListener(this);
    }

    @Override
    public void componentClosed() {
        scoreResult.removeLookupListener(this);
    }
    
    @Override
    public void resultChanged(LookupEvent le) {
        if(!scoreResult.allInstances().isEmpty()){
            Score score = scoreResult.allInstances().iterator().next();
            for(Node node : em.getRootContext().getChildren().getNodes()){
                if (node.getDisplayName().equals(score.getTitle())){
//                    try {
////                        em.setSelectedNodes(new Node[]{node});
//                    } catch (PropertyVetoException ex) {
//                        Exceptions.printStackTrace(ex);
//                    }
                }
            }
        }
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
