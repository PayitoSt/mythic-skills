package net.mythics.utils.inventories;

import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.mythics.utils.Items;
import net.mythics.utils.ymls.Stats;

public class Helpers {

    public static HashMap<ItemStack, Integer> expOrbs = new HashMap<ItemStack, Integer>();
    public static HashMap<Player, String> managerMessages = new HashMap<Player, String>();
    public static HashMap<Player, String> managerStatsSetup = new HashMap<Player, String>();
    public static HashMap<Player, Player> targetPlayer = new HashMap<Player, Player>();

    public static void setUpHashMap(){
        expOrbs.put(Items.expOrbSmall(), 1);
        expOrbs.put(Items.expOrbMedium(), 5);
        expOrbs.put(Items.expOrbLarge(), 10);
        expOrbs.put(Items.expOrbMega(), 20);
    }

    public static void removeOrb(ItemStack itemStack){

        int amount = itemStack.getAmount();
        if(amount == 1){
            itemStack.setAmount(0);
        }

        if(amount > 1){
            itemStack.setAmount(amount-1);
        }

    }


    public static boolean leveledUp(int previousLevel, int newLevel){
        if(previousLevel != newLevel){
            return true;
        }

        return false;
    }

    public static boolean playerIsEditing(Player player){
        if(Helpers.managerMessages.containsKey(Helpers.targetPlayer.get(player))){
            return true;
        }
        return false;
    }

    public static boolean isNumber(String input){
        try{
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }


    public static boolean hasOrbs(int orbs, int inputOrbs){

        if(orbs >= inputOrbs){
            return true;
        }
        
        return false;
        
    }

    public static double improvementLevel(int differenceBetweenLevel, double amount){
        return (Double.valueOf(differenceBetweenLevel))*amount;
    }

    public static String prestigeList(List<String> list){
        StringBuilder newString = new StringBuilder();
        newString.append("&8[");
        for(String i : list){
            switch(i){
                case "strength":
                    newString.append("&c★");
                    break;
                case "resistance":
                    newString.append("&a★");
                    break;
                case "agility":
                    newString.append("&b★");
                    break;
                case "attack_speed":
                    newString.append("&4★");
                    break;
                case "toughness":
                    newString.append("&9★");
                    break;
          }
        }
        newString.append("&8]");
        return newString.toString();
    }

    public static int generalLevelPlayer(Player player){
        FileConfiguration stats = Stats.getStats();


        int strength = stats.getInt("Players."+player.getUniqueId()+".skills"+".strength");
        int agility = stats.getInt("Players."+player.getUniqueId()+".skills"+".agility");
        int resistance = stats.getInt("Players."+player.getUniqueId()+".skills"+".resistance");
        int attackspeed = stats.getInt("Players."+player.getUniqueId()+".skills"+".attackspeed");
        int toughness = stats.getInt("Players."+player.getUniqueId()+".skills"+".toughness");

        int summation = strength+agility+resistance+attackspeed+toughness;

        return summation/100;
    }


    public static boolean playerHasClan(Player player){
        FileConfiguration stats = Stats.getStats();

        if(stats.contains("Players."+player.getUniqueId()+".skills"+".clan")){
            return true;
        }

        return false;
    }

    public static void setPlayerClan(Player player, String clanName){
        FileConfiguration stats = Stats.getStats();

        String path = "Players."+player.getUniqueId()+".skills"+".clan";

        stats.set(path, clanName);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8» &7Creaste un clan correctamente."));
    }
}
