/*
 * MIDIInstruments.java
 *
 * Created on 27 juli 2006, 1:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jfugue.instruments;

import java.util.Vector;
import org.musician.api.Instrument;
import org.musician.api.InstrumentCategory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

/**
 *
 * @author Pierre Matthijs
 */
public class MIDIInstruments  extends Children.Keys {
    
    private InstrumentCategory instrumentCategory;
    
    private String[][] instruments = new String[][]{
        {"1", "Piano", "Acoustic Grand Piano", "ACOUSTIC_GRAND"},
        {"2", "Piano", "Bright Acoustic Piano", "BRIGHT_ACOUSTIC"},
        {"3", "Piano", "Electric Grand Piano", "ELECTRIC_GRAND"},
        {"4", "Piano", "Honky-tonk Piano", "HONKEY_TONK"},
        {"5", "Piano", "Electric Piano 1", "ELECTRIC_PIANO_1"},
        {"6", "Piano", "Electric Piano 2", "ELECTRIC_PIANO_2"},
        {"7", "Piano", "Harpsichord", "HARPISCHORD"},
        {"8", "Piano", "Clavi", "CLAVINET"},
        {"9", "Chromatic", "Celesta", "CELESTA"},
        {"10", "Chromatic", "Glockenspiel", "GLOCKENSPIEL"},
        {"11", "Chromatic", "Music Box", "MUSIC_BOX"},
        {"12", "Chromatic", "Vibraphone", "VIBRAPHONE"},
        {"13", "Chromatic", "Marimba", "MARIMBA"},
        {"14", "Chromatic", "Xylophone", "XYLOPHONE"},
        {"15", "Chromatic", "Tubular Bells", "TUBULAR_BELLS"},
        {"16", "Chromatic", "Dulcimer", "DULCIMER"},
        {"17", "Organ", "Drawbar Organ", "DRAWBAR_ORGAN"},
        {"18", "Organ", "Percussive Organ", "PERCUSSIVE_ORGAN"},
        {"19", "Organ", "Rock Organ", "ROCK_ORGAN"},
        {"20", "Organ", "Church Organ", "CHURCH_ORGAN"},
        {"21", "Organ", "Reed Organ", "REED_ORGAN"},
        {"22", "Organ", "Accordion", "ACCORIDAN"},
        {"23", "Organ", "Harmonica", "HARMONICA"},
        {"24", "Organ", "Tango Accordion", "TANGO_ACCORDIAN"},
        {"25", "Guitar", "Acoustic Guitar (nylon)", "GUITAR"},
        {"26", "Guitar", "Acoustic Guitar (steel)", "STEEL_STRING_GUITAR"},
        {"27", "Guitar", "Electric Guitar (jazz)", "ELECTRIC_JAZZ_GUITAR"},
        {"28", "Guitar", "Electric Guitar (clean)", "ELECTRIC_CLEAN_GUITAR"},
        {"29", "Guitar", "Electric Guitar (muted)", "ELECTRIC_MUTED_GUITAR"},
        {"30", "Guitar", "Overdriven Guitar", "OVERDRIVEN_GUITAR"},
        {"31", "Guitar", "Distortion Guitar", "DISTORTION_GUITAR"},
        {"32", "Guitar", "Guitar harmonics", "GUITAR_HARMONICS"},
        {"33", "Bass", "Acoustic Bass", "ACOUSTIC_BASS"},
        {"34", "Bass", "Electric Bass (finger)", "ELECTRIC_BASS_FINGER"},
        {"35", "Bass", "Electric Bass (pick)", "ELECTRIC_BASS_PICK"},
        {"36", "Bass", "Fretless Bass", "FRETLESS_BASS"},
        {"37", "Bass", "Slap Bass 1", "SLAP_BASS_1"},
        {"38", "Bass", "Slap Bass 2", "SLAP_BASS_2"},
        {"39", "Bass", "Synth Bass 1", "SYNTH_BASS_1"},
        {"40", "Bass", "Synth Bass 2", "SYNTH_BASS_2"},
        {"41", "Strings", "Violin", "VIOLIN"},
        {"42", "Strings", "Viola", "VIOLA"},
        {"43", "Strings", "Cello", "CELLO"},
        {"44", "Strings", "Contrabass", "CONTRABASS"},
        {"45", "Strings", "Tremolo Strings", "TREMOLO_STRINGS"},
        {"46", "Strings", "Pizzicato Strings", "PIZZICATO_STRINGS"},
        {"47", "Strings", "Orchestral Harp", "ORCHESTRAL_STRINGS"},
        {"48", "Strings", "Timpani", "TIMPANI"},                
        {"49", "Ensemble", "String Ensemble 1", "STRING_ENSEMBLE_1"},
        {"50", "Ensemble", "String Ensemble 2", "STRING_ENSEMBLE_2"},
        {"51", "Ensemble", "SynthStrings 1", "SYNTHSTRINGS_1"},
        {"52", "Ensemble", "SynthStrings 2", "SYNTHSTRINGS_2"},
        {"53", "Ensemble", "Choir Aahs", "CHOIR_AAHS"},
        {"54", "Ensemble", "Voice Oohs", "VOICE_OOHS"},
        {"55", "Ensemble", "Synth Voice", "SYNTH_VOICE"},
        {"56", "Ensemble", "Orchestra Hit", "ORCHESTRA_HIT"},                
        {"57", "Brass", "Trumpet", "TRUMPET"},
        {"58", "Brass", "Trombone", "TROMBONE"},
        {"59", "Brass", "Tuba", "TUBA"},
        {"60", "Brass", "Muted Trumpet", "MUTED_TRUMPET"},
        {"61", "Brass", "French Horn", "FRENCH_HORN"},
        {"62", "Brass", "Brass Section", "BRASS_SECTION"},
        {"63", "Brass", "SynthBrass 1", "SYNTHBRASS_1"},
        {"64", "Brass", "SynthBrass 2", "SYNTHBRASS_2"},
        {"65", "Reed", "Soprano Sax", "SOPRANO_SAX"},
        {"66", "Reed", "Alto Sax", "ALTO_SAX"},
        {"67", "Reed", "Tenor Sax", "TENOR_SAX"},
        {"68", "Reed", "Baritone Sax", "BARITONE_SAX"},
        {"69", "Reed", "Oboe", "OBOE"},
        {"70", "Reed", "English Horn", "ENGLISH_HORN"},
        {"71", "Reed", "Bassoon", "BASSOON"},
        {"72", "Reed", "Clarinet", "CLARINET"},
        {"73", "Pipe", "Piccolo", "PICCOLO"},
        {"74", "Pipe", "Flute", "FLUTE"},
        {"75", "Pipe", "Recorder", "RECORDER"},
        {"76", "Pipe", "Pan Flute", "PAN_FLUTE"},
        {"77", "Pipe", "Blown Bottle", "BLOWN_BOTTLE"},
        {"78", "Pipe", "Shakuhachi", "SKAKUHACHI"},
        {"79", "Pipe", "Whistle", "WHISTLE"},
        {"80", "Pipe", "Ocarina", "OCARINA"},
        {"81", "Synth Lead", "Lead 1 (square)", "LEAD_SQUARE"},
        {"82", "Synth Lead", "Lead 2 (sawtooth)", "LEAD_SAWTOOTH"},
        {"83", "Synth Lead", "Lead 3 (calliope)", "LEAD_CALLIOPE"},
        {"84", "Synth Lead", "Lead 4 (chiff)", "LEAD_CHIFF"},
        {"85", "Synth Lead", "Lead 5 (charang)", "LEAD_CHARANG"},
        {"86", "Synth Lead", "Lead 6 (voice)", "LEAD_VOICE"},
        {"87", "Synth Lead", "Lead 7 (fifths)", "LEAD_FIFTHS"},
        {"88", "Synth Lead", "Lead 8 (bass + lead)", "LEAD_BASSLEAD"},
        {"89", "Synth Pad", "Pad 1 (new age)", "PAD_NEW_AGE"},
        {"90", "Synth Pad", "Pad 2 (warm)", "PAD_WARM"},
        {"91", "Synth Pad", "Pad 3 (polysynth)", "PAD_POLYSYNTH"},
        {"92", "Synth Pad", "Pad 4 (choir)", "PAD_CHOIR"},
        {"93", "Synth Pad", "Pad 5 (bowed)", "PAD_BOWED"},
        {"94", "Synth Pad", "Pad 6 (metallic)", "PAD_METALLIC"},
        {"95", "Synth Pad", "Pad 7 (halo)", "PAD_HALO"},
        {"96", "Synth Pad", "Pad 8 (sweep)", "PAD_SWEEP"},
        {"97", "Synth Effects", "FX 1 (train)", "FX_RAIN"},
        {"98", "Synth Effects", "FX 2 (soundtrack)", "FX_SOUNDTRACK"},
        {"99", "Synth Effects", "FX 3 (crystal)", "FX_CRYSTAL"},
        {"100", "Synth Effects", "FX 4 (atmosphere)", "FX_ATMOSPHERE"},
        {"101", "Synth Effects", "FX 5 (brightness)", "FX_BRIGHTNESS"},
        {"102", "Synth Effects", "FX 6 (goblins)", "FX_GOBLINS"},
        {"103", "Synth Effects", "FX 7 (echoes)", "FX_ECHOES"},
        {"104", "Synth Effects", "FX 8 (sci-fi)", "FX_SCI_FI"},
        {"105", "Ethnic", "Sitar", "SITAR"},
        {"106", "Ethnic", "Banjo", "BANJO"},
        {"107", "Ethnic", "Shamisen", "SHAMISEN"},
        {"108", "Ethnic", "Koto", "KOTO"},
        {"109", "Ethnic", "Kalimba", "KALIMBA"},
        {"110", "Ethnic", "Bag pipe", "BAGPIPE"},
        {"111", "Ethnic", "Fiddle", "FIDDLE"},
        {"112", "Ethnic", "Shanai", "SHANAI"},
        {"113", "Percussive", "Tinkle Bell", "TINKLE_BELL"},
        {"114", "Percussive", "Agogo", "AGOGO"},
        {"115", "Percussive", "Steel Drums", "STEEL_DRUMS"},
        {"116", "Percussive", "Woodblock", "WOODBLOCK"},
        {"117", "Percussive", "Taiko Drum", "TAIKO_DRUM"},
        {"118", "Percussive", "Melodic Tom", "MELODIC_TOM"},
        {"119", "Percussive", "Synth Drum", "SYNTH_DRUM"},
        {"120", "Percussive", "Reverse Cymbal", "REVERSE_CYMBAL"},
        {"121", "Sound Effects", "Guitar Fret Noise", "GUITAR_FRET_NOISE"},
        {"122", "Sound Effects", "Breath Noise", "BREATH_NOISE"},
        {"123", "Sound Effects", "Seashore", "SEASHORE"},
        {"124", "Sound Effects", "Bird Tweet", "BIRD_TWEET"},
        {"125", "Sound Effects", "Telephone Ring", "TELEPHONE_RING"},
        {"126", "Sound Effects", "Helicopter", "HELICOPTER"},
        {"127", "Sound Effects", "Applause", "APPLAUSE"},
        {"128", "Sound Effects", "Gunshot", "GUNSHOT"}
    };
    
    /**
     * Creates a new instance of MIDIInstruments
     */
    public MIDIInstruments(InstrumentCategory instrumentCategory) {
        this.instrumentCategory = instrumentCategory;
    }
    
    protected Node[] createNodes(Object key) {
        Instrument obj = (Instrument)key;
        InstrumentNode result = new InstrumentNode(obj);
        result.setDisplayName(obj.getDescription());
        return new Node[] { result };
    }
    
    protected void addNotify() {
        super.addNotify();
        Vector <Instrument> instr = new Vector<Instrument>();
        for (int i = 0; i < instruments.length; i++) {
            if ( instruments[i][1].equals(instrumentCategory.getInstrumentCategory()) ) {
                Instrument instrument = new Instrument();
                instrument.setMidiNumber(new Integer(instruments[i][0]));
                instrument.setCategory(instruments[i][1]);
                instrument.setDescription(instruments[i][2]);
                instrument.setJfugueDescription(instruments[i][3]);
                instr.addElement(instrument);
            }
        }
        
        Instrument[] objs = new Instrument[instr.size()];
        for (int i = 0; i<instr.size(); i++) {
            objs[i] = (Instrument)instr.elementAt(i);
        }
        setKeys(objs);
    }
}

