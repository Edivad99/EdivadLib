package edivad.edivadlib;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import edivad.edivadlib.setup.UpdateChecker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Main.MODID)
public class Main {

  public static final String MODID = "edivadlib";

  public static final Logger LOGGER = LogUtils.getLogger();

  public Main() {
    var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    if (FMLEnvironment.dist == Dist.CLIENT) {
      modEventBus.addListener(this::handleClientSetup);
    }
  }

  private void handleClientSetup(FMLClientSetupEvent event) {
    MinecraftForge.EVENT_BUS.register(new UpdateChecker(Main.MODID));
  }
}