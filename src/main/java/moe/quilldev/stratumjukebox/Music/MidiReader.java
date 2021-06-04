package moe.quilldev.stratumjukebox.Music;

import com.google.inject.Inject;
import moe.quilldev.stratumjukebox.Music.Notes.Note;
import moe.quilldev.stratumjukebox.Music.Notes.NoteData;
import org.bukkit.Sound;
import org.bukkit.plugin.Plugin;

import javax.sound.midi.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import static javax.sound.midi.ShortMessage.NOTE_ON;

public class MidiReader {

    Plugin plugin;

    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    final HashMap<String, Note> noteMapOct1 = new HashMap<>() {{
        put(NOTE_NAMES[0], Note.C_1);
        put(NOTE_NAMES[1], Note.C_S_1);
        put(NOTE_NAMES[2], Note.D_1);
        put(NOTE_NAMES[3], Note.D_S_1);
        put(NOTE_NAMES[4], Note.E_1);
        put(NOTE_NAMES[5], Note.F_1);
        put(NOTE_NAMES[6], Note.F_S_1);
        put(NOTE_NAMES[7], Note.G_1);
        put(NOTE_NAMES[8], Note.G_S_1);
        put(NOTE_NAMES[9], Note.A_1);
        put(NOTE_NAMES[10], Note.A_S_1);
        put(NOTE_NAMES[11], Note.B_1);
    }};
    final HashMap<String, Note> noteMapOct2 = new HashMap<>() {{
        put(NOTE_NAMES[0], Note.C_2);
        put(NOTE_NAMES[1], Note.C_S_2);
        put(NOTE_NAMES[2], Note.D_2);
        put(NOTE_NAMES[3], Note.D_S_2);
        put(NOTE_NAMES[4], Note.E_2);
        put(NOTE_NAMES[5], Note.F_2);
        put(NOTE_NAMES[6], Note.F_S_2);
        put(NOTE_NAMES[7], Note.G_2);
        put(NOTE_NAMES[8], Note.G_S_2);
        put(NOTE_NAMES[9], Note.A_2);
        put(NOTE_NAMES[10], Note.A_S_2);
        put(NOTE_NAMES[11], Note.B_2);
    }};

    @Inject
    public MidiReader(Plugin plugin) {
        this.plugin = plugin;
    }

    public SongData readMidi(Path path) {
        final var queryFile = path.toFile();
        final var mcNotes = new ArrayList<ArrayList<NoteData>>();
        long runtime = 0L;
        float bpm = 0;
        if (!queryFile.exists()) return null;

        try {
            final var sequence = MidiSystem.getSequence(queryFile);
            final var divisionType = sequence.getDivisionType();
            final var ticks = sequence.getTickLength();
            final float resolution = sequence.getResolution();

            final float beats = (divisionType == Sequence.SMPTE_24) ? 24f * resolution
                    : (divisionType == Sequence.SMPTE_25) ? 25f * resolution
                    : (divisionType == Sequence.SMPTE_30) ? 30f * resolution
                    : (divisionType == Sequence.SMPTE_30DROP) ? 29.97f * resolution
                    : ticks / resolution;

            final float seconds = (float) (sequence.getMicrosecondLength() / 1e6);
            final float minutes = seconds / 60;

            final var mcTicksPerTick = (seconds * 20 / ticks);
            bpm = (beats / minutes);

            runtime = sequence.getTickLength();
            //Loop through each track in the sequence
            for (Track track : sequence.getTracks()) {
                //Create a  list for storing notes on this track
                final var noteTrack = new ArrayList<NoteData>();

                //For each note in the track
                for (int i = 0; i < track.size(); i++) {
                    MidiEvent event = track.get(i);
                    MidiMessage message = event.getMessage();
                    if (message instanceof ShortMessage) {
                        ShortMessage sm = (ShortMessage) message;
                        if (sm.getCommand() == NOTE_ON) {
                            int key = sm.getData1();
                            int octave = (key / 12) - 1;
                            int velocity = sm.getData2();
                            int note = key % 12;

                            Note mcNote;
                            final var noteName = NOTE_NAMES[note];
                            if (octave == 1) {
                                mcNote = noteMapOct1.get(noteName);
                            } else {
                                mcNote = noteMapOct2.get(noteName);
                            }
                            if (mcNote == null) {
                                System.out.println(note + " " + octave);
                                continue;
                            }
                            noteTrack.add(new NoteData(Sound.BLOCK_NOTE_BLOCK_BIT, mcNote, velocity, (long) (mcTicksPerTick * event.getTick())));
                        }
                    }
                }
                mcNotes.add(noteTrack);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SongData(mcNotes, runtime, bpm);
    }
}
