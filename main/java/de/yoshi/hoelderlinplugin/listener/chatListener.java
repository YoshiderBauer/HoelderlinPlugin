package de.yoshi.hoelderlinplugin.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatListener implements Listener {

    @EventHandler

    private void ChatListener(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        event.setFormat(player.getName() + "§7 | §F" + event.getMessage());
    }
}
