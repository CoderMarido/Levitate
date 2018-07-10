package nl.marido.levitate.listeners;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import nl.marido.levitate.Levitate;

public class Activate implements Listener {
	
public static ArrayList<UUID> entries = new ArrayList<UUID>();

@EventHandler
public void activateListener(PlayerInteractEvent event) {
Action action = event.getAction();
if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
Player player = event.getPlayer();
Material hand = player.getItemInHand().getType();
Material item = Material.BLAZE_ROD;
if (hand == item) {
UUID uuid = player.getUniqueId();
if (entries.contains(uuid)) {
player.sendMessage("§eYou have §cdisabled §elevitating successfully.");
entries.remove(uuid);
} else {
player.sendMessage("§eYou have §aenabled §elevitating successfully.");
entries.add(uuid);
new BukkitRunnable() {
public void run() {
Material newhand = player.getItemInHand().getType();
if (newhand == item || !entries.contains(uuid)) {
Location pull = player.getLocation().add(player.getLocation().getDirection().normalize().multiply(10));
for (Entity near : pull.getWorld().getNearbyEntities(pull, 7, 7, 7)) {
if (near != player) {
Location location = near.getLocation();
double x = location.getX() - pull.getX();
double y = location.getY() - pull.getY();
double z = location.getZ() - pull.getZ();
Vector velocity = new Vector(x, y, z).normalize().multiply(- 0.7);
near.setVelocity(velocity);  
}
}
} else {
entries.remove(uuid);
cancel();
}
}
}.runTaskTimer(Levitate.getInstance(), 0, 0);
}
}
}
}

@EventHandler
public void leaveListener(PlayerQuitEvent event) {
UUID uuid = event.getPlayer().getUniqueId();
if (entries.contains(uuid)) {
entries.remove(uuid);
}
}
	
}