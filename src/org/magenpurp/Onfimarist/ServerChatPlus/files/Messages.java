package org.magenpurp.onfimarist.ServerChatPlus.files;

import org.magenpurp.api.utils.FileManager;

public class Messages extends FileManager {
    public static final String offlineNonExistent = "Offline-Or-Non-Existent";
    public static final String serverChatDisabled = "Server-Chat-Disabled";
    public static final String serverChatEnabled = "Server-Chat-Enabled";
    public static final String serverChatHasDisabled = "Server-Chat-Has-Been-Disabled";
    public static final String serverChatHasEnabled = "Server-Chat-Has-Been-Enabled";
    public static final String serverChatIsDisabled = "Server-Chat-Is-Disabled";
    public static final String joinMessage = "Player-Join-Message";
    public static final String leaveMessage = "Player-Leave-Message";
    public static final String tooFewArguments = "Too-Few-Arguments";
    public static final String noPermission = "Not-Enough-Permission";

    public Messages() {
        super("Messages");
        addDefault(offlineNonExistent, "&7This player is offline or doesn't exist.");
        addDefault(serverChatDisabled, "&7Server chat has been disabled.");
        addDefault(serverChatEnabled, "&7Server chat has been enabled.");
        addDefault(serverChatHasDisabled, "&7Server chat is already disabled.");
        addDefault(serverChatHasEnabled, "&7Server chat is already enabled.");
        addDefault(serverChatIsDisabled, "&7Server chat is disabled!");
        addDefault(joinMessage, "&7PLayer %player% has joined the server!");
        addDefault(leaveMessage, "&7Player %player% has left the server!");
        addDefault(tooFewArguments, "&7Too few arguments.");
        addDefault(noPermission, "&7You don't have access to this command.");
        copyDefaults();
        save();

    }
}
