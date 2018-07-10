package nl.marido.levitate;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import nl.marido.levitate.handlers.Updater;
import nl.marido.levitate.listeners.Activate;

public class Levitate extends JavaPlugin {

public static Levitate instance;	

public void onEnable() {
instance = this;
System.out.println("Thank you for using the Levitate resource.");
saveDefaultConfig();
Updater.runChecks();
registerListeners();
}

public void registerListeners() {
PluginManager manager = getServer().getPluginManager();
manager.registerEvents(new Activate(), this);
}

public void onDisable() {
System.out.println("Thank you for using the Levitate resource.");
}

public static Levitate getInstance() {
return instance;
}

}