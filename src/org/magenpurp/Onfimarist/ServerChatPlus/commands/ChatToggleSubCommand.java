package org.magenpurp.Onfimarist.ServerChatPlus.commands;

import org.magenpurp.Onfimarist.ServerChatPlus.Main;
import org.bukkit.command.CommandSender;
import org.magenpurp.api.command.SubCommand;
import org.magenpurp.Onfimarist.ServerChatPlus.Events.ChatEvent;

import static org.magenpurp.Onfimarist.ServerChatPlus.files.Messages.*;

public class ChatToggleSubCommand extends SubCommand {

    public ChatToggleSubCommand(String name, String[] permissions) {
        super(name, permissions);
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (ChatEvent.chat) {
            ChatEvent.chat = false;
            s.sendMessage(Main.messages.getString(serverChatDisabled));
            return;
        }
        ChatEvent.chat = true;
        s.sendMessage(Main.messages.getString(serverChatEnabled));
    }

    @Override
    public boolean canSee(CommandSender s) {
        return true;
    }
}
