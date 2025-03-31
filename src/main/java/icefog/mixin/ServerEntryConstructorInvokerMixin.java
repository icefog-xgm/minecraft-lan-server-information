package icefog.mixin;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MultiplayerServerListWidget.ServerEntry.class)
public interface ServerEntryConstructorInvokerMixin {
	@Invoker("<init>")
	static MultiplayerServerListWidget.ServerEntry invokeConstructor(
			MultiplayerServerListWidget omg,
			final MultiplayerScreen screen,
			final ServerInfo server
	) {
		throw new AssertionError();
	};
}
