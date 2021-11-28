package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.fileconfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class afkCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            Main.log(Main.NOPERMISSION);
            return true;
        }

        Player player = (Player) sender;
        fileconfig afk = new fileconfig("afk.yml");
        fileconfig status = new fileconfig("status.yml");

        if (!(afk.isSet(player.getName()))) {
            afk.set(player.getName(), false);
            afk.saveConfig();
        }

        if(status.getString(player.getName()) == "cam"){
            player.sendMessage(Main.PREFIX + "Du bist noch als Cam registriert!");
            return true;
        } else if(afk.getBoolean(player.getName()) == false){
            player.setPlayerListName("§7[AFK] " + player.getName());
            player.sendMessage(Main.PREFIX + "§7Du bist nun AFK!");
            for(Player all : Bukkit.getOnlinePlayers()){
                if(all != player){
                    all.sendMessage(Main.PREFIX + "§7 " + player.getName() + " ist nun AFK!");
                }
            }

            afk.set(player.getName(), true);
            afk.saveConfig();
            return true;
        } else if (afk.getBoolean(player.getName()) == true && player.hasPermission("op")) {
            player.setPlayerListName("[§cADMIN§f] " + player.getName());
            player.sendMessage(Main.PREFIX + "§7Du bist nun nichtmehr AFK!");
            afk.set(player.getName(), false);
            afk.saveConfig();
            return true;
        } else if (afk.getBoolean(player.getName()) == true &! player.hasPermission("op")){
            player.setPlayerListName("[§aPlayer§f] " + player.getName());
            player.sendMessage(Main.PREFIX + "§7Du bist nun nichtmehr AFK!");
            afk.set(player.getName(), false);
            afk.saveConfig();
            return true;
        } else {
            return true;
        }
    }

    /*
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tabComplete = new ArrayList<String>();
        tabComplete.add("<Grund>");
        return tabComplete;
    }
    */

}
