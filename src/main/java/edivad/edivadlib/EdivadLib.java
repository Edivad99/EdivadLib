package edivad.edivadlib;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import edivad.edivadlib.setup.UpdateChecker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EdivadLib.ID)
public class EdivadLib {

  public static final String ID = "edivadlib";

  public static final Logger LOGGER = LogUtils.getLogger();

  public EdivadLib() {
    var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    modEventBus.addListener(this::handleClientSetup);
  }

  private void handleClientSetup(FMLClientSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new UpdateChecker(ID));
  }
}