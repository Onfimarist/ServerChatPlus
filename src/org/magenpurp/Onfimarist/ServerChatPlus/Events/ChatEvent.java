package org.magenpurp.onfimarist.ServerChatPlus.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.magenpurp.onfimarist.ServerChatPlus.Main;
import org.magenpurp.onfimarist.ServerChatPlus.files.Messages;

public class ChatEvent implements Listener {

    public static boolean chat = true;

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (!chat && p.hasPermission("scp.bypass.chatdisable")) return;
        if (!chat && !p.hasPermission("scp.bypass.chatdisable")) {
            e.setCancelled(true);
            p.sendMessage(Main.messages.getString(Messages.serverChatIsDisabled));
        }

        //Prefix and Suffix
        if (Main.chat != null) {
            if (Main.chat.getPlayerPrefix(p) != null) e.setFormat(Main.chat.getPlayerPrefix(p) + e.getMessage());
            if (Main.chat.getPlayerSuffix(p) != null) e.setFormat(e.getFormat() + Main.chat.getPlayerSuffix(p));
        }
    }
}
