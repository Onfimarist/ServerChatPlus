package org.magenpurp.Onfimarist.ServerChatPlus.Events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.magenpurp.Onfimarist.ServerChatPlus.files.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.magenpurp.Onfimarist.ServerChatPlus.Main;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChatEvent implements Listener {

    public static boolean chat = true;

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (!chat && !p.hasPermission("immortalcommand.bypass.chatdisable")) {
            e.setCancelled(true);
            p.sendMessage(Main.messages.getString(Messages.serverChatIsDisabled));

            //SendMessage

        }

        //Prefix and Suffix
        if (Main.chat != null) {
            if (Main.chat.getPlayerPrefix(p) != null) e.setFormat(Main.chat.getPlayerPrefix(p) + e.getMessage());
            if (Main.chat.getPlayerSuffix(p) != null) e.setFormat(e.getFormat() + Main.chat.getPlayerSuffix(p));
        }

        SendMessage(e.getMessage(), e.getPlayer());
    }

    private void SendMessage(String message, Player player) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ONLINE");
        out.writeUTF("ImmortalChatMessage");

        ByteArrayOutputStream msgBytes = new ByteArrayOutputStream();
        DataOutputStream msgOut = new DataOutputStream(msgBytes);
        try {
            msgOut.writeUTF(message);
            msgOut.writeShort(123);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        out.writeShort(msgBytes.toByteArray().length);
        out.write(msgBytes.toByteArray());

        player.sendPluginMessage(JavaPlugin.getPlugin(Main.class), "BungeeCord", out.toByteArray());
    }
}
