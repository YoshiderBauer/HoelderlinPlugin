package de.yoshi.hoelderlinplugin;

import de.yoshi.hoelderlinplugin.Commands.*;
import de.yoshi.hoelderlinplugin.listener.JoinQuitListener;
import de.yoshi.hoelderlinplugin.listener.chatListener;
import de.yoshi.hoelderlinplugin.listener.gamemodeListener;
import de.yoshi.hoelderlinplugin.listener.serverPing;
import de.yoshi.hoelderlinplugin.utils.ItemBuilder;
import de.yoshi.hoelderlinplugin.utils.fileconfig;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Main extends JavaPlugin {

    static fileconfig settings = new fileconfig("settings.yml");
    public static final String PREFIX = settings.getString("PREFIX"); //"§7| §f§lHölderlinserver §l§7x§a "
    public static final String NOPERMISSION = "§cDu hast keine Berechtigung diesen Command auszuführen!";
    public static Main INSTANCE;

    public Main() { INSTANCE = this; }

    public static void log(String text){
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }

    @Override
    public void onEnable() {
        this.register();
        this.setFiles();
        //this.addInvisItemFrame();
        this.addSculcSensor();
        this.addBundle();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        World world = (World) Bukkit.getWorlds().get(0);
        fileconfig start = new fileconfig("start.yml");
        if(start.getBoolean("start") == false){
            world.setDifficulty(Difficulty.PEACEFUL);
            Bukkit.getServer().setDefaultGameMode(GameMode.ADVENTURE);
            world.getWorldBorder().setCenter(world.getSpawnLocation());
            world.getWorldBorder().setSize(20.0);
        } else if (start.getBoolean("start") == true){
            world.setDifficulty(Difficulty.HARD);
            Bukkit.getServer().setDefaultGameMode(GameMode.SURVIVAL);
            world.getWorldBorder().setCenter(world.getSpawnLocation());
            world.getWorldBorder().setSize(200000000);
        }

        log("Das Plugin wurde geladen.");
        log("§cDer Server muss nach dem ersten Laden des Plugins neugestartet werden!");

    }

    @Override
    public void onDisable() { log("§cDas Plugin wurde entladen."); }

    private void addInvisItemFrame(){
    ItemStack InvisItemFrame = new ItemStack(new ItemBuilder(Material.ITEM_FRAME)
            .displayname("InvisItemFrame")
            .enchant(Enchantment.PIERCING, 1)
            .flag(ItemFlag.HIDE_ATTRIBUTES)
            .amount(8)
            .build());

    NamespacedKey key = new NamespacedKey(this, "InvisItemFrame");
    ShapedRecipe recipe = new ShapedRecipe(key, InvisItemFrame);
    recipe.shape("III", "IGI", "III");
    recipe.setIngredient('G', Material.GLASS_PANE);
    recipe.setIngredient('I', Material.ITEM_FRAME);
    Bukkit.addRecipe(recipe);
    }

    private void addBundle(){
        ItemStack Bundle = new ItemStack(Material.BUNDLE);
        NamespacedKey key = new NamespacedKey(this, "Bundle");
        ShapedRecipe recipe = new ShapedRecipe(key, Bundle);
        recipe.shape("SHS", "HHH", "HHH");
        recipe.setIngredient('H', Material.RABBIT_HIDE);
        recipe.setIngredient('S', Material.STRING);
    }

    private void addSculcSensor(){
        ItemStack SculcSensor = new ItemStack(Material.SCULK_SENSOR);
        NamespacedKey key = new NamespacedKey(this, "SculcSensor");
        ShapedRecipe recipe = new ShapedRecipe(key, SculcSensor);
        recipe.shape("EOE", "OOO");
        recipe.setIngredient('E', Material.ENDER_PEARL);
        recipe.setIngredient('O', Material.OBSIDIAN);
    }

    private void register(){
        fileconfig settings = new fileconfig("settings.yml");
        //Events:
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new chatListener(), this);
        pluginManager.registerEvents(new serverPing(), this);
        pluginManager.registerEvents(new gamemodeListener(), this);

        //Commands:
        if(settings.getBoolean("lobbyCommand") == true){
            Bukkit.getPluginCommand("lobby").setExecutor(new lobbyCommand());
        }
        if(settings.getBoolean("botCommand") == true){
            Bukkit.getPluginCommand("bot").setExecutor(new botCommand());
        }
        Bukkit.getPluginCommand("start").setExecutor(new startCommand());
        Bukkit.getPluginCommand("afk").setExecutor(new afkCommand());
        //Bukkit.getPluginCommand("afk").setTabCompleter(new afkCommand());

        Bukkit.getPluginCommand("uhrzeit").setExecutor(new timeCommand());
        //Bukkit.getPluginCommand("status").setExecutor(new statusCommand()); !funktioniert noch nicht!
        //Bukkit.getPluginCommand("status").setTabCompleter(new statusCommand());
        Bukkit.getPluginCommand("cam").setExecutor(new camCommand());
        Bukkit.getPluginCommand("ping").setExecutor(new pingCommand());
    }

    public static void sendServer(Player player, String server){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try{
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        } catch (IOException e){
            e.printStackTrace();
        }
        player.sendPluginMessage(Main.INSTANCE, "BungeeCord", byteArrayOutputStream.toByteArray());
        log(player.getName() + " wurde zu " + server + " gesendet!");
    }

    private void setFiles(){
        //set settings.yml
        fileconfig settings = new fileconfig("settings.yml");
        if(!(settings.isSet("PREFIX"))){
            settings.set("PREFIX", "§7| §f§lHölderlinserver §l§7x§a ");
        }
        if(!(settings.isSet("lobbyCommand"))){
            settings.set("lobbyCommand", false);
        }
        if(!(settings.isSet("botCommand"))){
            settings.set("botCommand", false);
        }
        if(!(settings.isSet("botCommand Beschreibung"))){
            settings.set("botCommand Beschreibung", "Bots können noch nicht gelöscht werden! Der Server muss neugestartet werden um Bots zu löschen! Bots halten den Server dauerhaft online. Meistens nicht vereinbar mit den AGBs!");
        }
        if(!(settings.isSet("showPing"))){
            settings.set("showPing", false);
        }
        if(!(settings.isSet("Motd1"))){
            settings.set("Motd1", "Yoshi_der_Bauer ist der Beste!");
        }
        if(!(settings.isSet("Motd2"))){
            settings.set("Motd2", "Der hackt, der hackt, der hackt!");
        }
        if(!(settings.isSet("Motd3"))){
            settings.set("Motd3", "Ich mag Kakteen!");
        }
        if(!(settings.isSet("Motd4"))){
            settings.set("Motd4", "Ich bin schon wieder tot!");
        }
        if(!(settings.isSet("Motd5"))){
            settings.set("Motd5", "Sound.Player.Death");
        }
        if(!(settings.isSet("Motd6"))){
            settings.set("Motd6", "§eHerobrian has joined the game.");
        }
        if(!(settings.isSet("Motd7"))){
            settings.set("Motd7", "Tot, Halbes!");
        }
        settings.saveConfig();

        fileconfig start = new fileconfig("start.yml");
        if(!(start.isSet("start"))){
            start.set("start", false);
        }
        start.saveConfig();

        fileconfig afk = new fileconfig("afk.yml");
        if(!(afk.isSet("Beschreibung"))){
            afk.set("Beschreibung", "Hier werden die AFK Spieler gespeichert!");
            afk.saveConfig();
        }

        fileconfig status = new fileconfig("status.yml");
        if(!(status.isSet("Beschreibung"))){
            status.set("Beschreibung", "Hier werden die Cam-Accounts geschpeichert!");
            status.saveConfig();
        }
    }
}
