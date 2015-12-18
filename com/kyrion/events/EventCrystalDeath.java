package com.kyrion.events;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDeathEvent;


public class EventCrystalDeath implements Listener{
	
	public ConsoleCommandSender console;
	
		@SuppressWarnings("deprecation")
		@EventHandler 
		public void onEntityDeath(EntityDeathEvent event){
			  if (event.getEntity() instanceof Player) {
			    return;
			  }else{
				  
				 
				  if(event.getEntity().getType().getTypeId() == 71){
					  String name = event.getEntity().getCustomName();
					  String[] tokens = name.split("-");
					  
					  
					  Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc "+tokens[3]+" a gagné sa bataille contre "+tokens[1]);
					  Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								"victory "+tokens[1]+" 2");
					  //Activation du build
				  }
			  }
		}
}

