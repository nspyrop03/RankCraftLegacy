package me.amc.rankcraft;

import org.bukkit.entity.Player;

import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.utils.RCUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class StatsPlaceholdersExpansion extends PlaceholderExpansion {

    private MainCore plugin;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin
     *        The instance of our plugin.
     */
    public StatsPlaceholdersExpansion(MainCore plugin){
        this.plugin = plugin;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist(){
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister(){
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     * 
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest 
     * method to obtain a value if a placeholder starts with our 
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier(){
        return "rankcraft";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     *
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier 
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param  player
     *         A {@link org.bukkit.Player Player}.
     * @param  identifier
     *         A String containing the identifier/value.
     *
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player p, String text){

        if(p == null){
            return "";
        }
        /*
        // %someplugin_placeholder1%
        if(identifier.equals("placeholder1")){
            return plugin.getConfig().getString("placeholder1", "value doesnt exist");
        }

        // %someplugin_placeholder2%
        if(identifier.equals("placeholder2")){
            return plugin.getConfig().getString("placeholder2", "value doesnt exist");
        }
        */
        Stats s = MainCore.instance.rankCraft.stats;

		// %rankcraft_player_level%
		if (text.equals("player_level")) {
			return "" + s.getLevel(p);
		}

		if (text.equals("player_xp")) {
			return "" + s.getXp(p);
		}

		if (text.equals("player_rank")) {
			return s.getRank(p);
		}

		if (text.equals("player_blocksbroken")) {
			return "" + s.getBlocksBroken(p);
		}

		if (text.equals("player_blocksplaced")) {
			return "" + s.getBlocksPlaced(p);
		}

		if (text.equals("player_mana")) {
			return "" + s.getMana(p);
		}

		if (text.equals("player_max_mana")) {
			return "" + s.getMaxMana(p);
		}

		if (text.equals("player_xptolevelup")) {
			return "" + s.getXPToLevelUP(p);
		}

		if (text.equals("player_gold")) {
			return "" + MainCore.instance.rankCraft.gold.getGold(p);
		}

		if (text.equals("player_hp")) {
			if(MainCore.instance.config.enableHPSystem) {
				return "" + RCUtils.round(MainCore.instance.rankCraft.hpSystem.getHP(p), 1);
			} else {
				return "§4HPSystem disabled!";
			}
		}

		if (text.equals("player_max_hp")) {
			if(MainCore.instance.config.enableHPSystem) {
				return "" + RCUtils.round(MainCore.instance.rankCraft.hpSystem.getMaxHP(p), 1);
			} else {
				return "§4HPSystem disabled!";
			}
		}

		if (text.equals("player_hpbar")) {
			if(MainCore.instance.config.enableHPSystem) {
				return MainCore.instance.rankCraft.hpSystem.getHealthBar(p, 20);
			} else {
				return "§4HPSystem disabled!";
			}
		}

		if (text.equals("player_class")) {
			if(RpgClassData.getCurrentClass(p) == null || RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)) == null) {
				return "null";
			} else {
				if (RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)).getName() != null)
					return RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)).getName();
				else
					return "null";
			}

		}

		if (text.equals("player_class_level")) {
			if (RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)) != null)
				return "" + RpgClassData.getLevel(RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)), p);
			else
				return "null";
		}

		if (text.equals("player_class_xp")) {
			if (RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)) != null)
				return "" + RpgClassData.getXp(RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)), p);
			else
				return "null";
		}
		
		if(text.equals("player_manabar")) {
			return MainCore.instance.rankCraft.stats.getManaBar(p, 20);
		}
        
		if(text.equals("player_xpbar")) {
        	return MainCore.instance.rankCraft.stats.getXpBar(p, 20);
        }
        
        if(text.equals("player_hungerbar")) {
        	return MainCore.instance.rankCraft.stats.getHungerBar(p, 20);
        }
 
        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%) 
        // was provided
        return "null";
    }

}
