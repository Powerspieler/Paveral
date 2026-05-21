package me.powerspieler.paveral;

import io.papermc.paper.datapack.DatapackRegistrar;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.powerspieler.paveral.commands.ItemsCommand;
import me.powerspieler.paveral.commands.TestCommand;
import me.powerspieler.paveral.discovery.guide.GuideCommand;
import org.jspecify.annotations.NullMarked;

import java.net.URI;
import java.util.Objects;

@NullMarked
public class PaveralBootstrap implements PluginBootstrap {

    @Override
    public void bootstrap(BootstrapContext context) {
        final LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();

        // Register Paveral Commands
        manager.registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register("test", new TestCommand());
            commands.registrar().register("items", new ItemsCommand());
            commands.registrar().register("guide", new GuideCommand());
        });

        // Install Paveral Datapack
        manager.registerEventHandler(LifecycleEvents.DATAPACK_DISCOVERY, event -> {
            DatapackRegistrar registrar = event.registrar();
            try {
                final URI uri = Objects.requireNonNull(
                        PaveralBootstrap.class.getResource("/paveral_datapack")
                ).toURI();
                registrar.discoverPack(uri, "provided");
            } catch (Exception e) {
                System.err.println("Unable to register Paveral datapack");
                throw new RuntimeException(e);
            }
        });
    }
}
