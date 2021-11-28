package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class pingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Main.log(Main.NOPERMISSION);
            return true;
        }

        Player player = (Player) sender;
        player.sendMessage(Main.PREFIX + "Dein Ping liegt bei: " + player.getPing() + "ms!");
        return true;
    }
}
