package org.magenpurp.Onfimarist.ServerChatPlus.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.magenpurp.Onfimarist.ServerChatPlus.Main;

import static org.magenpurp.Onfimarist.ServerChatPlus.files.Messages.joinMessage;
import static org.magenpurp.Onfimarist.ServerChatPlus.files.Messages.leaveMessage;

public class JoinLeaveEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission("scp.messages.join")) {
            e.setJoinMessage(null); return;
        }
        e.setJoinMessage(Main.messages.getString(joinMessage).replace("%player", e.getPlayer().getDisplayName()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (!e.getPlayer().hasPermission("scp.messages.leave")) {
            e.setQuitMessage(null); return;
        }
        e.setQuitMessage(Main.messages.getString(leaveMessage).replace("%player%", e.getPlayer().getDisplayName()));
    }
}
