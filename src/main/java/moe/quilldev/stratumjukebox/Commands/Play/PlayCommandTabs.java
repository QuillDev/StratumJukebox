package moe.quilldev.stratumjukebox.Commands.Play;

import com.google.inject.Inject;
import moe.quilldev.stratumjukebox.Music.MusicManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class PlayCommandTabs implements TabCompleter {

    private final MusicManager musicManager;

    @Inject
    public PlayCommandTabs(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return musicManager.getSongMap().keySet().stream().filter(key -> key.contains(args[0])).collect(Collectors.toList());
        }
        return null;
    }
}
