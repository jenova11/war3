package com.kyrion.principal;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.kyrion.command.setCrystalCommand;
import com.kyrion.command.spawnCrystalCommand;
import com.kyrion.command.stopWarCommand;
import com.kyrion.command.victoryCommand;
import com.kyrion.command.warCommand;
import com.kyrion.events.EventBreakBlock;
import com.kyrion.events.EventChestWar;
import com.kyrion.events.EventCrystalDeath;
import com.kyrion.events.EventFactionCreateToMysql;
import com.kyrion.events.EventFactionDeleteToMysql;
import com.kyrion.events.EventFactionsMembershipChangeToMysql;
import com.massivecraft.factions.engine.EngineMain;

import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactionWar extends JavaPlugin {
	
	private String url = "jdbc:mysql://localhost:3306/factions";
    private String user = "factions";
    private String passwd = "lolita1122";
    public static Connection conn;
    
    public static Economy economy = null;
    
    @Override
    public void onEnable(){
    	
    	connect();
    	saveDefaultConfig();
    	setupEconomy();
    	
    	 File crystalConfig = new File("plugins/FactionWar/configB.yml");//Fichier de l'autre configuration
    	    if(!crystalConfig.exists()) {
    	        try {
    	        	crystalConfig.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//On crée ce fichier s'il n'existe pas
    	    }
    	 
    	    FileConfiguration spawnCrystal = YamlConfiguration.loadConfiguration(crystalConfig);//On charge l'autre configuration
    	    
    	//Enregistrement des commandes
    	getLogger().info("Plugin FactionWar démarré !");
    	
    	PluginManager pm = getServer().getPluginManager();
    	
    	CommandExecutor war = new warCommand(getConfig());
    	getCommand("war").setExecutor(war);
    	
    	CommandExecutor stopWarCommand = new stopWarCommand();
    	getCommand("capitule").setExecutor(stopWarCommand);
    	
    	CommandExecutor spawnCrystalCommand = new spawnCrystalCommand(spawnCrystal, crystalConfig);
    	getCommand("spawnCrystal").setExecutor(spawnCrystalCommand);
    	
    	CommandExecutor setCrystalCommand = new setCrystalCommand(spawnCrystal, crystalConfig);
    	getCommand("setCrystal").setExecutor(setCrystalCommand);
    	
    	CommandExecutor setVictoryCommand = new victoryCommand();
    	getCommand("victory").setExecutor(setVictoryCommand);
    	
    	//Enregistrement des events
    	Listener EventFactionCreate = new EventFactionCreateToMysql();
    	Listener EventFactionsDisband = new EventFactionDeleteToMysql();
    	Listener EventFactionsMembershipChange = new EventFactionsMembershipChangeToMysql();
    	Listener EventChestWar = new EventChestWar();
    	Listener EventCrystalDeath = new EventCrystalDeath();
    	Listener EventBreakBlock = new EventBreakBlock();
    	
    	pm.registerEvents(EventFactionCreate, this);
    	pm.registerEvents(EventFactionsDisband, this);
    	pm.registerEvents(EventFactionsMembershipChange, this);
    	pm.registerEvents(EventChestWar, this);
    	pm.registerEvents(EventCrystalDeath, this);
    	pm.registerEvents(EventBreakBlock, this);
    	
    	BlockBreakEvent.getHandlerList().unregister(EngineMain.get());
    }
    
    @Override
    public void onDisable(){
        // Actions � effectuer � la d�sactivation du plugin
        //   - A l'extinction du serveur
        //   - Pendant un /reload
    }
    
    public void connect(){
   	 
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connexion établie!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
    }
    
    private boolean setupEconomy()
    
    {
 
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
 
        if (economyProvider != null) {
 
            economy = economyProvider.getProvider();
 
        }
 
        return (economy != null);
 
    }
    
}
