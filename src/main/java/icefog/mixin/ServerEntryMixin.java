package icefog.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public abstract class ServerEntryMixin {

	@Accessor
	abstract MultiplayerScreen getScreen();

	@Unique
	private boolean isValidIndex(int i) {
		MultiplayerServerListWidget.ServerEntry entry = (MultiplayerServerListWidget.ServerEntry)(Object)this;
		return (i >= 0) && (i < getScreen().getServerList().size());
	}

	@Inject(at = @At("HEAD"), method = "swapEntries", cancellable = true)
	private void swapEntries(int i, int j, CallbackInfo info) {
		if (!isValidIndex(i) || !isValidIndex(j)) {
			info.cancel();
		}
	}
}
