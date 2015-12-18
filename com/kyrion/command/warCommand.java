package com.kyrion.command;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.kyrion.utils.StringsUtils;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;

public class warCommand implements CommandExecutor {
	int task;
	int task2;
	
	MPlayer mplayer;
	public static ArrayList<String> couldown = new ArrayList<String>();
	
	public static Hashtable<String, Integer> ActiveTaskWar = new Hashtable<String, Integer>();
	
	static ArrayList<String> bigCouldown = new ArrayList<String>();
	
	public static Hashtable<String, ArrayList<String>> CoffreOpen  = new Hashtable<String, ArrayList<String>>();
	
	ArrayList<String> attPlayers = new ArrayList<String>();
	
	static Hashtable<String, String> Baston = new Hashtable<String, String>();
	
	public static Hashtable<String, ArrayList<String>> Blocks = new Hashtable<String, ArrayList<String>>();
	
	
	public static String NO_CITY;
	public static String NO_PERM;
	public static String FACTION_NO_EXIST;
	public static String FACTION_NO_ENEMY;
	public static String ATT_MIN_DEUX;
	public static String DEF_MIN_DEUX;
	public static String ATT_MESSAGE;
	public static String DEF_MESSAGE;
	public static String FAC_ON_ASSAULT;
	public static String MIN_2H;
	public static String FIN_ASSAULT;
	
	public int minAtt;
	public int minDef;
	public int warTime;
	public static int warInterTime;
	
    public warCommand(FileConfiguration config) {
    	NO_CITY          = config.getString("text.war.NO_CITY");
    	NO_PERM          = config.getString("text.war.NO_PERM");
    	FACTION_NO_EXIST = config.getString("text.war.FACTION_NO_EXIST");
    	FACTION_NO_ENEMY = config.getString("text.war.FACTION_NO_ENEMY");
    	ATT_MIN_DEUX     = config.getString("text.war.ATT_MIN_DEUX");
    	DEF_MIN_DEUX     = config.getString("text.war.DEF_MIN_DEUX");
    	ATT_MESSAGE      = config.getString("text.war.ATT_MESSAGE");
    	DEF_MESSAGE      = config.getString("text.war.DEF_MESSAGE");
    	FAC_ON_ASSAULT   = config.getString("text.war.FAC_ON_ASSAULT");
    	MIN_2H           = config.getString("text.war.MIN_2H");
    	FIN_ASSAULT      = config.getString("text.war.FIN_ASSAULT");
    	
    	minAtt           = config.getInt("system.minAtt");
    	minDef           = config.getInt("system.minDef");
    	warTime          = config.getInt("system.warTime");
    	warInterTime     = config.getInt("system.warInterTime");
	}

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender; //Qui lance la guerre
            mplayer = MPlayer.get(p); //On récupère la faction du lanceur
            if(mplayer.hasFaction() == false){ //On vérifie que le lanceur a une faction
            	p.sendMessage(NO_CITY); 
            }else{
            	if(mplayer.getRole() == Rel.LEADER || mplayer.getRole() == Rel.OFFICER){ //On vérifie que c'est le lideur
            		
            		final Faction factionCible = FactionColl.get().getByName(args[0]); //On récupère la faction qui vas subir l'assault
            		
            		if(factionCible != null){  //On regarde si la faction attaquer existe
            			
            			if(mplayer.getFaction().getRelationWish(factionCible) == Rel.ENEMY){ //Es qu'ils sont bien enemy?
            				
            				List<MPlayer> NumberConnectedAttq = mplayer.getFaction().getMPlayersWhereOnline(true); //On récupère le nombre de joueur online chez les attaquants
            				List<MPlayer> NumberConnectedDeff = factionCible.getMPlayersWhereOnline(true);  //On récupère le nombre de joueur online chez les defensseur
            				
            				if(bigCouldown.contains(factionCible.getName()+mplayer.getFactionName())){ //Gestion de la contre attaque
            					bigCouldown.remove(factionCible.getName()+mplayer.getFactionName());
            				}
            				
            				if(!couldown.contains(factionCible.getName())){ //On regarde si la faction n'est pas deja en assault
            					
            					if(!bigCouldown.contains(mplayer.getFactionName()+factionCible.getName())){//On regarde si ca ne fait pas moins de 2h
            						
            						if(NumberConnectedAttq.size() >= minAtt){ //On vérifie qu'il y as asser d'attaquant
                    					if(NumberConnectedDeff.size() >= minDef){ //On vérifie qu'il y as asser d'attaquant
                    						
                    						CoffreOpen.put(mplayer.getFaction().getName(), new ArrayList<String>());
                    						//Blocks.put(mplayer.getFaction().getName(), new ArrayList<String>());
                    						
                    						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "f flag "+factionCible.getName()+" PVP yes");//Activation du pvp
                    						//Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "f perm "+factionCible.getName()+" build enemy yes ");//Activation du build

                    						
                    						
                            				sendAssaultMessage(mplayer.getFaction(),ATT_MESSAGE+factionCible.getName());
                            				sendAssaultMessage(factionCible, mplayer.getFactionName()+DEF_MESSAGE);
                            				
                            				couldown.add(factionCible.getName());
                            				
                            				
                            				Baston.put(factionCible.getName(), mplayer.getFaction().getName());
                            				
                            				
                            				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawnCrystal "+factionCible.getName()+" "+mplayer.getFaction().getName());
                            				
                            				task = Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("FactionWar"), 
                            						new Runnable(){

        												@Override
        												public void run() {
        													
        													if(couldown.contains(factionCible.getName())){
        														
        														ActiveTaskWar.remove(factionCible.getName());
        													
        														sendAssaultMessage(mplayer.getFaction(),FIN_ASSAULT);
        														Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
        																"victory "+factionCible.getName()+" 1");
        														Baston.remove(factionCible.getName());
        													}else{
        														
        													}
        													
        												}
                            					
                            				}, warTime);
                            				
                            				ActiveTaskWar.put(factionCible.getName(), task);
                            				
                            				
                            				                            				
                    					}else{
                    						p.sendMessage(DEF_MIN_DEUX);
                    					}
                    				}else{
                    					p.sendMessage(ATT_MIN_DEUX);
                    				}
            					}else{
            						p.sendMessage(MIN_2H);
            					}
            				}else{
            					p.sendMessage(FAC_ON_ASSAULT);
            				}
            			}else{
            				p.sendMessage(FACTION_NO_ENEMY);
            			}
            		}else{
            			p.sendMessage(FACTION_NO_EXIST);
            		}
            	}else{  
            		p.sendMessage(NO_PERM);
            	}
            }
        } else {
            // C'est la console du serveur qui a effectu�e la commande.
        }
        return true;
   }

    public static void sendAssaultMessage(Faction fac, String message){
    	for(MPlayer playerFctOnline : fac.getMPlayersWhereOnline(true)){
    		playerFctOnline.sendMessage(message);    		
    	}
    }
    
    public void allPlayerAttaquant(Faction fac){
    	for(MPlayer playerFct : fac.getMPlayers()){
    		attPlayers.add(playerFct.getName());
    	}
    }
}