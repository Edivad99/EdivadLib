package edivad.edivadlib.tools.utils;

import org.joml.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class GuiUtils {

  public static void drawTiledSprite(GuiGraphics guiGraphics, int xPosition, int yPosition,
      int yOffset, int desiredWidth, int desiredHeight, TextureAtlasSprite sprite,
      int textureWidth, int textureHeight, int zLevel) {
    if (desiredWidth == 0 || desiredHeight == 0 || textureWidth == 0 || textureHeight == 0) {
      return;
    }
    var renderType = RenderType.guiTextured(sprite.atlasLocation());
    var vertexBuffer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);

    int xTileCount = desiredWidth / textureWidth;
    int xRemainder = desiredWidth - (xTileCount * textureWidth);
    int yTileCount = desiredHeight / textureHeight;
    int yRemainder = desiredHeight - (yTileCount * textureHeight);
    int yStart = yPosition + yOffset;
    float uMin = sprite.getU0();
    float uMax = sprite.getU1();
    float vMin = sprite.getV0();
    float vMax = sprite.getV1();
    float uDif = uMax - uMin;
    float vDif = vMax - vMin;
    Matrix4f matrix4f = guiGraphics.pose().last().pose();
    for (int xTile = 0; xTile <= xTileCount; xTile++) {
      int width = (xTile == xTileCount) ? xRemainder : textureWidth;
      if (width == 0) {
        break;
      }
      int x = xPosition + (xTile * textureWidth);
      int maskRight = textureWidth - width;
      int shiftedX = x + textureWidth - maskRight;
      float uMaxLocal = uMax - (uDif * maskRight / textureWidth);
      for (int yTile = 0; yTile <= yTileCount; yTile++) {
        int height = (yTile == yTileCount) ? yRemainder : textureHeight;
        if (height == 0) {
          //Note: We don't want to fully break out because our height will be zero if we are looking to
          // draw the remainder, but there is no remainder as it divided evenly
          break;
        }
        int y = yStart - ((yTile + 1) * textureHeight);
        int maskTop = textureHeight - height;
        float vMaxLocal = vMax - (vDif * maskTop / textureHeight);
        vertexBuffer.addVertex(matrix4f, x, y + textureHeight, zLevel)
            .setColor(255, 255, 255, 255)
            .setUv(uMin, vMaxLocal);
        vertexBuffer.addVertex(matrix4f, shiftedX, y + textureHeight, zLevel)
            .setColor(255, 255, 255, 255)
            .setUv(uMaxLocal, vMaxLocal);
        vertexBuffer.addVertex(matrix4f, shiftedX, y + maskTop, zLevel)
            .setColor(255, 255, 255, 255)
            .setUv(uMaxLocal, vMin);
        vertexBuffer.addVertex(matrix4f, x, y + maskTop, zLevel)
            .setColor(255, 255, 255, 255)
            .setUv(uMin, vMin);
      }
    }
  }
}
