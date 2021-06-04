package moe.quilldev.stratumjukebox.Music.Notes;

import org.bukkit.Sound;

public class NoteData {

    private final Sound sound;
    private final float pitch;
    private final float velocity;
    private final long tick;

    public NoteData(Sound sound, float pitch, float velocity, long tick) {
        this.sound = sound;
        this.pitch = pitch;
        this.velocity = velocity;
        this.tick = tick;
    }

    public NoteData(Sound sound, Note note, float velocity, long tick) {
        this(sound, note.pitch, velocity, tick);
    }

    public float getPitch() {
        return pitch;
    }

    public Sound getSound() {
        return sound;
    }

    public long getTick() {
        return tick;
    }

    public float getVelocity() {
        return velocity;
    }
}
