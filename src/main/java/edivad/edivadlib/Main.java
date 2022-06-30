package edivad.edivadlib;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "edivadlib";
    public static final String MODNAME = "EdivadLib";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Main() {
    }
}