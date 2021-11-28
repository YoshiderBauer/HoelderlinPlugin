package de.yoshi.hoelderlinplugin.listener;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.fileconfig;
import de.yoshi.hoelderlinplugin.utils.settingsUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Random;

public class serverPing implements Listener {
    @EventHandler
    private void onPingEvent(ServerListPingEvent event){
        Random random = new Random();
        int rand = random.nextInt(1000-10) + 10;
        event.setMaxPlayers(rand);
        int Rand = random.nextInt(7) + 1;

        fileconfig settings = new fileconfig("settings.yml");
        if(Rand == 1){
            event.setMotd(settings.getString("Motd1"));
        } else if(Rand == 2) {
            event.setMotd(settings.getString("Motd2"));
        } else if (Rand == 3) {
            event.setMotd(settings.getString("Motd3"));
        } else if (Rand == 4) {
            event.setMotd(settings.getString("Motd4"));
        } else if (Rand == 5) {
            event.setMotd(settings.getString("Motd5"));
        } else if (Rand == 6){
            event.setMotd(settings.getString("Motd6"));
        } else if (Rand == 7){
            event.setMotd(settings.getString("Motd7"));
        }
        else {
            event.setMotd("§cCraft Attack Plugin von Yoshi");
        }

        if(settingsUtils.strToBool(settings.getString("showPing")) == true){
            Main.log("§cPing " + event.getAddress());
        }
    }

}
