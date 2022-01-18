package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.fileconfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class camCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Main.log(Main.NOPERMISSION);
            return false;
        }

        Player player = (Player) sender;

        if(!(player.hasPermission("op"))){
            player.sendMessage(Main.PREFIX + Main.NOPERMISSION);
            return false;
        }
        fileconfig status = new fileconfig("status.yml");
        if(args[0] == ""){
            player.sendMessage(Main.PREFIX + "§cDu musst noch einen Cam-Account eingeben.");
            return false;
        } else {
            Player cam = Bukkit.getServer().getPlayer(args[0]);
            if(cam == null){
                player.sendMessage(Main.PREFIX + " §cDieser Spieler existiert nicht!");
                return true;
            } else if(status.getBoolean(cam.getName()) == false){
                cam.setGameMode(GameMode.SPECTATOR);
                cam.setPlayerListName("§f§7[CAM] " + cam.getName());
                cam.sendMessage(Main.PREFIX + "Du bist nun als Cam-Account registriert!");
                status.set(cam.getName(), true);
                status.saveConfig();
                return true;
            } else if(status.getBoolean(cam.getName()) == true){
                cam.setGameMode(GameMode.SURVIVAL);
                if(cam.hasPermission("op")){
                    cam.setPlayerListName("[§cADMIN§f] " + cam.getName());
                } else {
                    cam.setPlayerListName("[§aPlayer§f] " + cam.getName());
                }
                cam.sendMessage(Main.PREFIX + "Du bist nun wieder ein normaler Spieler!");
                status.set(cam.getName(), false);
                status.saveConfig();
                return true;
            }
        }
        return false;
    }
}
