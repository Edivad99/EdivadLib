package edivad.edivadlib.tools;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TranslationsAdvancement {

  private final String title, desc;

  public TranslationsAdvancement(String modId, String name) {
    title = String.format("advancements.%s.%s", modId, name);
    desc = title + ".desc";
  }

  public String title() {
    return title;
  }

  public String desc() {
    return desc;
  }

  public MutableComponent translateTitle() {
    return Component.translatable(title);
  }

  public MutableComponent translateDescription() {
    return Component.translatable(desc);
  }
}
