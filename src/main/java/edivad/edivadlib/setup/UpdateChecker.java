package edivad.edivadlib.setup;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.VersionChecker;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

public record UpdateChecker(String modId) {

  @SubscribeEvent
  void handlePlayerLoggedInEvent(ClientPlayerNetworkEvent.LoggingIn event) {
    var modInfo = ModList.get().getModFileById(this.modId()).getMods().get(0);
    var modName = modInfo.getDisplayName();
    var result = VersionChecker.getResult(modInfo);
    var versionStatus = result.status();
    if (versionStatus.shouldDraw()) {
      var newVersion = result.target().toString();
      var modUrl = modInfo.getModURL().get().toString();
      var message = Component.literal(modName + ": ").withStyle(ChatFormatting.GREEN)
          .append(Component.literal(
                  String.format("A new version (%s) is available to download.", newVersion))
              .withStyle(style -> style
                  .withColor(ChatFormatting.WHITE)
                  .withUnderlined(true)
                  .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, modUrl))));
      event.getPlayer().displayClientMessage(message, false);
    }
  }
}
