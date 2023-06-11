package edivad.edivadlib.compat.top;

import com.mojang.blaze3d.systems.RenderSystem;
import edivad.edivadlib.tools.utils.GuiUtils;
import mcjty.theoneprobe.api.IElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Nullable;

public abstract class TOPElement implements IElement {

    private final int borderColor;
    private final int textColor;

    protected TOPElement(int borderColor, int textColor) {
        this.borderColor = borderColor;
        this.textColor = textColor;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y) {
        int width = getWidth();
        int height = getHeight();
        guiGraphics.fill(x, y, x + width - 1, y + 1, borderColor);
        guiGraphics.fill(x, y, x + 1, y + height - 1, borderColor);
        guiGraphics.fill(x + width - 1, y, x + width, y + height - 1, borderColor);
        guiGraphics.fill(x, y + height - 1, x + width, y + height, borderColor);
        TextureAtlasSprite icon = getIcon();
        if(icon != null) {
            int scale = getScaledLevel(width - 2);
            if(scale > 0) {
                boolean colored = applyRenderColor();
                GuiUtils.drawTiledSprite(x + 1, y + 1, height - 2, scale, height - 2, icon, 16, 16, 0);
                if(colored) {
                    RenderSystem.setShaderColor(1, 1, 1, 1);
                }
            }
        }
        renderScaledText(guiGraphics, Minecraft.getInstance().font, x + 4, y + 3, textColor, getWidth() - 8, getText());
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

    protected boolean applyRenderColor() {
        return false;
    }

    protected static void renderScaledText(GuiGraphics guiGraphics, Font font, int x, int y, int color,
        int maxWidth, MutableComponent component) {
        String text = component.getString();
        int length = font.width(text);
        if(length <= maxWidth) {
            guiGraphics.drawString(font, text, x, y, color);
        }
        else {
            float scale = (float) maxWidth / length;
            float reverse = 1 / scale;
            float yAdd = 4 - (scale * 8) / 2F;
            var poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.scale(scale, scale, scale);
            guiGraphics.drawString(font, text, (int) (x * reverse), (int) ((y * reverse) + yAdd), color);
            poseStack.popPose();
        }
        //Make sure the color does not leak from having drawn the string
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }
}
