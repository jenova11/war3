package com.kyrion.events;

import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.kyrion.principal.FactionWar;
import com.massivecraft.factions.event.EventFactionsCreate;

public class EventFactionCreateToMysql implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEventFactionCreateToMysql(EventFactionsCreate event)
	{
		//try{
	    //    Statement state = FactionWar.conn.createStatement();
	    //    state.executeUpdate("INSERT INTO factions (idFaction,nom, date, point) VALUES ('"+event.getFactionId()+"','"+event.getFactionName()+"',NOW(),200)");
	    //    state.executeUpdate("INSERT INTO player_faction (nom, factionId, uuid, grade) VALUES ('"+event.getSender().getName()+"','"+event.getFactionId()+"','000',0)");
	    //    state.close();
	    //}catch(SQLException e){
	    //	e.printStackTrace();
	    //}
		
	}
}
