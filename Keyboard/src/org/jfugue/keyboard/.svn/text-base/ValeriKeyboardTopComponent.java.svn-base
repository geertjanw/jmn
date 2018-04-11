/*
 * KeyboardTopComponent.java
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.windows.TopComponent;

@ConvertAsProperties(dtd = "-//org.netbeans.modules.musician//ValeriKeyboardTopComponent//EN", autostore = false)
@TopComponent.Description(preferredID = "ValeriKeyboardTopComponent", persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "output", openAtStartup = true, position = 10)
@TopComponent.OpenActionRegistration(displayName = "#CTL_KeyboardAction", preferredID = "ValeriKeyboardTopComponent")
@ActionID(category = "Window", id = "org.jfugue.keyboard.ValeriKeyboardTopComponent")
@ActionReference(path = "Menu/Window", position = 10)
public final class ValeriKeyboardTopComponent extends TopComponent {

    private static ValeriKeyboardTopComponent instance;
    /**
     * path to the icon used by the component and its open action
     */
    static final String ICON_PATH = "org/jfugue/keyboard/keyboard-16.png";
    private static final String PREFERRED_ID = "ValeriKeyboardTopComponent";
    private Piano localKeyboard;
    private KeyboardListener listener;
    public final int ON = 0;
    public final int OFF = 1;
    public final int PROGRAM = 192;
    public final int NOTEON = 144;
    public final int NOTEOFF = 128;
    public final int SUSTAIN = 64;
    public final int REVERB = 91;
    private MidiDevice.Info[] infos;
    private MidiDevice device;
    private MidiChannel channel;
    private Sequencer sequencer;
    private Sequence sequence;
    private Synthesizer synthesizer;
    private Vector sequencerInfos = new Vector();
    private Vector synthesizerInfos = new Vector();
    private Vector transmitterInfos = new Vector();
    private Vector receiverInfos = new Vector();
    private Instrument instruments[];
    private ChannelData channels[];
    private static ChannelData cc;                     // current channel
    private Controls controls;
    private long startTime;
    private boolean midiPortInA;
    private boolean midiPortInB;
    private boolean midiPortOutA;
    private boolean midiPortOutB;
    private MidiDevice inputPortA;
    private MidiDevice outputPortA;
    private MidiDevice inputPortB;
    private MidiDevice outputPortB;
    private MidiDevice inputPort;
    private MidiDevice outputPort;
    private InstrumentMidiReceiver midiRx = null;
    private Transmitter midiInTx = null;
    private final InstanceContent newNoteContent = new InstanceContent();
    private final InstanceContent newPitchContent = new InstanceContent();

    public ValeriKeyboardTopComponent() {
        initComponents();

        localKeyboard = new Piano(this, newNoteContent);
        keyboardPanel.add(localKeyboard, BorderLayout.CENTER);

        midiOpen();

        controls = new Controls();

        validate();
        repaint();

        // Setup External Midikeyboard
        InstrumentMidiReceiver midiRx = new InstrumentMidiReceiver(System.out);

        setName(NbBundle.getMessage(ValeriKeyboardTopComponent.class, "CTL_KeyboardTopComponent"));
        setToolTipText(NbBundle.getMessage(ValeriKeyboardTopComponent.class, "HINT_KeyboardTopComponent"));
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));

        listener = new KeyboardListener();
        addKeyListener(listener);

        associateLookup(new ProxyLookup(new AbstractLookup(newNoteContent), new AbstractLookup(newPitchContent)));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        octaveBtnGrp = new javax.swing.ButtonGroup();
        keyboardControlsToolbar = new javax.swing.JToolBar();
        presS = new javax.swing.JSlider();
        reverbS = new javax.swing.JSlider();
        veloS = new javax.swing.JSlider();
        bendS = new javax.swing.JSlider();
        octavePanel = new javax.swing.JPanel();
        twoOctaves = new javax.swing.JToggleButton();
        fourOctaves = new javax.swing.JToggleButton();
        sixOctaves = new javax.swing.JToggleButton();
        mouseOverKB = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        keyNames = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        midiKeyNames = new javax.swing.JToggleButton();
        keyboardPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        keyboardControlsToolbar.setToolTipText("Piano Controls");
        keyboardControlsToolbar.setMaximumSize(new java.awt.Dimension(1000, 48));
        keyboardControlsToolbar.setMinimumSize(new java.awt.Dimension(14, 48));
        keyboardControlsToolbar.setPreferredSize(new java.awt.Dimension(800, 48));

        presS.setToolTipText("Pressure");
        presS.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        presS.setMaximumSize(new java.awt.Dimension(128, 32));
        presS.setMinimumSize(new java.awt.Dimension(128, 32));
        presS.setPreferredSize(new java.awt.Dimension(128, 32));
        keyboardControlsToolbar.add(presS);
        presS.getAccessibleContext().setAccessibleParent(keyboardControlsToolbar);

        reverbS.setToolTipText("Reverbration");
        reverbS.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        reverbS.setMaximumSize(new java.awt.Dimension(128, 32));
        reverbS.setMinimumSize(new java.awt.Dimension(128, 32));
        reverbS.setPreferredSize(new java.awt.Dimension(128, 32));
        keyboardControlsToolbar.add(reverbS);
        reverbS.getAccessibleContext().setAccessibleParent(keyboardControlsToolbar);

        veloS.setToolTipText("Velocity");
        veloS.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        veloS.setMaximumSize(new java.awt.Dimension(128, 32));
        veloS.setMinimumSize(new java.awt.Dimension(128, 32));
        veloS.setPreferredSize(new java.awt.Dimension(128, 32));
        keyboardControlsToolbar.add(veloS);
        veloS.getAccessibleContext().setAccessibleParent(keyboardControlsToolbar);

        bendS.setToolTipText("Pitch Bend");
        bendS.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder()));
        bendS.setMaximumSize(new java.awt.Dimension(128, 32));
        bendS.setMinimumSize(new java.awt.Dimension(128, 32));
        bendS.setPreferredSize(new java.awt.Dimension(128, 32));
        keyboardControlsToolbar.add(bendS);
        bendS.getAccessibleContext().setAccessibleParent(keyboardControlsToolbar);

        octavePanel.setToolTipText("Keyboard Octaves to Display");
        octavePanel.setMaximumSize(new java.awt.Dimension(137, 32));
        octavePanel.setMinimumSize(new java.awt.Dimension(137, 32));
        octavePanel.setPreferredSize(new java.awt.Dimension(137, 32));

        octaveBtnGrp.add(twoOctaves);
        twoOctaves.setFont(new java.awt.Font("Serif", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(twoOctaves, "2");
        twoOctaves.setToolTipText("Two Octaves");
        twoOctaves.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        twoOctaves.setMaximumSize(new java.awt.Dimension(24, 24));
        twoOctaves.setMinimumSize(new java.awt.Dimension(24, 24));
        twoOctaves.setPreferredSize(new java.awt.Dimension(24, 24));
        twoOctaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoOctavesActionPerformed(evt);
            }
        });
        octavePanel.add(twoOctaves);

        octaveBtnGrp.add(fourOctaves);
        fourOctaves.setFont(new java.awt.Font("Serif", 0, 12));
        fourOctaves.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(fourOctaves, "4");
        fourOctaves.setToolTipText("Four Octaves");
        fourOctaves.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        fourOctaves.setMaximumSize(new java.awt.Dimension(24, 24));
        fourOctaves.setMinimumSize(new java.awt.Dimension(24, 24));
        fourOctaves.setPreferredSize(new java.awt.Dimension(24, 24));
        fourOctaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourOctavesActionPerformed(evt);
            }
        });
        octavePanel.add(fourOctaves);

        octaveBtnGrp.add(sixOctaves);
        sixOctaves.setFont(new java.awt.Font("Serif", 0, 12));
        org.openide.awt.Mnemonics.setLocalizedText(sixOctaves, "6");
        sixOctaves.setToolTipText("Six Octaves");
        sixOctaves.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sixOctaves.setMaximumSize(new java.awt.Dimension(24, 24));
        sixOctaves.setMinimumSize(new java.awt.Dimension(24, 24));
        sixOctaves.setPreferredSize(new java.awt.Dimension(24, 24));
        sixOctaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixOctavesActionPerformed(evt);
            }
        });
        octavePanel.add(sixOctaves);

        keyboardControlsToolbar.add(octavePanel);

        org.openide.awt.Mnemonics.setLocalizedText(mouseOverKB, "Mouse Over");
        mouseOverKB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mouseOverKB.setMaximumSize(new java.awt.Dimension(64, 23));
        mouseOverKB.setMinimumSize(new java.awt.Dimension(64, 23));
        mouseOverKB.setPreferredSize(new java.awt.Dimension(64, 23));
        keyboardControlsToolbar.add(mouseOverKB);
        mouseOverKB.getAccessibleContext().setAccessibleParent(keyboardControlsToolbar);

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, "  ");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton1.setEnabled(false);
        keyboardControlsToolbar.add(jButton1);

        org.openide.awt.Mnemonics.setLocalizedText(keyNames, "Key Names");
        keyNames.setToolTipText("Show All Key Names");
        keyNames.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        keyNames.setMaximumSize(new java.awt.Dimension(64, 23));
        keyNames.setMinimumSize(new java.awt.Dimension(64, 23));
        keyNames.setPreferredSize(new java.awt.Dimension(64, 23));
        keyNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyNamesActionPerformed(evt);
            }
        });
        keyboardControlsToolbar.add(keyNames);

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, "  ");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.setEnabled(false);
        keyboardControlsToolbar.add(jButton2);

        org.openide.awt.Mnemonics.setLocalizedText(midiKeyNames, "Midi Names");
        midiKeyNames.setToolTipText("Show All Key Names");
        midiKeyNames.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        midiKeyNames.setMaximumSize(new java.awt.Dimension(64, 23));
        midiKeyNames.setMinimumSize(new java.awt.Dimension(64, 23));
        midiKeyNames.setPreferredSize(new java.awt.Dimension(64, 23));
        midiKeyNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                midiKeyNamesActionPerformed(evt);
            }
        });
        keyboardControlsToolbar.add(midiKeyNames);

        add(keyboardControlsToolbar, java.awt.BorderLayout.NORTH);

        keyboardPanel.setLayout(new java.awt.BorderLayout());
        keyboardPanel.add(jPanel1, java.awt.BorderLayout.SOUTH);

        add(keyboardPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void midiKeyNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_midiKeyNamesActionPerformed
        localKeyboard.showAllMidiNames(midiKeyNames.isSelected());
    }//GEN-LAST:event_midiKeyNamesActionPerformed

    private void keyNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyNamesActionPerformed
        localKeyboard.showAllNames(keyNames.isSelected());
    }//GEN-LAST:event_keyNamesActionPerformed

    private void sixOctavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixOctavesActionPerformed
        localKeyboard.setNumberOfOctaves(6);
    }//GEN-LAST:event_sixOctavesActionPerformed

    private void fourOctavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourOctavesActionPerformed
        localKeyboard.setNumberOfOctaves(4);
    }//GEN-LAST:event_fourOctavesActionPerformed

    private void twoOctavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoOctavesActionPerformed
        localKeyboard.setNumberOfOctaves(2);
    }//GEN-LAST:event_twoOctavesActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider bendS;
    private javax.swing.JToggleButton fourOctaves;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton keyNames;
    private javax.swing.JToolBar keyboardControlsToolbar;
    private javax.swing.JPanel keyboardPanel;
    private javax.swing.JToggleButton midiKeyNames;
    private javax.swing.JToggleButton mouseOverKB;
    private javax.swing.ButtonGroup octaveBtnGrp;
    private javax.swing.JPanel octavePanel;
    private javax.swing.JSlider presS;
    private javax.swing.JSlider reverbS;
    private javax.swing.JToggleButton sixOctaves;
    private javax.swing.JToggleButton twoOctaves;
    private javax.swing.JSlider veloS;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Do not use directly: reserved for *.settings files
     * only, i.e. deserialization routines; otherwise you could get a
     * non-deserialized instance. To obtain the singleton instance, use {@link findInstance}.
     */
    public static synchronized ValeriKeyboardTopComponent getDefault() {
        if (instance == null) {
            instance = new ValeriKeyboardTopComponent();
        }
        return instance;
    }

    /**
     * Obtain the KeyboardTopComponent instance. Never call {@link #getDefault}
     * directly!
     */
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

    public InstrumentMidiReceiver getMidiReceiver() {
        return midiRx;
    }

    public Piano getKeyboard() {
        return localKeyboard;
    }

    public void midiOpen() {
        try {
            if (synthesizer == null) {
                if ((synthesizer = MidiSystem.getSynthesizer()) == null) {
                    System.out.println("getSynthesizer() failed!");
                    return;
                }
            }
            synthesizer.open();
            sequencer = MidiSystem.getSequencer();
            sequence = new Sequence(Sequence.PPQ, 10);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        Soundbank sb = synthesizer.getDefaultSoundbank();

        if (sb != null) {
            instruments = synthesizer.getDefaultSoundbank().getInstruments();
            synthesizer.loadInstrument(instruments[0]);
        }

        MidiChannel midiChannels[] = synthesizer.getChannels();
        channels = new ChannelData[midiChannels.length];
        for (int i = 0; i < channels.length; i++) {
            channels[i] = new ChannelData(midiChannels[i], i);
        }
        cc = channels[0];

    }

    public void midiClose() {
        if (synthesizer != null) {
            synthesizer.close();
        }
        if (sequencer != null) {
            sequencer.close();
        }
        sequencer = null;
        synthesizer = null;
        instruments = null;
        channels = null;
    }

    public static void noteOn(int knum) {
        cc.channel.noteOn(knum, cc.velocity);
    }

    public static void noteOff(int knum) {
        cc.channel.noteOff(knum, cc.velocity);
    }

    /**
     * given 120 bpm: (120 bpm) / (60 seconds per minute) = 2 beats per second 2
     * / 1000 beats per millisecond (2 * resolution) ticks per second (2 *
     * resolution)/1000 ticks per millisecond, or (resolution / 500) ticks per
     * millisecond ticks = milliseconds * resolution / 500
     */
    public void createShortEvent(int type, int num) {
        ShortMessage message = new ShortMessage();
        try {
            long millis = System.currentTimeMillis() - startTime;
            long tick = millis * sequence.getResolution() / 500;
            message.setMessage(type + cc.num, num, cc.velocity);
            MidiEvent event = new MidiEvent(message, tick);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void enableExternalKeyboard(InstrumentMidiReceiver inMidiRx) {
        midiRx = inMidiRx;
        System.out.println(inputPort);
        try {
            inputPort.open();
            List<Transmitter> listTransmitters = inputPort.getTransmitters();
            if (!listTransmitters.isEmpty()) {
                System.out.println("getTransmitters problem- list should be empty but contains:");
                for (Transmitter item : listTransmitters) {
                    System.out.print(" " + item);
                }
                System.out.println();
            }

            midiInTx = inputPort.getTransmitter();
            listTransmitters = inputPort.getTransmitters();
            System.out.println("Open Transmitters:");
            for (Transmitter item : listTransmitters) {
                System.out.print(" " + item);
            }
            System.out.println();

            midiInTx.setReceiver(midiRx);

        } catch (MidiUnavailableException e) {
            // Oops! Should never get here
            System.out.println("Exception in PlumStone Deployment Tester main ()");
            System.out.println(e);
            System.exit(0);
        }
    }

    public void disableExternalKeyboard() {
        midiInTx.close();
        inputPort.close();
        outputPort.close();
    }

    public boolean isMouseOverKB() {
        return mouseOverKB.isSelected();
    }

    public void setSelectedMouseOverKB(boolean changeMouseHover) {
        mouseOverKB.setSelected(changeMouseHover);
    }

    public void setVisibleKeyNamesBtn(boolean show) {
        keyNames.setVisible(show);
    }

    public void setVisibleMidiKeyNamesBtn(boolean show) {
        midiKeyNames.setVisible(show);
    }

    public void setVisibleMouseOverKBBtn(boolean show) {
        mouseOverKB.setVisible(show);
    }

    public void programChange(int program) {
        if (instruments != null) {
            synthesizer.loadInstrument(instruments[program]);
        }
        cc.channel.programChange(program);
    }

    /**
     * Stores MidiChannel information.
     */
    class ChannelData {

        MidiChannel channel;
        int velocity, pressure, bend, reverb;
        int row, col, num;

        public ChannelData(MidiChannel channel, int num) {
            this.channel = channel;
            this.num = num;
            velocity = pressure = bend = reverb = 64;
        }

        public void setComponentStates() {
            JSlider slider[] = {veloS, presS, bendS, reverbS};
            int v[] = {velocity, pressure, bend, reverb};
            for (int i = 0; i < slider.length; i++) {
                TitledBorder tb = (TitledBorder) slider[i].getBorder();
                String s = tb.getTitle();
                tb.setTitle(s.substring(0, s.indexOf('=') + 1) + s.valueOf(v[i]));
                slider[i].repaint();
            }
        }
    } // End class ChannelData

    /**
     * A collection of MIDI controllers.
     */
    class Controls implements ChangeListener, ItemListener {

        public Controls() {

            setSliderProperties(veloS, "Veloc", 0, 127, 64);
            setSliderProperties(presS, "Press", 0, 127, 64);
            setSliderProperties(reverbS, "Reverb", 0, 127, 64);
            setSliderProperties(bendS, "Bend", 0, 16383, 8192);

//            midiChannelComboBox.setPreferredSize(new Dimension(120,25));
//            midiChannelComboBox.setMaximumSize(new Dimension(120,25));
//            for (int i = 1; i <= 16; i++) {
//                midiChannelComboBox.addItem("Channel " + String.valueOf(i));
//            }
//            midiChannelComboBox.addItemListener(this);
        }

        private void setSliderProperties(JSlider s, String name, int min, int max, int value) {
            s.setMinimum(min);
            s.setMaximum(max);
            s.setValue(value);
            s.addChangeListener(this);
            TitledBorder tb = new TitledBorder(new EtchedBorder());
            tb.setTitle(name + " = " + value);
            s.setBorder(tb);
        }

        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            int value = slider.getValue();
            TitledBorder tb = (TitledBorder) slider.getBorder();
            String s = tb.getTitle();
            tb.setTitle(s.substring(0, s.indexOf('=') + 2) + s.valueOf(value));
            if (s.startsWith("Veloc")) {
                cc.velocity = value;
            } else if (s.startsWith("Press")) {
                cc.channel.setChannelPressure(cc.pressure = value);
            } else if (s.startsWith("Bend")) {
                cc.channel.setPitchBend(cc.bend = value);
            } else if (s.startsWith("Reverb")) {
                cc.channel.controlChange(REVERB, cc.reverb = value);
            }
            slider.repaint();
        }

        public void itemStateChanged(ItemEvent e) {
//            if (e.getSource() instanceof JComboBox) {
//                JComboBox combo = (JComboBox) e.getSource();
//                cc = channels[combo.getSelectedIndex()];
//                cc.setComponentStates();
//            }
        }
    } // End class Controls
}
