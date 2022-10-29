package de.yoshi.hoelderlinplugin.listener;

import de.yoshi.hoelderlinplugin.utils.fileconfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SpawnElytra implements Listener {
    private final int multiplyValue;
    private final int spawnRadius;
    private final List<Player> flying = new ArrayList<>();
    private final List<Player> boosted = new ArrayList<>();

    public SpawnElytra(Plugin plugin){
        fileconfig settings = new fileconfig("settings.yml");
        this.multiplyValue = settings.getInt("SpawnElytraBoost");
        this.spawnRadius = settings.getInt("SpawnRadius");

        Bukkit.getScheduler().runTaskTimer(plugin,() -> {
            Bukkit.getWorld("world").getPlayers().forEach(player -> {
            if(player.getGameMode() != GameMode.SURVIVAL) return;
            player.setAllowFlight(isInSpawnRadius(player));
            if(flying.contains(player) && !player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setGliding(false);
                boosted.remove(player);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    flying.remove(player);
                }, 5);
            }
            });}, 0,3 );

    }

    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent event){
        if(event.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if(!isInSpawnRadius(event.getPlayer())) return;
        event.setCancelled(true);
        event.getPlayer().setGliding(true);
        flying.add(event.getPlayer());
        //event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder("Drücke ").append(new KeybindComponent("key.swapOffhand")).append(" um dich zu Boosten!").create());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntityType() == EntityType.PLAYER && (event.getCause() == EntityDamageEvent.DamageCause.FALL || event.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) && flying.contains(event.getEntity())) event.setCancelled(true);
    }

    @EventHandler
    public void onSwapItem(PlayerSwapHandItemsEvent event){
        if(boosted.contains(event.getPlayer()) || event.getPlayer().getGameMode() == GameMode.CREATIVE || event.getPlayer().getGameMode() == GameMode.SPECTATOR || !event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isAir() || !event.getPlayer().isGliding()) return;
        event.setCancelled(true);
        boosted.add(event.getPlayer());
        event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(multiplyValue));
    }

    @EventHandler
    public void onToggleGlide(EntityToggleGlideEvent event){
        if(event.getEntityType() == EntityType.PLAYER && flying.contains(event.getEntity())) event.setCancelled(true);

    }

    private boolean isInSpawnRadius(Player player){
        if(!player.getWorld().getName().equals("world")) return false;
        return player.getWorld().getSpawnLocation().distance(player.getLocation()) <= spawnRadius;
    }
}
