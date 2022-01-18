package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.tpsUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public class tpsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        double TPS = tpsUtils.getTPS();
        DecimalFormat tpsFormat = new DecimalFormat("#.##");

        if(!(commandSender instanceof Player)){
            Main.log(Main.PREFIX + "Aktuelle TPS: " + tpsFormat.format(TPS));
            return true;
        }

        Player player = (Player) commandSender;

        if(TPS > 20){
            player.sendMessage( Main.PREFIX + "§rAktuelle TPS: §a" + tpsFormat.format(TPS));
        } else if(TPS < 20 && TPS > 10){
            player.sendMessage(Main.PREFIX + "§rAktuelle TPS: §e" + tpsFormat.format(TPS));
        } else if(TPS < 10){
            player.sendMessage(Main.PREFIX + "§rAktuelle TPS: §c" + tpsFormat.format(TPS));
        }
        return true;
    }
}
