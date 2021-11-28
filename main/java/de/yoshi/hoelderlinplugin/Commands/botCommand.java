package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.npcUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class botCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 0){
            player.sendMessage(Main.PREFIX + "§cDu musst noch einen Namen eingeben.");
            return true;
        } else if(args.length > 16){
            player.sendMessage(Main.PREFIX + "§cDer Name darf höchstens 16 Zeichen lang sein!");
            return true;
        } else{
            npcUtils.createNPC(player, args[0], args[1]);
            Bukkit.broadcastMessage(Main.PREFIX + "§a" + args[0] + " §7hat den Server betreten.");
            //player.sendMessage(Main.PREFIX + "NPC erfolgreich erstellt");
        }

        return true;
    }

}
