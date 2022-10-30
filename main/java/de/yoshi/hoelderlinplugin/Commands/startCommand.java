package de.yoshi.hoelderlinplugin.Commands;

import de.yoshi.hoelderlinplugin.Main;
import de.yoshi.hoelderlinplugin.utils.fileconfig;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class startCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            Main.log(Main.NOPERMISSION);
            return true;
        }
        Player player = (Player) sender;
        fileconfig start = new fileconfig("start.yml");

        if(!(player.hasPermission("op"))){
            player.sendMessage(Main.NOPERMISSION);
            return true;
        }
        World world = (World) Bukkit.getWorlds().get(0);
        if(start.getBoolean("start")){
            player.sendMessage(Main.PREFIX + "§cDas Event wurde schon gestartet!");
            return true;
        }



        start.set("start", true);
        start.saveConfig();
        world.getWorldBorder().setCenter(world.getSpawnLocation());
        world.getWorldBorder().setSize(29999984.0, 2000);
        world.setDifficulty(Difficulty.HARD);
        Bukkit.getServer().setDefaultGameMode(GameMode.SURVIVAL);
        fileconfig status = new fileconfig("status.yml");
        for(Player player1 : Bukkit.getOnlinePlayers()){
            if(status.getString(player1.getName()) != "cam"){
                player1.setGameMode(GameMode.SURVIVAL);
            }
            player1.removePotionEffect(PotionEffectType.WEAKNESS);
            player1.removePotionEffect(PotionEffectType.SATURATION);
            player1.removePotionEffect(PotionEffectType.REGENERATION);
            player1.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            player1.sendMessage(Main.PREFIX + "§aViel Spaß!");
        }

        player.sendMessage(Main.PREFIX + "§aDas Event wurde erfolgreich gestartet!");


        return true;
    }
}
