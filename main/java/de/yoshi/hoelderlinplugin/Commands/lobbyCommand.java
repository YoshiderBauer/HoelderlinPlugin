package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class lobbyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Main.log(Main.NOPERMISSION);
            return true;
        }

        Player player = (Player) sender;

        player.sendMessage(Main.PREFIX + "Du wirst nun weitergeleitet.");
        player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 0.2f, 1.2f);
        Main.sendServer(player, "Lobby");


        return true;
    }
}
