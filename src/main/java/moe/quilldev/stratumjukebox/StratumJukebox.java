package moe.quilldev.stratumjukebox;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import moe.quill.StratumCommon.Commands.StratumCommand;
import moe.quill.StratumCommon.Plugin.StratumConfigBuilder;
import moe.quill.StratumCommon.Plugin.StratumPlugin;
import moe.quilldev.stratumjukebox.Commands.MusicReload.MusicReloadCommand;
import moe.quilldev.stratumjukebox.Commands.Play.PlayCommand;
import moe.quilldev.stratumjukebox.Commands.Play.PlayCommandTabs;

@Singleton
public final class StratumJukebox extends StratumPlugin {
    @Inject
    PlayCommand playCommand;
    @Inject
    MusicReloadCommand musicReloadCommand;

    //Tab Completer
    @Inject
    PlayCommandTabs playCommandTabs;

    public StratumJukebox() {
        super(new StratumConfigBuilder().build());

    }

    @Override
    public void onEnable() {
        final var injector = Guice.createInjector(new PluginModule(this));
        injector.injectMembers(this);

        // Plugin startup logic
        registerCommand(
                new StratumCommand("play", playCommand, playCommandTabs),
                new StratumCommand("musicreload", musicReloadCommand, null)
        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
