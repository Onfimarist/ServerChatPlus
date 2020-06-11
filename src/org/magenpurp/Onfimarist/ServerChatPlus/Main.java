package org.magenpurp.onfimarist.ServerChatPlus;

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

import java.util.Collections;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static Messages messages;
    public static Chat chat = null;

    @Override
    public void onEnable() {
        new MagenAPI(this).initCMD().loadMagenAPI();
        VersionSupport versionSupport = MagenAPI.getVersionSupport();
        setupChat();

        ServerChatPlusCommand serverChatPlusCommand = new ServerChatPlusCommand();
        serverChatPlusCommand.setAliases(Collections.singletonList("scp"));
        serverChatPlusCommand.addSubCommand(new ChatToggleSubCommand("toggle", new String[] {"scp.admin.togglechat"}), "Toggle Chat!");
        versionSupport.registerCommand(serverChatPlusCommand);

        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatLogEvent(),this);
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new LeaveEvent(), this);

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

}
