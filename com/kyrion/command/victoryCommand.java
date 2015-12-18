package com.kyrion.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.kyrion.principal.FactionWar;
import com.kyrion.utils.StringsUtils;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;

public class victoryCommand implements CommandExecutor {
	public String WARN_PLAYER_SEND = "Un joueur ne peux pas lancer cette commande";
	public String NO_WAR = "il n'y as pas de Guerre en cour";
	
	Faction factionCible;
	Faction factionAtt;
	
	String FactionWin;
	String FactionLoose;
	ArrayList<String> attPlayers = new ArrayList<String>();
	
	
	int pointDef = 0;
	int pointAtt = 0;
	
	int QuiAWin;
	//args[0] Défenceur
	//args[1] 1 = win def 2 = win att
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if(!(sender instanceof Player)) {
            if(warCommand.couldown.contains(args[0])){
            	
            	QuiAWin = Integer.parseInt(args[1]);
            	
            	factionCible =  FactionColl.get().getByName(args[0]);
            	factionAtt = FactionColl.get().getByName(warCommand.Baston.get(args[0]));
            	
            	if(QuiAWin == 1){//La def gagne
            		
            		warCommand.couldown.remove(factionCible.getName());//On retire la faction cible du couldown
            		
            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "f flag "+factionCible.getName()+" PVP no");//On retire le pvp
            		
            		sendAssaultMessage(factionCible,warCommand.FIN_ASSAULT);//on avertie les defensseur
            		sendAssaultMessage(factionAtt,warCommand.FIN_ASSAULT);//On averti les attaquants
            		
            		allPlayerAttaquant(factionAtt);//On recupère les attaquants
    					String res = StringsUtils.convertToString(attPlayers);//on les convertis en string
    					 //r:100 t:30min p:ThisMisterious,Seronjo,Aboubakkar,coxo
    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
    							"cw -w kyrion -o "+factionCible.getHome().getLocationX().intValue()+" "+factionCible.getHome().getLocationY().intValue()+" "+factionCible.getHome().getLocationZ().intValue()+" -u DeusMakina pr rb r:300  a:break p:"+res+" t:30min");
    					System.out.println("cw -w kyrion -o "+factionCible.getHome().getLocationX().intValue()+" "+factionCible.getHome().getLocationY().intValue()+" "+factionCible.getHome().getLocationZ().intValue()+" -u DeusMakina pr rb r:300 a:block-break p:"+res+" t:30min");
    					//On rolback
                	
					warCommand.bigCouldown.add(factionAtt.getName()+factionCible.getName());//On les ajoute au bug couldown
					AddToBigCouldown(factionAtt.getName(), factionCible.getName());
					warCommand.CoffreOpen.remove(factionAtt.getName());
					
					Entity entity = spawnCrystalCommand.instance.crystals.remove("Faction-"+factionCible.getName() + "-vs-" + factionAtt.getName());
                    if (entity != null && !entity.isDead())
                        entity.remove();
                    attPlayers.clear();
                   
                    
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc "+factionCible.getName()+" a défendu ca ville contre "+factionAtt.getName());
					  
                    

                	
            		
                        
            	}else{//l'attaque gagne
            		
            		warCommand.ActiveTaskWar.remove(args[0]);//On annule la victoire des defensseur
            		warCommand.couldown.remove(factionCible.getName());//On retire la faction cible du couldown
            		
            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "f flag "+factionCible.getName()+" PVP no");//On retire le pvp
            		
            		
            		sendAssaultMessage(factionCible,warCommand.FIN_ASSAULT);//on avertie les defensseur
            		sendAssaultMessage(factionAtt,warCommand.FIN_ASSAULT);//On averti les attaquants
              		allPlayerAttaquant(factionAtt);//On recupère les attaquants
              		
    					String res = StringsUtils.convertToString(attPlayers);//on les convertis en string
    					
                		
    					Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
    							"cw -w kyrion -o "+factionCible.getHome().getLocationX().intValue()+" "+factionCible.getHome().getLocationY().intValue()+" "+factionCible.getHome().getLocationZ().intValue()+" -u DeusMakina pr rb r:300  a:break p:"+res+" t:30min");
    					System.out.println("cw -w kyrion -o "+factionCible.getHome().getLocationX().intValue()+" "+factionCible.getHome().getLocationY().intValue()+" "+factionCible.getHome().getLocationZ().intValue()+" -u DeusMakina pr rb r:300 a:block-break p:"+res+" t:30min");
    					//On rolback
    					
    					warCommand.bigCouldown.add(factionAtt.getName()+factionCible.getName());//On les ajoute au bug couldown
    					AddToBigCouldown(factionAtt.getName(), factionCible.getName());
    					warCommand.CoffreOpen.remove(factionAtt.getName());
    					attPlayers.clear();
    				
            	}
            	
            	
            	 
                 
        		try{
        	        Statement state = FactionWar.conn.createStatement();
        	        
        	        String sql = "SELECT points FROM factions WHERE idfaction ='"+factionCible.getId()+"'";
                    ResultSet rs = state.executeQuery(sql);
                   
                    
                    while(rs.next()){
                        //Retrieve by column name
                        pointDef  = rs.getInt("points");
                    }
                    String sql2 = "SELECT points FROM factions WHERE idfaction ='"+factionAtt.getId()+"'";
                    ResultSet rs2 = state.executeQuery(sql2);
                    while(rs2.next()){
                        //Retrieve by column name
                        pointAtt  = rs2.getInt("points");
                    }
                    if(QuiAWin == 1){//def
                    	pointDef = pointDef + ((pointAtt*20)/100);
                    	pointAtt = pointAtt - ((pointAtt*20)/100);
                    }else{//att
                    	pointDef = pointDef - ((pointDef*20)/100);
                    	pointAtt = pointAtt + ((pointDef*20)/100);
                    }
        	        state.executeUpdate("UPDATE factions SET points = "+pointAtt+" WHERE idfaction ='"+factionAtt.getId()+"'");//ATT
        	        state.executeUpdate("UPDATE factions SET points = "+pointDef+" WHERE idfaction ='"+factionCible.getId()+"'");//DEF
        	        state.close();
        	    }catch(SQLException e){
        	    	e.printStackTrace();
        	    }

            	
            }else{
            	
            }
    	}else{
    		 Player p = (Player)sender;// On r�cup�re le joueur.
    		 p.sendMessage(WARN_PLAYER_SEND);
    	}
    	
	return true;
   }
    
    
    public void allPlayerAttaquant(Faction fac){
    	for(MPlayer playerFct : fac.getMPlayers()){
    		attPlayers.add(playerFct.getName());
    	}
    }
    public void sendAssaultMessage(Faction fac, String message){
    	for(MPlayer playerFctOnline : fac.getMPlayersWhereOnline(true)){
    		playerFctOnline.sendMessage(message);    		
    	}
    }
    
    public void AddToBigCouldown(final String att, final String def){
    	Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("FactionWar"), 
				new Runnable(){
       
					@Override
					public void run() {
						warCommand.bigCouldown.remove(att+def);
						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "f "+factionCible+" flag PVP yes");//Activation du pvp
					}
			
		}, warCommand.warInterTime);
    }
}
