package me.xian7720.customCommandPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class command implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            p.sendMessage("성공이다!!!!!?");
            return true;
        } else {
            commandSender.sendMessage("넌 플레이어가 아니다!!");
        }
        return false;
    }
}
