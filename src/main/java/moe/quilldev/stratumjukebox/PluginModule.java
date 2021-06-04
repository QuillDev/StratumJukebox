package moe.quilldev.stratumjukebox;

import com.google.inject.AbstractModule;
import moe.quilldev.stratumjukebox.Music.MidiReader;
import moe.quilldev.stratumjukebox.Music.MusicManager;
import org.bukkit.plugin.Plugin;

public class PluginModule extends AbstractModule {

    private final Plugin plugin;
    private final MidiReader midiReader;
    private final MusicManager musicManager;

    public PluginModule(Plugin plugin) {
        this.plugin = plugin;
        this.midiReader = new MidiReader(plugin);
        this.musicManager = new MusicManager(plugin, midiReader);
    }

    @Override
    public void configure() {
        bind(Plugin.class).toInstance(plugin);
        bind(MidiReader.class).toInstance(midiReader);
        bind(MusicManager.class).toInstance(musicManager);
    }
}
