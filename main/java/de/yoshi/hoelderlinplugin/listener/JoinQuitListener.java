package de.yoshi.hoelderlinplugin.listener;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.fileconfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinQuitListener implements Listener {

    @EventHandler
    private void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setPlayerListHeader("            " + Main.PREFIX + "         ");
        player.setPlayerListFooter(" ");

        event.setJoinMessage(Main.PREFIX + "§a" + player.getDisplayName() + " §7hat den Server betreten.");

        fileconfig status = new fileconfig("status.yml");
        fileconfig afk = new fileconfig("afk.yml");
        if(afk.getBoolean(player.getName()) == false && player.hasPermission("op")){
            player.setPlayerListName("[§cADMIN§f] " + player.getName());
        } else if(afk.getBoolean(player.getName()) == false &! player.hasPermission("op")){
            player.setPlayerListName("[§aPlayer§f] " + player.getName());
        } else if (afk.getBoolean(player.getName()) == true){
            player.setPlayerListName("§7[AFK] " + player.getName());
            player.sendMessage(Main.PREFIX + "§7Du bist noch AFK!");
        } else if (status.getBoolean(player.getName()) == true){
            player.setPlayerListName("§f§7[CAM] " + player.getName());
            player.setGameMode(GameMode.SPECTATOR);
        }

        fileconfig start = new fileconfig("start.yml");
        if(start.getBoolean("start") == false){
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
            player.setGameMode(GameMode.ADVENTURE);
            player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 999999, 255, true, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999, 255, true, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 255, true, false, false));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 255, true, false, false));
        }

        if(!(status.isSet(player.getName()))){
            status.set(player.getName(), false);
            Main.log(player.getName() + " hat zum ersten Mal den Server betreten!");
        }

    }

    @EventHandler
    private void playerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage(Main.PREFIX + "§c" + player.getDisplayName() + " §7hat den Server verlassen.");
    }
}
