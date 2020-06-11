package org.magenpurp.onfimarist.ServerChatPlus.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.magenpurp.onfimarist.ServerChatPlus.Main;
import org.magenpurp.onfimarist.ServerChatPlus.files.Messages;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (!e.getPlayer().hasPermission("scp.messages.leave")) {
            e.setQuitMessage(null);
        } else {
            e.setQuitMessage(Main.messages.getString(Messages.leaveMessage));
        }
    }
}
