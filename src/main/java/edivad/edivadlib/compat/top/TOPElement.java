package edivad.edivadlib.compat.top;

import org.jspecify.annotations.Nullable;
import edivad.edivadlib.tools.utils.GuiUtils;
import mcjty.theoneprobe.api.IElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.MutableComponent;

public abstract class TOPElement implements IElement {

  private final int borderColor;
  private final int textColor;

  protected TOPElement(int borderColor, int textColor) {
    this.borderColor = borderColor;
    this.textColor = textColor;
  }

  protected static void renderScaledText(GuiGraphicsExtractor graphics, Font font, int x, int y,
      int color, int maxWidth, MutableComponent component) {
    String text = component.getString();
    int length = font.width(text);
    if (length <= maxWidth) {
      graphics.text(font, text, x, y, color);
    } else {
      float scale = (float) maxWidth / length;
      float reverse = 1 / scale;
      float yAdd = 4 - (scale * 8) / 2F;
      var poseStack = graphics.pose();
      poseStack.pushMatrix();
      poseStack.scale(scale, scale);
      graphics.text(font, text, (int) (x * reverse), (int) ((y * reverse) + yAdd), color);
      poseStack.popMatrix();
    }
  }

  @Override
  public void render(GuiGraphicsExtractor graphics, int x, int y) {
    int width = getWidth();
    int height = getHeight();
    graphics.fill(x, y, x + width - 1, y + 1, borderColor);
    graphics.fill(x, y, x + 1, y + height - 1, borderColor);
    graphics.fill(x + width - 1, y, x + width, y + height - 1, borderColor);
    graphics.fill(x, y + height - 1, x + width, y + height, borderColor);
    TextureAtlasSprite icon = getIcon();
    if (icon != null) {
      int scale = getScaledLevel(width - 2);
      if (scale > 0) {
        GuiUtils.drawTiledSprite(graphics, x + 1, y + 1, scale, height - 2, icon, 16, 16,
            getRenderColor());
      }
    }
    renderScaledText(graphics, Minecraft.getInstance().font, x + 4, y + 3, textColor,
        getWidth() - 8, getText());
  }

  @Override
  public int getWidth() {
    return 100;
  }

  @Override
  public int getHeight() {
    return 13;
  }

  public abstract int getScaledLevel(int level);

  @Nullable
  public abstract TextureAtlasSprite getIcon();

  public abstract MutableComponent getText();

  protected int getRenderColor() {
    return -1;
  }
}
