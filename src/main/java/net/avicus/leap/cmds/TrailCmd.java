package net.avicus.leap.cmds;

import net.avicus.leap.Leap;
import net.avicus.leap.api.Trail;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrailCmd implements CommandExecutor {

    private Leap plugin;

    public TrailCmd(Leap plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            String trails = "";
            for (Trail trail : Trail.getList()) {
                if (trail.isEnabled()) {
                    ChatColor color = trail.hasPermission(sender) ? ChatColor.GREEN : ChatColor.GOLD;
                    trails += color + trail.getName() + ChatColor.YELLOW + ", ";
                }
            }

            sender.sendMessage(ChatColor.AQUA + "Trails:");
            int index = 0;
            for (String trail : trails.split(", ")) {
                sender.sendMessage(ChatColor.YELLOW + "" + (index + 1) + ":" + ChatColor.RESET + " " + trail);
                index++;
            }
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command!");
            return true;
        }
        Player player = (Player) sender;

        String input = args[0].toLowerCase();
        Trail trail = Trail.getByName(input);

        if (trail == null || !trail.isEnabled()) {
            sender.sendMessage(ChatColor.RED + "There are no trails by that name!");
            return true;
        }

        if (!plugin.getTrails(player).contains(trail)) {
            sender.sendMessage(ChatColor.RED + "That trail is locked!");
            return true;
        }

        plugin.setTrail(player, trail);
        sender.sendMessage(ChatColor.YELLOW + "Your trail was set to " + ChatColor.GOLD + trail.getName() + ChatColor.YELLOW + ".");
        return true;
    }

    public String getUsage() {
        return "/trail <name>";
    }

}
