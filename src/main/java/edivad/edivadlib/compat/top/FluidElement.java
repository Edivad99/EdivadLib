package edivad.edivadlib.compat.top;


public abstract class FluidElement extends TOPElement {

//  protected final FluidStack fluid;
//  protected final int capacity;
//  protected final int colorLiquid;
//
//  protected FluidElement(FluidStack fluid, int capacity, int colorLiquid) {
//    super(0xFF000000, 0xFFFFFF);
//    this.fluid = fluid;
//    this.capacity = capacity;
//    this.colorLiquid = colorLiquid;
//  }
//
//  public FluidElement(FluidStack fluid, int capacity, BlockEntity blockentity) {
//    this(fluid, capacity, FluidUtils.getLiquidColorWithBiome(fluid, blockentity));
//  }
//
//  public FluidElement(RegistryFriendlyByteBuf buf) {
//    this(FluidStack.OPTIONAL_STREAM_CODEC.decode(buf), buf.readInt(), buf.readInt());
//  }
//
//  @Override
//  public void toBytes(RegistryFriendlyByteBuf buf) {
//    FluidStack.OPTIONAL_STREAM_CODEC.encode(buf, fluid);
//    buf.writeInt(capacity);
//    buf.writeInt(colorLiquid);
//  }
//
//  @Override
//  public int getScaledLevel(int level) {
//    if (capacity == 0 || fluid.getAmount() == Integer.MAX_VALUE) {
//      return level;
//    }
//    long fluidAmount = fluid.getAmount();
//    long result = fluidAmount * level / capacity;
//    return (int) result;
//  }
//
//  @Nullable
//  @Override
//  public TextureAtlasSprite getIcon() {
//    return fluid.isEmpty() ? null : FluidUtils.getFluidTexture(fluid);
//  }
//
//  @Override
//  public MutableComponent getText() {
//    String liquidText = fluid.isEmpty() ? "Empty" : fluid.getHoverName().getString();
//    DecimalFormat f = new DecimalFormat("#,##0");
//    int amount = fluid.getAmount();
//    return Component.literal(String.format("%s: %smB", liquidText, f.format(amount)));
//  }
//
//  @Override
//  protected boolean applyRenderColor(GuiGraphics guiGraphics) {
//    FluidUtils.color(guiGraphics, colorLiquid);
//    return true;
//  }
}
