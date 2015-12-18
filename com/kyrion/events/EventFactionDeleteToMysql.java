package com.kyrion.events;

import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.kyrion.principal.FactionWar;
import com.massivecraft.factions.event.EventFactionsDisband;

public class EventFactionDeleteToMysql implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEventFactionDeleteToMysql(EventFactionsDisband event)
	{
		//try{
	    //    Statement state = FactionWar.conn.createStatement();
	    //    state.executeUpdate("DELETE FROM factions WHERE nom = '"+event.getFaction().getName()+"';");
	    //    state.executeUpdate("DELETE FROM player_faction WHERE factionId = '"+event.getFactionId()+"';");
	    //    state.close();
	    //}catch(SQLException e){
	    //	e.printStackTrace();
	    //}
		
	}
}
