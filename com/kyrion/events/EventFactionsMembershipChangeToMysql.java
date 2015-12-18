package com.kyrion.events;

import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.kyrion.principal.FactionWar;
import com.massivecraft.factions.event.EventFactionsMembershipChange;
import com.massivecraft.factions.event.EventFactionsMembershipChange.MembershipChangeReason;


public class EventFactionsMembershipChangeToMysql implements Listener{
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEventFactionsMembershipChange(EventFactionsMembershipChange event){
	//	MembershipChangeReason raison = event.getReason();
	//	if(raison == MembershipChangeReason.JOIN){//join
	//		try{
	//	        Statement state = FactionWar.conn.createStatement();
	//	        state.executeUpdate("INSERT INTO player_faction (nom, factionId, uuid, grade) VALUES ('"+event.getSender().getName()+"','"+event.getNewFaction().getId()+"','000',0)");
	//	        state.close();
	//	    }catch(SQLException e){
	//	    	e.printStackTrace();
	//	    }
	//	}else if(raison == MembershipChangeReason.KICK || raison == MembershipChangeReason.LEAVE){//leave ou kick
	//		try{
	//	        Statement state = FactionWar.conn.createStatement();
	//	        state.executeUpdate("DELETE FROM player_faction WHERE factionId = '"+event.getNewFaction().getId()+"';");
	//	        state.close();
	//	    }catch(SQLException e){
	//	    	e.printStackTrace();
	//	    }
	//	}
	}
}
