package icefog.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerServerListWidget.class)
public abstract class ExampleMixin {
	@Shadow protected abstract void updateEntries();

	@Shadow @Final private MultiplayerScreen screen;

	@Unique
	private ServerInfo lan_to_server_info(LanServerInfo info) {
		return new ServerInfo(info.getMotd(), info.getAddressPort(), ServerInfo.ServerType.LAN);
	}

	@Unique
	private MultiplayerServerListWidget.Entry replace_lan_entry(MultiplayerServerListWidget widget, MultiplayerServerListWidget.Entry entry) {
		if (entry instanceof MultiplayerServerListWidget.LanServerEntry lan_entry) {
			ServerInfo info = lan_to_server_info(lan_entry.getLanServerEntry());
			return ServerEntryConstructorInvokerMixin.invokeConstructor(widget, this.screen, info);
		}
		else {
			return entry;
		}
	}

	@Inject(at = @At("RETURN"), method = "updateEntries")
	private void updateEntriesHook(CallbackInfo info) {
		MultiplayerServerListWidget widget = ((MultiplayerServerListWidget)(Object)this);
		widget.children().replaceAll((entry) -> this.replace_lan_entry(widget, entry));
	}
}