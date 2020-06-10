package org.magenpurp.Onfimarist.ServerChatPlus.commands;

import org.magenpurp.Onfimarist.ServerChatPlus.Main;
import org.bukkit.command.CommandSender;
import org.magenpurp.api.command.ParentCommand;

import static org.magenpurp.Onfimarist.ServerChatPlus.files.Messages.noPermission;

public class ImmortalChatCommand extends ParentCommand {

    public ImmortalChatCommand() {
        super("immortalChat");
    }

    @Override
    public void sendDefaultMessage(CommandSender s) {
        if (s.hasPermission("immoralcommand.commands")) {
            showCommandsList(s);
       }
    }

    @Override
    public String noPermissionMessage() {
        return Main.messages.getString(noPermission);
    }
}
