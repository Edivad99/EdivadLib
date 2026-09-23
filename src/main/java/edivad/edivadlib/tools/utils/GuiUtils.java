package edivad.edivadlib.tools.utils;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class GuiUtils {

  public static void drawTiledSprite(GuiGraphicsExtractor graphics, int x, int y, int width,
      int height, TextureAtlasSprite sprite, int textureWidth, int textureHeight, int color) {
    if (width <= 0 || height <= 0 || textureWidth <= 0 || textureHeight <= 0) {
      return;
    }
    graphics.enableScissor(x, y, x + width, y + height);
    for (int tileX = 0; tileX < width; tileX += textureWidth) {
      for (int tileY = 0; tileY < height; tileY += textureHeight) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, x + tileX, y + tileY,
            textureWidth, textureHeight, color);
      }
    }
    graphics.disableScissor();
  }
}
