package org.magenpurp.onfimarist.ServerChatPlus.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ChatLogEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
            if (!e.getMessage().startsWith("/")) {
                fileCreate();
                writeToFile(e);
            }
    }

    private void fileCreate() {
        try {
            new File("plugins/ServerChatPlus").mkdirs();
            txtFile().createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeToFile(AsyncPlayerChatEvent e) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime hour = LocalDateTime.now();
            FileWriter fw = new FileWriter(txtFile(), true);
            fw.write("[" + format.format(hour) + "]" + e.getPlayer().getName() + " > " + e.getMessage());
            fw.write("\r\n");
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private File txtFile() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return new File ("plugins/ServerChatPlus", format.format(date) + ".txt");
    }
}
