package com.github.sawors.infected;

import com.github.sawors.infected.items.InfctdItem;
import com.github.sawors.infected.mobs.GlobalMobManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

public final class Infected extends JavaPlugin {
    
    private static JavaPlugin instance;
    private static boolean qualityarmoryenabled = false;
    private static HashMap<String, InfctdItem> itemmap = new HashMap<>();
    private static HashSet<Integer> registeredlisteners = new HashSet<>();
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        
        saveDefaultConfig();
        
        qualityarmoryenabled = getServer().getPluginManager().isPluginEnabled("QualityArmory");
        
        getServer().getPluginManager().registerEvents(new GlobalMobManager(), this);
    }
    
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    
    
    public static JavaPlugin getPlugin(){
        return instance;
    }
    
    public static boolean isQualityArmoryEnabled(){
        return qualityarmoryenabled;
    }
    
    public static void logAdmin(TextComponent msg){
        Bukkit.getLogger().log(Level.INFO, "["+getPlugin().getName()+"] "+msg.content().replaceAll("§e", ""));
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p.isOp()){
                p.sendMessage(ChatColor.YELLOW+"[DEBUG] "+getTimeText()+" : "+msg.content().replaceAll("§e", ""));
            }
        }
    }
    
    public static void logAdmin(String msg){
        logAdmin(Component.text(ChatColor.YELLOW+msg));
    }
    public static void logAdmin(Object msg){
        logAdmin(Component.text(ChatColor.YELLOW+msg.toString()));
    }
    
    static String getTimeText(){
        LocalDateTime time = LocalDateTime.now();
        return "["+time.getDayOfMonth()+"."+time.getMonthValue()+"."+time.getYear()+" "+time.getHour()+":"+time.getMinute()+":"+time.getSecond()+"]";
    }
    
    
    private void registerItem(InfctdItem item){
        itemmap.put(item.getId(), item);
        
        
        if(item instanceof Listener listener){
            for(Method method : listener.getClass().getMethods()){
                if(!registeredlisteners.contains(method.hashCode()) && method.getAnnotation(EventHandler.class) != null && method.getParameters().length >= 1 && Event.class.isAssignableFrom(method.getParameters()[0].getType())){
                    // method is recognized as handling an event
                    /*
                    plugin -> parameter
                    listener -> parameter
                    for (Map.Entry<Class<? extends Event>, Set<RegisteredListener>> entry : plugin.getPluginLoader().createRegisteredListeners(listener, plugin).entrySet()) {
                        getEventListeners(getRegistrationClass(entry.getKey())).registerAll(entry.getValue());
                    }
                    */
                    logAdmin("Listener found : "+method.getName());
                    Class<? extends Event> itemclass = method.getParameters()[0].getType().asSubclass(Event.class);
                    
                    getServer().getPluginManager().registerEvent(itemclass, listener, method.getAnnotation(EventHandler.class).priority(), EventExecutor.create(method, itemclass),getPlugin());
                    registeredlisteners.add(method.hashCode());
                }
            }
        }
    }
    
    public static InfctdItem getRegisteredItem(String id){
        return itemmap.get(id);
    }
    
    public static Inventory getItemListDisplay(){
        Inventory itemsview = Bukkit.createInventory(null, 6*9, Component.text("Item List"));
        for(InfctdItem item : itemmap.values()){
            itemsview.addItem(item.get());
        }
        
        return itemsview;
    }
}
