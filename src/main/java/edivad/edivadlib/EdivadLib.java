package edivad.edivadlib;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import edivad.edivadlib.setup.UpdateChecker;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(EdivadLib.ID)
public class EdivadLib {

  public static final String ID = "edivadlib";

  public static final Logger LOGGER = LogUtils.getLogger();

  public EdivadLib(IEventBus modEventBus) {
    modEventBus.addListener(this::handleClientSetup);
  }

  private void handleClientSetup(FMLClientSetupEvent event) {
    NeoForge.EVENT_BUS.register(new UpdateChecker(ID));
  }
}