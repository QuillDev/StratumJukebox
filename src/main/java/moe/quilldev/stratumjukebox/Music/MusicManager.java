package moe.quilldev.stratumjukebox.Music;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

@Singleton
public class MusicManager {

    private static final Logger logger = LoggerFactory.getLogger(MusicManager.class.getSimpleName());

    private final HashMap<String, SongData> songMap = new HashMap<>();
    private final Plugin plugin;
    private final MidiReader midiReader;
    private final Path musicPath;

    @Inject
    public MusicManager(Plugin plugin, MidiReader midiReader) {
        this.plugin = plugin;
        this.midiReader = midiReader;
        this.musicPath = Path.of(plugin.getDataFolder().getPath(), "music");
        this.loadSongs();
    }

    public void loadSongs() {
        try {
            //If the directory does not exist, create it
            if (!Files.exists(musicPath)) {
                Files.createDirectories(musicPath);
            }

            Files.walk(musicPath, 1)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(midiPath -> {
                        final var song = midiReader.readMidi(midiPath);
                        if (song == null) return;
                        final var name = midiPath.getFileName().toString();
                        songMap.put(name, song);
                        logger.info(String.format("Loaded song -> %s", name));
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, SongData> getSongMap() {
        return songMap;
    }

    public SongData getSong(String query) {
        return songMap.getOrDefault(query, null);
    }
}
