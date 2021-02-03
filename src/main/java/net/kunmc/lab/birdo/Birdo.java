package net.kunmc.lab.birdo;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Birdo extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (!player.getScoreboardTags().contains("birdo"))
            return;

        new BukkitRunnable() {
            int count = event.getMessage().length();

            @Override
            public void run() {
                Location loc = player.getEyeLocation();
                loc.getWorld().spawn(loc, Egg.class, egg -> {
                    egg.setShooter(player);
                    egg.setVelocity(loc.getDirection());
                });
                loc.getWorld().playSound(loc, Sound.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1, .5f);

                if (--count <= 0)
                    cancel();
            }
        }.runTaskTimer(this, 0, 5);
    }

}
