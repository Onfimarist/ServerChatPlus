package org.magenpurp.onfimarist.ServerChatPlus.commands;

import org.magenpurp.onfimarist.ServerChatPlus.Main;
import org.bukkit.command.CommandSender;
import org.magenpurp.api.command.ParentCommand;
import org.magenpurp.onfimarist.ServerChatPlus.files.Messages;

public class ServerChatPlusCommand extends ParentCommand {

    public ServerChatPlusCommand() {
        super("serverChatPlus");
    }

    @Override
    public void sendDefaultMessage(CommandSender s) {
        if (s.hasPermission("scp.commands")) {
            showCommandsList(s);
       }
    }

    @Override
    public String noPermissionMessage() {
        return Main.messages.getString(Messages.noPermission);
    }
}
