package edivad.edivadlib.setup;

import edivad.edivadlib.Main;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.VersionChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateChecker {

    private final String modId;

    public UpdateChecker(String modId) {
        this.modId = modId;
    }

    @SubscribeEvent
    public void handlePlayerLoggedInEvent(ClientPlayerNetworkEvent.LoggingIn event) {
        try {
            var modInfo = ModList.get().getModFileById(modId).getMods().get(0);
            String qualifier = modInfo.getVersion().getQualifier();
            if(qualifier != null && qualifier.contains("NONE"))
                return;
            var versionRAW = VersionChecker.getResult(modInfo);
            if(versionRAW.target() == null)
                return;

            var status = versionRAW.status();
            LocalPlayer player = event.getPlayer();

            List<String> messages = new ArrayList<>();
            if(status.equals(VersionChecker.Status.OUTDATED) && versionRAW.changes().containsKey(versionRAW.target())) {
                String changes = versionRAW.changes().get(versionRAW.target());

                messages.add(String.format("%s[%s]%s A new version is available (%s), please update!",
                        ChatFormatting.GREEN, modId, ChatFormatting.WHITE, versionRAW.target()));
                messages.add(ChatFormatting.YELLOW + "Changelog:");

                Arrays.stream(changes.split("\n"))
                        .map(change -> ChatFormatting.WHITE + "- " + change)
                        .collect(Collectors.toCollection(() -> messages));
                if(versionRAW.changes().size() > 1) {
                    messages.add(ChatFormatting.WHITE + "- And more...");
                }
            }
            messages.stream()
                    .map(Component::literal)
                    .forEach(message -> player.displayClientMessage(message, false));

        }
        catch(Exception e) {
            Main.LOGGER.warn("Unable to check the version", e);
        }
    }
}
