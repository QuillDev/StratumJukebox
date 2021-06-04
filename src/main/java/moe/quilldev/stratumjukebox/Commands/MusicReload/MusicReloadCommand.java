package moe.quilldev.stratumjukebox.Commands.MusicReload;

import com.google.inject.Inject;
import moe.quilldev.stratumjukebox.Music.MusicManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MusicReloadCommand implements CommandExecutor {

    private final MusicManager musicManager;

    @Inject
    public MusicReloadCommand(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        musicManager.loadSongs();
        sender.sendMessage("Reloaded songs!");
        return true;
    }
}
