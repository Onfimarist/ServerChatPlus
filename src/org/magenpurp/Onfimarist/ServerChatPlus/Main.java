package org.magenpurp.Onfimarist.ServerChatPlus;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.magenpurp.Onfimarist.ServerChatPlus.Events.ChatEvent;
import org.magenpurp.Onfimarist.ServerChatPlus.commands.ChatToggleSubCommand;
import org.magenpurp.Onfimarist.ServerChatPlus.commands.ImmortalChatCommand;
import org.magenpurp.Onfimarist.ServerChatPlus.Events.ChatLogEvent;
import org.magenpurp.Onfimarist.ServerChatPlus.Events.JoinLeaveEvent;
import org.magenpurp.Onfimarist.ServerChatPlus.files.Messages;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.magenpurp.api.MagenAPI;
import org.magenpurp.api.versionsupport.VersionSupport;

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
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        new MagenAPI(this).initCMD().loadMagenAPI();
        VersionSupport versionSupport = MagenAPI.getVersionSupport();

        if (!setupChat()) {
            getLogger().warning("Vault plugin not found!\n ImmortalChat will not be able to\n use all of it's functions.");
        }

        ImmortalChatCommand iChatCommand = new ImmortalChatCommand();
        iChatCommand.setAliases(Collections.singletonList("ic"));
        iChatCommand.addSubCommand(new ChatToggleSubCommand("toggle", new String[] {"immortalchat.admin.togglechat"}), "Toggle Chat!");
        versionSupport.registerCommand(iChatCommand);

        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatLogEvent(),this);
        getServer().getPluginManager().registerEvents(new JoinLeaveEvent(), this);

        messages = new Messages();

        Logger logger = this.getLogger();

        new UpdateChecker(this, 72063).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There is not a new update available.");
            } else {
                logger.info("There is a new update available.");
            }
        });
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

    public void disablePlugin(String reason) {
        getLogger().warning(reason);
        this.getPluginLoader().disablePlugin(this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        short len = in.readShort();
        byte[] msgBytes = new byte[len];
        in.readFully(msgBytes);

        if (!subChannel.equals("ImmortalChatMessage"))
            return;

        DataInputStream msgIn = new DataInputStream(new ByteArrayInputStream(msgBytes));
        try {
            String someData = msgIn.readUTF();
            short someNumber = msgIn.readShort();

            if (someNumber != 123) {
                Bukkit.getLogger().warning("Critical Error. I have no idea what just happened.");
                return;
            }

            p.chat(someData);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
