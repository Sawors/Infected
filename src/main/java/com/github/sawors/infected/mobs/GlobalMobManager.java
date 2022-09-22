package com.github.sawors.infected.mobs;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

public class GlobalMobManager implements Listener {
    
    @EventHandler
    public static void preventDefaultMobSpawn(CreatureSpawnEvent event){
        if(event.getEntity() instanceof Zombie zomb){
            zomb.clearLootTable();
            zomb.setArmsRaised(false);
            zomb.setCanBreakDoors(true);
            zomb.setShouldBurnInDay(false);
            zomb.setCanPickupItems(false);
        }
        if(event.getEntity() instanceof Husk husk){
            husk.setShouldBurnInDay(false);
            husk.clearLootTable();
            husk.setArmsRaised(false);
            husk.setCanBreakDoors(true);
            husk.setCanPickupItems(false);
        }
    
    
        // cancelled entities
        List<EntityType> blockedentity = List.of(
                EntityType.ZOMBIE_VILLAGER,
                EntityType.SKELETON,
                EntityType.CREEPER,
                EntityType.SPIDER,
                EntityType.CAVE_SPIDER,
                EntityType.STRAY
        );
        if(blockedentity.contains(event.getEntity().getType())){
            event.setCancelled(true);
        }
    }
}
