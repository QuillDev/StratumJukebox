package moe.quilldev.stratumjukebox.Commands.Play;

import com.google.inject.Inject;
import moe.quilldev.stratumjukebox.Music.MusicManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlayCommand implements CommandExecutor {

    private final Plugin plugin;
    private final MusicManager musicManager;

    @Inject
    public PlayCommand(Plugin plugin, MusicManager musicManager) {
        this.plugin = plugin;
        this.musicManager = musicManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final var player = (Player) sender;

        final var songData = musicManager.getSong(String.join(" ", args));
        if (songData == null) {
            player.sendMessage("No data found for that song...");
            return true;
        }
        for (final var track : songData.getTracks()) {
            for (final var note : track) {
                //schedule all of the notes to play
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    //Play the tick when the time is right
                    player.playSound(player.getLocation(), note.getSound(), note.getVelocity() / 128, note.getPitch());
                }, note.getTick());
            }
        }


        return true;
    }
}
