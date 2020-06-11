package org.magenpurp.onfimarist.ServerChatPlus.Events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.magenpurp.onfimarist.ServerChatPlus.Main;
import org.magenpurp.onfimarist.ServerChatPlus.files.Messages;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("scp.messages.join")) {
            e.setJoinMessage(null);
        } else {
            e.setJoinMessage(Main.messages.getString(Messages.joinMessage).replace("%player%", e.getPlayer().getDisplayName()));
        }

        if (e.getPlayer().hasPermission("scp.update")) {
            e.getPlayer().sendMessage(ChatColor.AQUA + "ServerChatPlus > " + ChatColor.RESET + "A new update is available! Download it here:\n https://github.com/Onfimarist/ServerChatPlus/releases/latest");
        }
    }
}
