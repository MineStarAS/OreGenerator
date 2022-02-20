package kr.kro.minestar.oregenerator

import kr.kro.minestar.oregenerator.Main.Companion.prefix
import kr.kro.minestar.oregenerator.functions.ore.DeepOrePercentage
import kr.kro.minestar.oregenerator.functions.ore.OrePercentage
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

object CMD : CommandExecutor, TabCompleter {
    private enum class Arg {  ore, deep }
    private enum class ArgOP { reload }

    override fun onCommand(player: CommandSender, cmd: Command, label: String, args: Array<out String>): Boolean {
        if (player !is Player) return false
        if (args.isEmpty()) {
            return false
        }
        when (args.first()) {
            ArgOP.reload.name -> {
                if (!player.isOp) return false
                OrePercentage.reload()
                DeepOrePercentage.reload()
                "$prefix §areloaded one percent.".toPlayer(player)
            }
            Arg.ore.name -> OrePercentage.checkPercent(player)
            Arg.deep.name -> DeepOrePercentage.checkPercent(player)
        }
        return false
    }

    override fun onTabComplete(player: CommandSender, cmd: Command, alias: String, args: Array<out String>): MutableList<String> {
        val list = mutableListOf<String>()
        if (!player.isOp) return list
        if (args.size == 1) {
            for (s in Arg.values()) if (s.name.contains(args.last())) list.add(s.name)
            if(player.isOp) for (s in ArgOP.values()) if (s.name.contains(args.last())) list.add(s.name)
        }
        return list
    }
}