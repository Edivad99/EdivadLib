package edivad.edivadlib.tools.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

public class FluidUtils {

  //Render liquid
  public static float getRed(int color) {
    return (color >> 16 & 0xFF) / 255.0F;
  }

  public static float getGreen(int color) {
    return (color >> 8 & 0xFF) / 255.0F;
  }

  public static float getBlue(int color) {
    return (color & 0xFF) / 255.0F;
  }

  public static float getAlpha(int color) {
    return (color >> 24 & 0xFF) / 255.0F;
  }

  public static void color(int color) {
    RenderSystem.setShaderColor(getRed(color), getGreen(color), getBlue(color), getAlpha(color));
  }

  @Nullable
  public static TextureAtlasSprite getFluidTexture(@NotNull FluidStack fluidStack) {
    var extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
    var stillTexture = extensions.getStillTexture(fluidStack);
    return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
  }

  public static int getLiquidColorWithBiome(@NotNull FluidStack fluidStack, Level level,
      BlockPos pos) {
    if (level.isClientSide) {
      if (fluidStack.getFluid().isSame(Fluids.WATER)) {
        return BiomeColors.getAverageWaterColor(level, pos) | 0xFF000000;
      }
    }

    return IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack);
  }

  public static int getLiquidColorWithBiome(@NotNull FluidStack fluid,
      @NotNull BlockEntity blockEntity) {
    return getLiquidColorWithBiome(fluid, blockEntity.getLevel(), blockEntity.getBlockPos());
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