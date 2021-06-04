package moe.quilldev.stratumjukebox.Music;

import moe.quilldev.stratumjukebox.Music.Notes.NoteData;

import java.util.ArrayList;

public class SongData {

    public ArrayList<ArrayList<NoteData>> tracks;
    public long runtime;
    public float bpm;

    public SongData(ArrayList<ArrayList<NoteData>> tracks, long runtime, float bpm) {
        this.tracks = tracks;
        this.runtime = runtime;
        this.bpm = bpm;
    }

    public ArrayList<ArrayList<NoteData>> getTracks() {
        return tracks;
    }

    public long getRuntime() {
        return runtime;
    }

    public float getBpm() {
        return bpm;
    }
}
