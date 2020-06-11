package org.magenpurp.onfimarist.ServerChatPlus;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.magenpurp.onfimarist.ServerChatPlus.Events.ChatEvent;
import org.magenpurp.onfimarist.ServerChatPlus.Events.JoinEvent;
import org.magenpurp.onfimarist.ServerChatPlus.Events.LeaveEvent;
import org.magenpurp.onfimarist.ServerChatPlus.commands.ChatToggleSubCommand;
import org.magenpurp.onfimarist.ServerChatPlus.commands.ServerChatPlusCommand;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.magenpurp.api.MagenAPI;
import org.magenpurp.api.versionsupport.VersionSupport;
import org.magenpurp.onfimarist.ServerChatPlus.Events.ChatLogEvent;
import org.magenpurp.onfimarist.ServerChatPlus.files.Messages;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

public class Main extends JavaPlugin implements PluginMessageListener {

    public static Messages messages;
    public static Chat chat = null;

    @Override
    public void onEnable() {
        new MagenAPI(this).initCMD().loadMagenAPI();
        VersionSupport versionSupport = MagenAPI.getVersionSupport();
        setupChat();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        ServerChatPlusCommand serverChatPlusCommand = new ServerChatPlusCommand();
        serverChatPlusCommand.setAliases(Collections.singletonList("scp"));
        serverChatPlusCommand.addSubCommand(new ChatToggleSubCommand("toggle", new String[] {"scp.admin.togglechat"}), "Toggle Chat!");
        versionSupport.registerCommand(serverChatPlusCommand);

        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getLogger().info("Event registered.");
        getServer().getPluginManager().registerEvents(new ChatLogEvent(),this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(), this);

        messages = new Messages();

        Logger logger = this.getLogger();

        /*new UpdateChecker(this, 72063).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There is not a new update available.");
            } else {
                logger.info("There is a new update available.");
            }
        });*/
    }

    @Override
    public void onDisable() {

    }

    private boolean setupChat() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        Bukkit.getLogger().info("Received message");
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        getLogger().info("SubChannel = " + subChannel);

        if (subChannel.equals("ServerChatPlusMessage")) {
            short len = in.readShort();
            byte[] msgBytes = new byte[len];
            in.readFully(msgBytes);

            DataInputStream msgIn = new DataInputStream(new ByteArrayInputStream(msgBytes));
            try {
                String playerMessage = msgIn.readUTF();
                getLogger().info("SomeData = " + playerMessage);
                short readShort = msgIn.readShort();

                p.chat(playerMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
