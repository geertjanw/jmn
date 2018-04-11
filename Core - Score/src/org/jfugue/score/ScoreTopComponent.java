package org.jfugue.score;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.jfugue.Pattern;
import org.jfugue.Player;
import org.musician.api.*;
import org.openide.awt.StatusDisplayer;
import org.openide.cookies.PrintCookie;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Lookup.Result;
import org.openide.util.*;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

@TopComponent.Description(preferredID = "ScoreTopComponent", persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@NbBundle.Messages("CTL_ScoreAction=Score, CTL_ScoreTopComponent=Score Window")
public class ScoreTopComponent extends TopComponent implements LookupListener, PropertyChangeListener {

    private final InstanceContent generalContent = new InstanceContent();
    private final CentralLookup centralLookup = CentralLookup.getDefault();
    public static final String ICON_PATH = "org/netbeans/modules/musician/resources/stave-16.png";
    private Instrument instrument = new Instrument("Piano", "Acoustic Grand Piano", "ACOUSTIC_GRAND", 1);
    private Score score = new Score();
    private double duration = 0.25;
    private boolean rest = false;
    private boolean dotted = false;
    private boolean flat = false;
    private boolean sharp = false;
    private boolean restore = false;
    private SingleStave stave;
    private Vector<SingleStave> vStave = new Vector<>();
    private Vector<Note> selectedNotes = new Vector<>();
    private int count = 0;
    private JLabel label;
    private NoteWrapper noteWrapper;
//    private int newPitch;
    private double newDuration;

    public ScoreTopComponent() {
        this("Untitled", 'G', "C maj", 4, 4, 100, 60);
    }

    public ScoreTopComponent(
            String title,
            char clef,
            String keysignature,
            Integer rythmcount,
            Integer rythmunit,
            Integer volume,
            Integer tempo) {

        initComponents();

        toolbar.add(btnWholeNote);
        toolbar.add(btnHalfNote);
        toolbar.add(btnQuarterNote);
        toolbar.add(btnEighthNote);
        toolbar.add(btnSixteenthNote);

        toolbar.add(new JToolBar.Separator());

        toolbar.add(btnWholeRest);
        toolbar.add(btnHalfRest);
        toolbar.add(btnQuarterRest);
        toolbar.add(btnEighthRest);
        toolbar.add(btnSixteenthRest);

        toolbar.add(new JToolBar.Separator());

        toolbar.add(btnDotted);

        toolbar.add(new JToolBar.Separator());

        toolbar.add(btnClear);
        toolbar.add(btnFlat);
        toolbar.add(btnSharp);
        toolbar.add(btnRestore);

        toolbar.add(new JToolBar.Separator());

        toolbar.add(btnPlay);
        toolbar.add(btnStop);
        toolbar.add(btnBegin);
        toolbar.add(btnEnd);
        toolbar.add(btnSave);
        setSliderProperties(ctempo, 20, 480, tempo);
        toolbar.add(ctempo);

        toolbar.add(new JToolBar.Separator());

        toolbar.add(messageArea);

        centralLookup.add(score);
        generalContent.add(score);
        centralLookup.add(instrument);
        generalContent.add(instrument);
        newDuration = 0.25;


        associateLookup(
                new ProxyLookup(new Lookup[]{
                    new AbstractLookup(generalContent),
                    centralLookup,
                    new ScorePrintNode().getLookup()}));

        score.setTitle(title);
        score.setClef(clef);
        score.setKeySignature(keysignature);
        score.setRythmCount(rythmcount);
        score.setRythmUnit(rythmunit);
        score.setVolume(volume);
        score.setTempo(tempo);

        score.addPropertyChangeListener(this);

        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
        setDisplayName(title);

        addStave();

        this.setVisible(true);

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpNotesAndRests = new javax.swing.ButtonGroup();
        grpModifiers = new javax.swing.ButtonGroup();
        btnWholeNote = new javax.swing.JToggleButton();
        btnHalfNote = new javax.swing.JToggleButton();
        btnQuarterNote = new javax.swing.JToggleButton();
        btnEighthNote = new javax.swing.JToggleButton();
        btnSixteenthNote = new javax.swing.JToggleButton();
        btnWholeRest = new javax.swing.JToggleButton();
        btnHalfRest = new javax.swing.JToggleButton();
        btnQuarterRest = new javax.swing.JToggleButton();
        btnEighthRest = new javax.swing.JToggleButton();
        btnSixteenthRest = new javax.swing.JToggleButton();
        btnDotted = new javax.swing.JToggleButton();
        btnClear = new javax.swing.JToggleButton();
        btnFlat = new javax.swing.JToggleButton();
        btnSharp = new javax.swing.JToggleButton();
        btnRestore = new javax.swing.JToggleButton();
        btnPlay = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnBegin = new javax.swing.JButton();
        btnEnd = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        messageArea = new javax.swing.JLabel();
        ctempo = new javax.swing.JSlider();
        pnlScore = new javax.swing.JScrollPane();
        pnlStave = new javax.swing.JPanel();
        toolbar = new javax.swing.JToolBar();

        grpNotesAndRests.add(btnWholeNote);
        btnWholeNote.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/whole-note-24.png", false));
        btnWholeNote.setToolTipText("Whole note");
        btnWholeNote.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnWholeNote.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnWholeNote.setMaximumSize(new java.awt.Dimension(22, 28));
        btnWholeNote.setMinimumSize(new java.awt.Dimension(22, 28));
        btnWholeNote.setPreferredSize(new java.awt.Dimension(22, 28));
        btnWholeNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWholeNoteActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnHalfNote);
        btnHalfNote.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/half-note-24.png", false));
        btnHalfNote.setToolTipText("Half note");
        btnHalfNote.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnHalfNote.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnHalfNote.setMaximumSize(new java.awt.Dimension(22, 28));
        btnHalfNote.setMinimumSize(new java.awt.Dimension(22, 28));
        btnHalfNote.setPreferredSize(new java.awt.Dimension(22, 28));
        btnHalfNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHalfNoteActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnQuarterNote);
        btnQuarterNote.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/quarter-note-24.png", false));
        btnQuarterNote.setSelected(true);
        btnQuarterNote.setToolTipText("Quarter note");
        btnQuarterNote.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnQuarterNote.setIconTextGap(0);
        btnQuarterNote.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnQuarterNote.setMaximumSize(new java.awt.Dimension(22, 28));
        btnQuarterNote.setMinimumSize(new java.awt.Dimension(22, 28));
        btnQuarterNote.setPreferredSize(new java.awt.Dimension(22, 28));
        btnQuarterNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuarterNoteActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnEighthNote);
        btnEighthNote.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/eighth-note-24.png", false));
        btnEighthNote.setToolTipText("Eighth note");
        btnEighthNote.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEighthNote.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEighthNote.setMaximumSize(new java.awt.Dimension(22, 28));
        btnEighthNote.setMinimumSize(new java.awt.Dimension(22, 28));
        btnEighthNote.setPreferredSize(new java.awt.Dimension(22, 28));
        btnEighthNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEighthNoteActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnSixteenthNote);
        btnSixteenthNote.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/sixteenth-note-24.png", false));
        btnSixteenthNote.setToolTipText("Sixteenth note");
        btnSixteenthNote.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSixteenthNote.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSixteenthNote.setMaximumSize(new java.awt.Dimension(22, 28));
        btnSixteenthNote.setMinimumSize(new java.awt.Dimension(22, 28));
        btnSixteenthNote.setPreferredSize(new java.awt.Dimension(22, 28));
        btnSixteenthNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSixteenthNoteActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnWholeRest);
        btnWholeRest.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/whole-rest-24.png", false));
        btnWholeRest.setToolTipText("Whole rest");
        btnWholeRest.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnWholeRest.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnWholeRest.setMaximumSize(new java.awt.Dimension(22, 28));
        btnWholeRest.setMinimumSize(new java.awt.Dimension(22, 28));
        btnWholeRest.setPreferredSize(new java.awt.Dimension(22, 28));
        btnWholeRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWholeRestActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnHalfRest);
        btnHalfRest.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/half-rest-24.png", false));
        btnHalfRest.setToolTipText("Half rest");
        btnHalfRest.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnHalfRest.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnHalfRest.setMaximumSize(new java.awt.Dimension(22, 28));
        btnHalfRest.setMinimumSize(new java.awt.Dimension(22, 28));
        btnHalfRest.setPreferredSize(new java.awt.Dimension(22, 28));
        btnHalfRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHalfRestActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnQuarterRest);
        btnQuarterRest.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/quarter-rest-24.png", false));
        btnQuarterRest.setToolTipText("Quarter rest");
        btnQuarterRest.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnQuarterRest.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnQuarterRest.setMaximumSize(new java.awt.Dimension(22, 28));
        btnQuarterRest.setMinimumSize(new java.awt.Dimension(22, 28));
        btnQuarterRest.setPreferredSize(new java.awt.Dimension(22, 28));
        btnQuarterRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuarterRestActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnEighthRest);
        btnEighthRest.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/eighth-note-24.png", false));
        btnEighthRest.setToolTipText("Eighth rest");
        btnEighthRest.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEighthRest.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEighthRest.setMaximumSize(new java.awt.Dimension(22, 28));
        btnEighthRest.setMinimumSize(new java.awt.Dimension(22, 28));
        btnEighthRest.setPreferredSize(new java.awt.Dimension(22, 28));
        btnEighthRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEighthRestActionPerformed(evt);
            }
        });

        grpNotesAndRests.add(btnSixteenthRest);
        btnSixteenthRest.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/sixteenth-rest-24.png", false));
        btnSixteenthRest.setToolTipText("Sixteenth rest");
        btnSixteenthRest.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSixteenthRest.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSixteenthRest.setMaximumSize(new java.awt.Dimension(22, 28));
        btnSixteenthRest.setMinimumSize(new java.awt.Dimension(22, 28));
        btnSixteenthRest.setPreferredSize(new java.awt.Dimension(22, 28));
        btnSixteenthRest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSixteenthRestActionPerformed(evt);
            }
        });

        btnDotted.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/ball16.png", false));
        btnDotted.setToolTipText("Dotted note or rest");
        btnDotted.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnDotted.setEnabled(false);
        btnDotted.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnDotted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDottedActionPerformed(evt);
            }
        });

        grpModifiers.add(btnClear);
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnClear.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(btnClear, "C");
        btnClear.setToolTipText("Clear");
        btnClear.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnClear.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnClear.setMaximumSize(new java.awt.Dimension(22, 28));
        btnClear.setMinimumSize(new java.awt.Dimension(22, 28));
        btnClear.setPreferredSize(new java.awt.Dimension(22, 28));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        grpModifiers.add(btnFlat);
        btnFlat.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/flat24.png", false));
        btnFlat.setToolTipText("Flat");
        btnFlat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFlat.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFlat.setMaximumSize(new java.awt.Dimension(22, 28));
        btnFlat.setMinimumSize(new java.awt.Dimension(22, 28));
        btnFlat.setPreferredSize(new java.awt.Dimension(22, 28));
        btnFlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlatActionPerformed(evt);
            }
        });

        grpModifiers.add(btnSharp);
        btnSharp.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/sharp24.png", false));
        btnSharp.setToolTipText("Sharp");
        btnSharp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSharp.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSharp.setMaximumSize(new java.awt.Dimension(22, 28));
        btnSharp.setMinimumSize(new java.awt.Dimension(22, 28));
        btnSharp.setPreferredSize(new java.awt.Dimension(22, 28));
        btnSharp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSharpActionPerformed(evt);
            }
        });

        grpModifiers.add(btnRestore);
        btnRestore.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/restore24.png", false));
        btnRestore.setToolTipText("Restore");
        btnRestore.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnRestore.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnRestore.setMaximumSize(new java.awt.Dimension(22, 28));
        btnRestore.setMinimumSize(new java.awt.Dimension(22, 28));
        btnRestore.setPreferredSize(new java.awt.Dimension(22, 28));
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        btnPlay.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/Play16.gif", false));
        btnPlay.setToolTipText("Play");
        btnPlay.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnPlay.setMaximumSize(new java.awt.Dimension(25, 28));
        btnPlay.setMinimumSize(new java.awt.Dimension(25, 28));
        btnPlay.setPreferredSize(new java.awt.Dimension(25, 28));
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnStop.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/Stop16.gif", false));
        btnStop.setToolTipText("Stop");
        btnStop.setEnabled(false);
        btnStop.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnStop.setMaximumSize(new java.awt.Dimension(25, 28));
        btnStop.setMinimumSize(new java.awt.Dimension(25, 28));
        btnStop.setPreferredSize(new java.awt.Dimension(25, 28));

        btnBegin.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/StepBack16.gif", false));
        btnBegin.setToolTipText("Begin");
        btnBegin.setEnabled(false);
        btnBegin.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnBegin.setMaximumSize(new java.awt.Dimension(25, 28));
        btnBegin.setMinimumSize(new java.awt.Dimension(25, 28));
        btnBegin.setPreferredSize(new java.awt.Dimension(25, 28));

        btnEnd.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/StepForward16.gif", false));
        btnEnd.setToolTipText("End");
        btnEnd.setEnabled(false);
        btnEnd.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnEnd.setMaximumSize(new java.awt.Dimension(25, 28));
        btnEnd.setMinimumSize(new java.awt.Dimension(25, 28));
        btnEnd.setPreferredSize(new java.awt.Dimension(25, 28));

        btnSave.setIcon(ImageUtilities.loadImageIcon("/org/netbeans/modules/musician/resources/Save16.gif", false));
        btnSave.setToolTipText("Play");
        btnSave.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSave.setMaximumSize(new java.awt.Dimension(25, 28));
        btnSave.setMinimumSize(new java.awt.Dimension(25, 28));
        btnSave.setPreferredSize(new java.awt.Dimension(25, 28));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        messageArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        messageArea.setMaximumSize(new java.awt.Dimension(512, 28));
        messageArea.setMinimumSize(new java.awt.Dimension(512, 28));
        messageArea.setPreferredSize(new java.awt.Dimension(512, 28));

        ctempo.setFont(new java.awt.Font("Serif", 1, 12)); // NOI18N
        ctempo.setMajorTickSpacing(40);
        ctempo.setMaximum(480);
        ctempo.setMinimum(20);
        ctempo.setMinorTickSpacing(3);
        ctempo.setPaintTicks(true);
        ctempo.setToolTipText("Answer Staff Tempo");
        ctempo.setAutoscrolls(true);
        ctempo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        ctempo.setMaximumSize(new java.awt.Dimension(320, 32));
        ctempo.setMinimumSize(new java.awt.Dimension(320, 32));
        ctempo.setPreferredSize(new java.awt.Dimension(320, 32));
        ctempo.setValueIsAdjusting(true);
        ctempo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ctempoStateChanged(evt);
            }
        });

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        pnlScore.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        pnlStave.setBackground(new java.awt.Color(255, 255, 255));
        pnlStave.setAlignmentX(4.0F);
        pnlStave.setLayout(new java.awt.GridLayout(0, 1));
        pnlScore.setViewportView(pnlStave);

        add(pnlScore, java.awt.BorderLayout.CENTER);

        toolbar.setRollover(true);
        toolbar.setAutoscrolls(true);
        add(toolbar, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
//        getKeyboardTopComponent().findInstance().setScoreTopComponent(this);
    }//GEN-LAST:event_formComponentShown

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
//        getKeyboardTopComponent().findInstance().setScoreTopComponent(null);
    }//GEN-LAST:event_formComponentHidden

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
//        getKeyboardTopComponent().findInstance().setScoreTopComponent(this);
    }//GEN-LAST:event_formFocusGained

    private void ctempoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ctempoStateChanged
        int tempo = ctempo.getValue();
        TitledBorder tb = (TitledBorder) ctempo.getBorder();
        String s = tb.getTitle();
        tb.setTitle(s.valueOf(tempo));
        score.setReplaceTempo(tempo);
        ctempo.repaint();
    }//GEN-LAST:event_ctempoStateChanged

    private void setSliderProperties(JSlider slider, int min, int max, int value) {
        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setValue(value);
        TitledBorder tb = new TitledBorder(new EtchedBorder());
        tb.setTitle(((Integer) value).toString());
        slider.setBorder(tb);
    }

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        Player player = new Player();
        Pattern pattern = new Pattern(score.toJFugue());
        count = count + 1;
        File midi = new File("midi-file" + count + ".midi");
        try {
            player.saveMidi(pattern, midi);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        player.close();

        StatusDisplayer.getDefault().setStatusText("Successfully saved to " + midi.getAbsolutePath());

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed

        Player player = new Player();
        Pattern pattern = new Pattern(score.toJFugue());
        player.play(pattern);
        player.close();

        StatusDisplayer.getDefault().setStatusText("Successfully played!");

    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestoreActionPerformed
        if (btnRestore.isSelected()) {
            setRestore(true);
            setSharp(false);
            setFlat(false);
        } else {
            setRestore(false);
        }
    }//GEN-LAST:event_btnRestoreActionPerformed

    private void btnSharpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSharpActionPerformed
        if (btnSharp.isSelected()) {
            setRestore(false);
            setSharp(true);
            setFlat(false);
        } else {
            setSharp(false);
        }
    }//GEN-LAST:event_btnSharpActionPerformed

    private void btnFlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlatActionPerformed
        if (btnFlat.isSelected()) {
            setRestore(false);
            setSharp(false);
            setFlat(true);
        } else {
            setFlat(false);
        }
    }//GEN-LAST:event_btnFlatActionPerformed

    private void btnDottedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDottedActionPerformed
        if (btnDotted.isSelected()) {
            setDotted(true);
        } else {
            setDotted(false);
        }
    }//GEN-LAST:event_btnDottedActionPerformed

    private void btnSixteenthRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSixteenthRestActionPerformed
        duration = 1.0 / 16;
        setRest(true);
        newDuration = duration;
    }//GEN-LAST:event_btnSixteenthRestActionPerformed

    private void btnEighthRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEighthRestActionPerformed
        duration = 0.125;
        setRest(true);
        newDuration = duration;
    }//GEN-LAST:event_btnEighthRestActionPerformed

    private void btnQuarterRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuarterRestActionPerformed
        duration = 0.25;
        setRest(true);
        newDuration = duration;
    }//GEN-LAST:event_btnQuarterRestActionPerformed

    private void btnHalfRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHalfRestActionPerformed
        duration = 0.5;
        setRest(true);
        newDuration = duration;
    }//GEN-LAST:event_btnHalfRestActionPerformed

    private void btnWholeRestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWholeRestActionPerformed
        duration = 1.0;
        setRest(true);
        newDuration = duration;
    }//GEN-LAST:event_btnWholeRestActionPerformed

    private void btnSixteenthNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSixteenthNoteActionPerformed
        duration = 1.0 / 16;
        setRest(false);
        newDuration = duration;
    }//GEN-LAST:event_btnSixteenthNoteActionPerformed

    private void btnEighthNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEighthNoteActionPerformed
        duration = 0.125;
        setRest(false);
        newDuration = duration;
    }//GEN-LAST:event_btnEighthNoteActionPerformed

    private void btnQuarterNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuarterNoteActionPerformed
        duration = 0.25;
        setRest(false);
        newDuration = duration;
    }//GEN-LAST:event_btnQuarterNoteActionPerformed

    private void btnHalfNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHalfNoteActionPerformed
        duration = 0.5;
        setRest(false);
        newDuration = duration;
    }//GEN-LAST:event_btnHalfNoteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        if (btnClear.isSelected()) {
            setFlat(false);
            setSharp(false);
            setRestore(false);
            btnClear.setForeground(Color.BLUE);
        } else {
            btnClear.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnWholeNoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWholeNoteActionPerformed
        duration = 1.0;
        setRest(false);
        newDuration = duration;
    }//GEN-LAST:event_btnWholeNoteActionPerformed

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized

        for (int i = 0; i < vStave.size(); i++) {
            stave = (SingleStave) vStave.elementAt(i);
            stave.setWidth(this.getWidth());
        }
        repaint();

    }//GEN-LAST:event_formComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBegin;
    private javax.swing.JToggleButton btnClear;
    private javax.swing.JToggleButton btnDotted;
    private javax.swing.JToggleButton btnEighthNote;
    private javax.swing.JToggleButton btnEighthRest;
    private javax.swing.JButton btnEnd;
    private javax.swing.JToggleButton btnFlat;
    private javax.swing.JToggleButton btnHalfNote;
    private javax.swing.JToggleButton btnHalfRest;
    private javax.swing.JButton btnPlay;
    private javax.swing.JToggleButton btnQuarterNote;
    private javax.swing.JToggleButton btnQuarterRest;
    private javax.swing.JToggleButton btnRestore;
    private javax.swing.JButton btnSave;
    private javax.swing.JToggleButton btnSharp;
    private javax.swing.JToggleButton btnSixteenthNote;
    private javax.swing.JToggleButton btnSixteenthRest;
    private javax.swing.JButton btnStop;
    private javax.swing.JToggleButton btnWholeNote;
    private javax.swing.JToggleButton btnWholeRest;
    private javax.swing.JSlider ctempo;
    private javax.swing.ButtonGroup grpModifiers;
    private javax.swing.ButtonGroup grpNotesAndRests;
    private javax.swing.JLabel messageArea;
    private javax.swing.JScrollPane pnlScore;
    private javax.swing.JPanel pnlStave;
    private javax.swing.JToolBar toolbar;
    // End of variables declaration//GEN-END:variables
    CentralLookup.Result<Instrument> instrumentResult;
    Result<NoteWrapper> publishedNotes;
    Result<Score> currentScoreResult;
//    Result<Double> publishedPitches;

    @Override
    public void resultChanged(LookupEvent le) {
        if (publishedNotes != null && !publishedNotes.allInstances().isEmpty()) {
            noteWrapper = publishedNotes.allInstances().iterator().next();
//            newPitch = publishedPitches.allInstances().iterator().next();
//            StatusDisplayer.getDefault().setStatusText("Pitch" + newPitch);

            stave.validateNewNote(noteWrapper.getNote(), noteWrapper.getPitch(), newDuration);
            stave.addNote(noteWrapper.getNote());
        }
//        
//        if (currentScoreResult.allInstances().iterator().hasNext()) {
//            TopComponent[] scoreTCs = WindowManager.getDefault().findMode("editor").getTopComponents();
//            Score scoreFromLookup = currentScoreResult.allInstances().iterator().next();
//            for (TopComponent scoreTC : scoreTCs) {
//                if (scoreFromLookup == scoreTC.getLookup().lookup(Score.class)) {
//                    requestActive();
//                }
//            }
//        }

//        for (Instrument instrument : instrumentResult.allInstances()) {
//            instrument = instrumentResult.allInstances().iterator().next();
//            repaint();
//        }
//        if (!openScores.allInstances().isEmpty() && openScores.allInstances() != null) {
//            for (Node s : openScores.allInstances()) {
//                s.addPropertyChangeListener(this);
//                Mode editorMode = WindowManager.getDefault().findMode("editor");
//                TopComponent[] opened = WindowManager.getDefault().getOpenedTopComponents(editorMode);
//                for (TopComponent tc : opened) {
////                    if (tc.getDisplayName().equals(s.getDisplayName())) {
////                        tc.requestActive();
////                    }
//                }
//            }
//        }

    }

    @Override
    public void componentShowing() {
        publishedNotes = Utilities.actionsGlobalContext().lookupResult(NoteWrapper.class);
        publishedNotes.addLookupListener(this);
//        publishedPitches = Utilities.actionsGlobalContext().lookupResult(Integer.class);
//        publishedPitches.addLookupListener(this);
    }

    @Override
    protected void componentHidden() {
        publishedNotes.removeLookupListener(this);
//        currentScoreResult.removeLookupListener(this);
//        publishedPitches.removeLookupListener(this);
    }

    @Override
    public void componentOpened() {
        instrumentResult = CentralLookup.getDefault().lookupResult(Instrument.class);
        instrumentResult.addLookupListener(this);
        currentScoreResult = Utilities.actionsGlobalContext().lookupResult(Score.class);
        currentScoreResult.addLookupListener(this);
    }

    @Override
    protected void componentClosed() {
        centralLookup.remove(score);
        generalContent.remove(score);
        currentScoreResult.removeLookupListener(this);

        instrumentResult.removeLookupListener(this);
        publishedNotes.removeLookupListener(this);
    }

    public void addStave() {

        stave = new SingleStave(this, this.getWidth(), generalContent, instrument);
        generalContent.add(stave);
        vStave.addElement(stave);
        pnlStave.add(stave, BorderLayout.CENTER);

        validate();
        repaint();

        Rectangle rect = stave.getBounds();
        pnlStave.scrollRectToVisible(rect);

    }

    public void addFooter() {

        java.util.Date date = new java.util.Date();

        String s = "<html><pre>     <b>Title:</b> " + getName() + "<br>     <b>Print Date:</b> "
                + date + "<br>     <b>Composer:</b> "
                + System.getProperty("user.name") + "</pre></html>";

        label = new JLabel();
        label.setText(s);
        pnlStave.add(label, BorderLayout.AFTER_LAST_LINE);

        validate();
        repaint();

        Rectangle rect = stave.getBounds();
        pnlStave.scrollRectToVisible(rect);

    }

    public void removeFooter() {

        if (label != null) {
            pnlStave.remove(label);
        }

        validate();
        repaint();

        Rectangle rect = stave.getBounds();
        pnlStave.scrollRectToVisible(rect);

    }

    /*
     * public void selectNotes(Vector<Note> snotes) { selectedNotes = snotes;
     * for (int i=0; i<vStave.size(); i++) { Vector notes =
     * vStave.elementAt(i).getNotes(); for (int j=0; j<notes.size(); j++) {
     * ((Note) notes.elementAt(j)).setSelected(false); } } if (selectedNotes !=
     * null) { for (int j=0; j<selectedNotes.size(); j++) { ((Note)
     * selectedNotes.elementAt(j)).setSelected(true); } } }
     */
    public void clearSelectedNotes() {
        for (int j = 0; j < selectedNotes.size(); j++) {
            ((Note) selectedNotes.elementAt(j)).setSelected(false);
        }
        selectedNotes.clear();
    }

    public void addSelectedNote(Note note) {
        note.setSelected(true);
        selectedNotes.add(note);
    }

    public Vector<Note> getSelectedNotes() {
        return selectedNotes;
    }

    public void update(Graphics g) {
        validate();
        paint(g);
    }

    public double getDuration() {
        return duration;
    }

//    public void setInstrument(Instrument instr) {
//        cl.remove(instrument);
//        generalContent.remove(instrument);
//        this.instrument = instr;
//        score.setInstrument(instrument);
//        cl.add(instrument);
//        generalContent.add(instrument);
//    }
    public boolean isRest() {
        return rest;
    }

    public void setRest(boolean rest) {
        this.rest = rest;
    }

    public boolean isDotted() {
        return dotted;
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    public boolean isFlat() {
        return flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }

    public boolean isSharp() {
        return sharp;
    }

    public void setSharp(boolean sharp) {
        this.sharp = sharp;
    }

    public boolean isRestore() {
        return restore;
    }

    public void setRestore(boolean restore) {
        this.restore = restore;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Stave getStave() {
        return stave;
    }

    public void setErrorMessage(String newMsg) {
        messageArea.setForeground(Color.RED);
        messageArea.setText(newMsg);
    }

    public String getErrorMessage() {
        return messageArea.getText();
    }

    public void setInformationalMessage(String newMsg) {
        messageArea.setForeground(Color.BLACK);
        messageArea.setText(newMsg);
    }

    public String getInformationalMessage() {
        return messageArea.getText();
    }

    public void clearMessageArea() {
        messageArea.setForeground(Color.BLACK);
        messageArea.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("title")) {
            setDisplayName(score.getTitle());
            requestActive();
        }
    }

    //Print functionality:
    private class ScorePrintNode extends AbstractNode {

        public ScorePrintNode() {
            super(Children.LEAF);
//            CookieSet cookies = getCookieSet();
            generalContent.add(new ScorePrintCookie());
        }
    }

    class ScorePrintCookie implements PrintCookie, Printable, ImageObserver {

        /*
         * image to print
         */
        protected RenderedImage image;

        /**
         * Prepare the image to fit on the given page, within the given margins.
         * Returns null if it is unable to prepare the image for the given page.
         * Throws a IllegalArgumentException if the page were too small for the
         * image.
         *
         */
        protected RenderedImage prepareImage(PageFormat pf) throws IllegalArgumentException {
            try {
                AffineTransform af = new AffineTransform();
                pf.setOrientation(PageFormat.LANDSCAPE);
                image = (RenderedImage) getImage(pnlScore);
                /**
                 * notify if too big for page *
                 */
                if (pf.getImageableWidth() - pf.getImageableX() < image.getWidth()
                        || pf.getImageableHeight() - pf.getImageableY() < image.getHeight()) {
                    throw new IllegalArgumentException("Page too small for image"); //NOI18N
                }
                AffineTransformOp afo = new AffineTransformOp(
                        af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                BufferedImage o = (BufferedImage) image;
                BufferedImage i = new BufferedImage(o.getWidth() + (int) pf.getImageableX(),
                        o.getHeight() + (int) pf.getImageableY(), o.getType());
                return afo.filter((BufferedImage) image, i);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        /**
         * Print the content of the object.
         */
        public void print() {
            addFooter();
            Printable printable = new ScorePrintCookie();
            new PrintPreview(printable, getName());
            removeFooter();
        }

        /*
         * Implements Printable
         */
        public int print(Graphics graphics, PageFormat pageFormat, int page)
                throws PrinterException {
            if (page != 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2 = (Graphics2D) graphics;
            if (image == null) {
                /**
                 * prepareImage() failed, most probably cause is image does not
                 * implement RenderedImage, just draw the image then.
                 *
                 */
                graphics.drawImage(getImage(pnlScore), (int) pageFormat.getImageableX(),
                        (int) pageFormat.getImageableY(), this);
            } else {
                g2.drawRenderedImage(image, new AffineTransform());
            }
            return Printable.PAGE_EXISTS;
        }

        public boolean imageUpdate(java.awt.Image image, int flags, int param2,
                int param3, int param4, int param5) {
            return false;
        }
    }

    private BufferedImage getImage(Container container) {
        BufferedImage image = new BufferedImage(container.getWidth(),
                container.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        container.paint(g2);
        g2.dispose();
        return image;
    }

    public void doClickBtnCompositionSixteenthNote() {
        btnSixteenthNote.doClick();
    }

    public void doClickBtnCompositionEighthNote() {
        btnEighthNote.doClick();
    }

    public void doClickBtnCompositionQuarterNote() {
        newDuration = 0.25;
        btnQuarterNote.doClick();
    }

    public void doClickBtnCompositionHalfNote() {
        newDuration = 0.5;
        btnHalfNote.doClick();
    }

    public void doClickBtnCompositionWholeNote() {
        newDuration = 1;
        btnWholeNote.doClick();
    }
}
