package kr.kro.minestar.oregenerator.functions.ore

import kr.kro.minestar.oregenerator.Main.Companion.pl
import kr.kro.minestar.utility.number.round
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Material
import org.bukkit.entity.Player
import java.io.File
import kotlin.random.Random

object OrePercentage {
    private val map = hashMapOf(
        Pair(Material.STONE, 0),
        Pair(Material.COAL_ORE, 0),
        Pair(Material.LAPIS_ORE, 0),
        Pair(Material.COPPER_ORE, 0),
        Pair(Material.IRON_ORE, 0),
        Pair(Material.GOLD_ORE, 0),
        Pair(Material.EMERALD_ORE, 0),
        Pair(Material.DIAMOND_ORE, 0),
    )

    fun reload() {
        pl.config.load(File(pl.dataFolder, "config.yml"))
        map.clear()
        map[Material.STONE] = pl.config.getInt("stone")
        map[Material.COAL_ORE] = pl.config.getInt("coal")
        map[Material.LAPIS_ORE] = pl.config.getInt("lapis")
        map[Material.COPPER_ORE] = pl.config.getInt("copper")
        map[Material.IRON_ORE] = pl.config.getInt("iron")
        map[Material.GOLD_ORE] = pl.config.getInt("gold")
        map[Material.EMERALD_ORE] = pl.config.getInt("emerald")
        map[Material.DIAMOND_ORE] = pl.config.getInt("diamond")
    }

    fun getOre(): Material {
        var total = 0
        for (int in map.values) total += int
        val random = Random.nextInt(total)
        var count = 0
        for (m in map) {
            if (random in count until (count + m.value)) return m.key
            count += m.value
        }
        return Material.STONE
    }

    private fun total(): Int {
        var total = 0
        for (int in map.values) total += int
        return total
    }

    fun checkPercent(player: Player) {
        val oreList = listOf(
            Material.STONE,
            Material.COAL_ORE,
            Material.LAPIS_ORE,
            Material.COPPER_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.EMERALD_ORE,
            Material.DIAMOND_ORE,
        )
        val total = total()
        " ".toPlayer(player)
        "§9${javaClass.simpleName} total §f- §b$total".toPlayer(player)
        for (material in oreList) {
            val default = map[material] ?: 0
            val defaultPercent = (default / total.toDouble() * 100).round(3)
            "§6$material §f| §e$default §f| §a$defaultPercent%".toPlayer(player)
        }
    }
}