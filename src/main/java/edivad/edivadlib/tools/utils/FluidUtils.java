package edivad.edivadlib.tools.utils;

import java.util.Objects;
import org.apache.commons.lang3.NotImplementedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.data.AtlasIds;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidUtils {

  public static float getRed(int color) {
    return ARGB.red(color) / 255.0F;
  }

  public static float getGreen(int color) {
    return ARGB.green(color) / 255.0F;
  }

  public static float getBlue(int color) {
    return ARGB.blue(color) / 255.0F;
  }

  public static float getAlpha(int color) {
    return ARGB.alpha(color) / 255.0F;
  }

  public static void color(GuiGraphics guiGraphics, int color) {
    throw new NotImplementedException();
    //guiGraphics.setColor(getRed(color), getGreen(color), getBlue(color), getAlpha(color));
  }

  public static TextureAtlasSprite getFluidTexture(FluidStack fluidStack) {
    var extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
    var stillTexture = extensions.getStillTexture(fluidStack);
    return Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.BLOCKS).getSprite(stillTexture);
  }

  public static int getLiquidColorWithBiome(FluidStack fluidStack, Level level,
      BlockPos pos) {
    if (level.isClientSide()) {
      if (fluidStack.getFluid().isSame(Fluids.WATER)) {
        return BiomeColors.getAverageWaterColor(level, pos) | 0xFF000000;
      }
    }

    return IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack);
  }

  public static int getLiquidColorWithBiome(FluidStack fluid,
      BlockEntity blockEntity) {
    return getLiquidColorWithBiome(fluid, Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos());
  }

  public static int getFluidScaled(int pixels, FluidStack fluid, int maxLiquidAmount) {
    if (maxLiquidAmount == 0) {
      return pixels;
    }
    long currentLiquidAmount = fluid.getAmount();
    long x = currentLiquidAmount * pixels / maxLiquidAmount;
    return pixels - (int) x;
  }
}
