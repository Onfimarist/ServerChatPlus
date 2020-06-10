package org.magenpurp.onfimarist.ServerChatPlus.commands;

import org.magenpurp.onfimarist.ServerChatPlus.Main;
import org.bukkit.command.CommandSender;
import org.magenpurp.api.command.SubCommand;
import org.magenpurp.onfimarist.ServerChatPlus.Events.ChatEvent;
import org.magenpurp.onfimarist.ServerChatPlus.files.Messages;

public class ChatToggleSubCommand extends SubCommand {

    public ChatToggleSubCommand(String name, String[] permissions) {
        super(name, permissions);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (ChatEvent.chat) {
            ChatEvent.chat = false;
            s.sendMessage(Main.messages.getString(Messages.serverChatDisabled));
            return;
        }
        ChatEvent.chat = true;
        s.sendMessage(Main.messages.getString(Messages.serverChatEnabled));
    }

    @Override
    public boolean canSee(CommandSender s) {
        return true;
    }
}
