package edivad.edivadlib.tools.utils;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
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

  public static TextureAtlasSprite getFluidTexture(FluidStack fluidStack) {
    var fluidModel = Minecraft.getInstance()
        .getModelManager()
        .getFluidStateModelSet()
        .get(fluidStack.getFluid().defaultFluidState());
    return fluidModel.stillMaterial().sprite();
  }

  public static int getLiquidColorWithBiome(FluidStack fluidStack, Level level,
      BlockPos pos) {
    if (level instanceof ClientLevel clientLevel) {
      if (fluidStack.getFluid().isSame(Fluids.WATER)) {
        return BiomeColors.getAverageWaterColor(clientLevel, pos) | 0xFF000000;
      }
    }

    if (fluidStack.isEmpty()) {
      return 0;
    }

    var fluidModel = Minecraft.getInstance()
        .getModelManager()
        .getFluidStateModelSet()
        .get(fluidStack.getFluid().defaultFluidState());
    var tintSource = fluidModel.fluidTintSource();
    if (tintSource == null) {
      return -1;
    }
    return tintSource.colorAsStack(fluidStack);
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
