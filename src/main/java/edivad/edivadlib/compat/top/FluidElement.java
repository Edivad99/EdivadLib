package edivad.edivadlib.compat.top;

import edivad.edivadlib.tools.utils.FluidUtils;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public abstract class FluidElement extends TOPElement {

    protected final FluidStack fluid;
    protected final int capacity;
    protected final int colorLiquid;

    protected FluidElement(@NotNull FluidStack fluid, int capacity, int colorLiquid) {
        super(0xFF000000, 0xFFFFFF);
        this.fluid = fluid;
        this.capacity = capacity;
        this.colorLiquid = colorLiquid;
    }

    public FluidElement(@NotNull FluidStack fluid, int capacity, BlockEntity blockentity) {
        this(fluid, capacity, FluidUtils.getLiquidColorWithBiome(fluid, blockentity));
    }

    public FluidElement(FriendlyByteBuf buf) {
        this(buf.readFluidStack(), buf.readInt(), buf.readInt());
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFluidStack(fluid);
        buf.writeInt(capacity);
        buf.writeInt(colorLiquid);
    }

    @Override
    public int getScaledLevel(int level) {
        if(capacity == 0 || fluid.getAmount() == Integer.MAX_VALUE) {
            return level;
        }
        long fluidAmount = fluid.getAmount();
        long result = fluidAmount * level / capacity;
        return (int) result;
    }

    @Override
    public TextureAtlasSprite getIcon() {
        return fluid.isEmpty() ? null : FluidUtils.getFluidTexture(fluid);
    }

    @Override
    public MutableComponent getText() {
        String liquidText = fluid.isEmpty() ? "Empty" : fluid.getDisplayName().getString();
        DecimalFormat f = new DecimalFormat("#,##0");
        int amount = fluid.getAmount();
        return Component.literal(String.format("%s: %smB", liquidText, f.format(amount)));
    }

    @Override
    protected boolean applyRenderColor() {
        FluidUtils.color(colorLiquid);
        return true;
    }
}
