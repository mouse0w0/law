package com.github.mouse0w0.law.command;

import com.github.mouse0w0.law.Main;
import com.github.mouse0w0.law.config.Config;
import com.github.mouse0w0.law.config.Lang;
import com.github.mouse0w0.law.config.Law;
import com.github.mouse0w0.law.listener.QueryListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LawCommand implements CommandExecutor {
    public static void init() {
        Main.getPlugin().getCommand("law").setExecutor(new LawCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length != 0 ? args[0] : "help") {
            case "help":
                if (!sender.hasPermission("law.admin.help")) {
                    sender.sendMessage(Lang.getWithPrefix("command-no-permission"));
                } else {
                    sender.sendMessage(Lang.get("command-help-message"));
                }
                break;
            case "reload":
                if (!sender.hasPermission("law.admin.reload")) {
                    sender.sendMessage(Lang.getWithPrefix("command-no-permission"));
                } else {
                    Config.load();
                    Lang.load();
                    Law.load();
                    sender.sendMessage(Lang.getWithPrefix("command-reload-message"));
                }
                break;
            case "world":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Lang.getWithPrefix("command-no-player"));
                } else if (!sender.hasPermission("law.admin.world")) {
                    sender.sendMessage(Lang.getWithPrefix("command-no-permission"));
                } else {
                    sender.sendMessage(Lang.getWithPrefix("command-world-message", ((Player) sender).getWorld().getName()));
                }
                break;
            case "query":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(Lang.getWithPrefix("command-no-player"));
                } else if (!sender.hasPermission("law.admin.query")) {
                    sender.sendMessage(Lang.getWithPrefix("command-no-permission"));
                } else {
                    UUID uuid = ((Player) sender).getUniqueId();
                    if (QueryListener.add(uuid)) {
                        sender.sendMessage(Lang.getWithPrefix("command-query-enabled"));
                    } else {
                        QueryListener.remove(uuid);
                        sender.sendMessage(Lang.getWithPrefix("command-query-disabled"));
                    }
                }
                break;
            default:
                sender.sendMessage(Lang.getWithPrefix("command-usage-message"));
                break;
        }
        return true;
    }
}
