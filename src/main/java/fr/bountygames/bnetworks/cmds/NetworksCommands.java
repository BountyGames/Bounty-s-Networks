package fr.bountygames.bnetworks.cmds;

import fr.bountygames.bnetworks.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class NetworksCommands implements CommandExecutor {

    private Main plugin;

    public NetworksCommands(Main main) {
        this.plugin = main;
        if(plugin.getConfig().getBoolean("twitch.enabled")) {
            Objects.requireNonNull(plugin.getCommand("twitch")).setExecutor(this);
        }
        if(plugin.getConfig().getBoolean("discord.enabled")) {
            Objects.requireNonNull(plugin.getCommand("discord")).setExecutor(this);
        }
        if(plugin.getConfig().getBoolean("youtube.enabled")) {
            Objects.requireNonNull(plugin.getCommand("youtube")).setExecutor(this);
        }
        if(plugin.getConfig().getBoolean("twitter.enabled")) {
            Objects.requireNonNull(plugin.getCommand("twitter")).setExecutor(this);
        }
        if(plugin.getConfig().getBoolean("reddit.enabled")) {
            Objects.requireNonNull(plugin.getCommand("reddit")).setExecutor(this);
        }
    }

    private void errorMessage(CommandSender sender, String network) {
        sender.sendMessage("§7[§bBounty's §aNetworks§7] " +
                Objects.requireNonNull(plugin.getLangConfig().getString("error"))
                        .replace("&", "§"));
        plugin.getLogger().warning("§cError while sending " + network + " message to " + sender.getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("twitch") ||
                command.getName().equalsIgnoreCase("discord") ||
                command.getName().equalsIgnoreCase("youtube") ||
                command.getName().equalsIgnoreCase("twitter") ||
                command.getName().equalsIgnoreCase("reddit")) {
            try {
                sender.sendMessage(Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig()
                        .getString( command.getName() + ".message"))
                        .replace("&", "§")
                        .replace("%player%", sender.getName())));
            } catch (Exception e) {
                errorMessage(sender, command.getName());
            }
        }

        return false;
    }
}
