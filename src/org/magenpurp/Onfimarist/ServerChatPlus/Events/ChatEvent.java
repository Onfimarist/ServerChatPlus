package org.magenpurp.onfimarist.ServerChatPlus.Events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.magenpurp.onfimarist.ServerChatPlus.Main;
import org.magenpurp.onfimarist.ServerChatPlus.files.Messages;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChatEvent implements Listener {

    public static boolean chat = true;

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (!chat && !p.hasPermission("scp.bypass.chatdisable")) {
            e.setCancelled(true);
            p.sendMessage(Main.messages.getString(Messages.serverChatIsDisabled));
        }

        //Prefix and Suffix
        if (Main.chat != null) {
            if (Main.chat.getPlayerPrefix(p) != null) e.setFormat(Main.chat.getPlayerPrefix(p) + e.getMessage());
            if (Main.chat.getPlayerSuffix(p) != null) e.setFormat(e.getFormat() + Main.chat.getPlayerSuffix(p));
        }

        SendMessage(e.getMessage(), e.getPlayer());
    }

    private void SendMessage(String m, Player p) {
        Bukkit.getLogger().info("Started sending message.");
        Bukkit.getLogger().info("Message is: " + m);
        Bukkit.getLogger().info("Player is: " + p.getName());
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        Bukkit.getLogger().info("Forward");
        out.writeUTF("ONLINE");
        Bukkit.getLogger().info("Online");
        out.writeUTF("ServerChatPlusMessage");
        Bukkit.getLogger().info("ServerChatPlusMessage");

        ByteArrayOutputStream msgBytes = new ByteArrayOutputStream();
        DataOutputStream msgOut = new DataOutputStream(msgBytes);
        try {
            msgOut.writeUTF(m);
            Bukkit.getLogger().info("Data written");
            msgOut.writeShort(123);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.writeShort(msgBytes.toByteArray().length);
        Bukkit.getLogger().info("Short Written");
        out.write(msgBytes.toByteArray());
        Bukkit.getLogger().info("Message Written");

        p.sendPluginMessage(JavaPlugin.getPlugin(Main.class), "BungeeCord", out.toByteArray());
        Bukkit.getLogger().info("Message Sent");
    }
}
