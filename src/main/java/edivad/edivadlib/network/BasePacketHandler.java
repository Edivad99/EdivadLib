package edivad.edivadlib.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IDirectionAwarePayloadHandlerBuilder;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public abstract class BasePacketHandler {

  protected BasePacketHandler(IEventBus modEventBus, String modId, String version) {
    modEventBus.addListener(RegisterPayloadHandlerEvent.class, event -> {
      var registrar = event.registrar(modId).versioned(version);
      registerClientToServer(new PacketRegistrar(registrar, IDirectionAwarePayloadHandlerBuilder::server));
      registerServerToClient(new PacketRegistrar(registrar, IDirectionAwarePayloadHandlerBuilder::client));
    });
  }

  protected void registerClientToServer(PacketRegistrar registrar) {
  }

  protected void registerServerToClient(PacketRegistrar registrar) {
  }

  @FunctionalInterface
  protected interface ContextAwareHandler {
    <P extends CustomPacketPayload, T> void accept(
        IDirectionAwarePayloadHandlerBuilder<P, T> builder, T handler);
  }

  protected record PacketRegistrar(IPayloadRegistrar registrar, ContextAwareHandler handler) {
    public <T extends EdivadLibPacket> void play(ResourceLocation id, FriendlyByteBuf.Reader<T> reader) {
      registrar.play(id, reader, builder ->
          handler.accept(builder, EdivadLibPacket::handleMainThread));
    }
  }
}
