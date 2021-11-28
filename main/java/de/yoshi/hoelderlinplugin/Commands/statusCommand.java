package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.fileconfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class statusCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Main.log(Main.NOPERMISSION);
            return true;
        }

        Player player = (Player) sender;
        fileconfig status = new fileconfig("status.yml");

        if(status.get(player.getName()) == "cam"){
            player.sendMessage(Main.PREFIX + Main.NOPERMISSION);
            return false;
        }

        if(args[1].isEmpty()){
            player.sendMessage(Main.PREFIX + "§cDu musst noch eienen Status eingeben.");
            return true;
        } else if (args[1] == "afk"){
            player.setPlayerListName("§7[AFK] " + player.getName());
            if(args[2].isEmpty()){
                player.sendMessage(Main.PREFIX + "§7Du bist nun AFK!");
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(all != player){
                        all.sendMessage(Main.PREFIX + "§7 " + player.getName() + " ist nun AFK!");
                    }
                }
            } else {
                player.sendMessage(Main.PREFIX + "§7Du bist nun AFK! (Grund: " + args[2] + ")");
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(all != player){
                        all.sendMessage(Main.PREFIX + "§7 " + player.getName() + " ist nun AFK (Grund: " + args[2] + ")!");
                    }
                }
            }

            status.set(player.getName(), "afk");
            status.saveConfig();
        } else if(args[1] == "player"){
            player.setPlayerListName("[§aPlayer§f] " + player.getName());
            if(status.get(player.getName()) == "afk"){
                player.sendMessage(Main.PREFIX + "Du bist nun nichtmehr AFK!");
                for(Player all : Bukkit.getOnlinePlayers()){
                    if(all != player){
                        all.sendMessage(Main.PREFIX + "§7 " + player.getName() + " ist nun nichtmehr AFK!");
                    }
                }
            } else {
                if(player.hasPermission("op")){
                    player.sendMessage(Main.PREFIX + "Dein Status wurde zu : §a§lPlayer §f§r geändert!");
                } else {
                    player.sendMessage(Main.PREFIX + "Dein Status wurde zurückgesetzt!");
                }
            }
            status.set(player.getName(), "player");
            status.saveConfig();
        } else if(args[1] == "admin"){
            if(!(player.hasPermission("op"))){
                player.sendMessage(Main.PREFIX + Main.NOPERMISSION);
                return false;
            }
            player.setPlayerListName("[§cAdmin§f] " + player.getName());
            status.set(player.getName(), "admin");
            status.saveConfig();
            player.sendMessage(Main.PREFIX + "Dein Status wurde zurückgesetzt!");
        } else if (args[1] == "reset"  && !(status.get(player.getName()) == "reset")){
            if(player.hasPermission("op")){
                player.setPlayerListName("[§cAdmin§f] " + player.getName());
                status.set(player.getName(), "admin");
                status.saveConfig();
                player.sendMessage(Main.PREFIX + "Dein Status wurde zurückgesetzt!");
            } else {
                player.setPlayerListName("[§aPlayer§f] " + player.getName());
                status.set(player.getName(), "player");
                status.saveConfig();
                player.sendMessage(Main.PREFIX + "Dein Status wurde zurückgesetzt!");
            }
        } else if (args[1] == "boom") {
            player.setPlayerListName("[§cBOOM§f] " + player.getName());
            status.set(player.getName(), "boom");
            status.saveConfig();
            player.sendMessage(Main.PREFIX + "Dein Status wurde zu : §c§lBOOM §f§r geändert!");
        } else {
            return true;
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> tabComplete = new ArrayList<String>();
        tabComplete.add("afk");
        tabComplete.add("admin");
        tabComplete.add("player");
        tabComplete.add("boom");
        tabComplete.add("reset");
        //tabComplete.add()
        return tabComplete;
    }
}
