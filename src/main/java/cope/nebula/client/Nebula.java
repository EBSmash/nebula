package cope.nebula.client;

import cope.nebula.client.manager.*;
import cope.nebula.client.manager.hole.HoleManager;
import cope.nebula.client.manager.macro.MacroManager;
import cope.nebula.util.internal.timing.Stopwatch;
import cope.nebula.util.internal.timing.TimeFormat;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import static net.minecraftforge.fml.common.Mod.EventHandler;
import static net.minecraftforge.fml.common.Mod.Instance;

@Mod(modid = Nebula.MODID, name = Nebula.NAME, version = Nebula.VERSION)
public class Nebula {
    public static final String NAME = "Nebula";
    public static final String VERSION = "1.0";
    public static final String MODID = "nebula";

    @Instance
    private static Nebula INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger(NAME);

    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private MacroManager macroManager;
    private HoleManager holeManager;

    private RotationManager rotationManager;
    private InteractionManager interactionManager;
    private HotbarManager hotbarManager;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // Roxanne, Roxanne... All she wanna do is party all night
        LOGGER.info("Loading {} v{}...", NAME, VERSION);

        Stopwatch stopwatch = new Stopwatch();
        stopwatch.resetTime();

        new ForgeEventManager();

        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        macroManager = new MacroManager();
        holeManager = new HoleManager();

        rotationManager = new RotationManager();
        interactionManager = new InteractionManager();
        hotbarManager = new HotbarManager();

        // Goddamn, Roxanne... Never gonna love me but it's alright
        LOGGER.info("Completed setup of {} in {}ms", NAME, stopwatch.getTime(TimeFormat.MILLISECONDS));

        Display.setTitle(NAME + " v" + VERSION);
    }

    public static Nebula getInstance() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public MacroManager getMacroManager() {
        return macroManager;
    }

    public HoleManager getHoleManager() {
        return holeManager;
    }

    public RotationManager getRotationManager() {
        return rotationManager;
    }

    public InteractionManager getInteractionManager() {
        return interactionManager;
    }

    public HotbarManager getHotbarManager() {
        return hotbarManager;
    }
}
