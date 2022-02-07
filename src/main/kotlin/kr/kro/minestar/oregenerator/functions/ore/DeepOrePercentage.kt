package kr.kro.minestar.oregenerator.functions.ore

import kr.kro.minestar.oregenerator.Main.Companion.pl
import kr.kro.minestar.utility.number.round
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Material
import org.bukkit.entity.Player
import java.io.File
import kotlin.random.Random

object DeepOrePercentage {
    private val map = hashMapOf(
        Pair(Material.DEEPSLATE, 0),
        Pair(Material.DEEPSLATE_COAL_ORE, 0),
        Pair(Material.DEEPSLATE_LAPIS_ORE, 0),
        Pair(Material.DEEPSLATE_COPPER_ORE, 0),
        Pair(Material.DEEPSLATE_IRON_ORE, 0),
        Pair(Material.DEEPSLATE_GOLD_ORE, 0),
        Pair(Material.DEEPSLATE_EMERALD_ORE, 0),
        Pair(Material.DEEPSLATE_DIAMOND_ORE, 0),
    )

    fun reload() {
        pl.config.load(File(pl.dataFolder, "config.yml"))
        map.clear()
        map[Material.DEEPSLATE] = pl.config.getInt("deep_stone")
        map[Material.DEEPSLATE_COAL_ORE] = pl.config.getInt("deep_coal")
        map[Material.DEEPSLATE_LAPIS_ORE] = pl.config.getInt("deep_lapis")
        map[Material.DEEPSLATE_COPPER_ORE] = pl.config.getInt("deep_copper")
        map[Material.DEEPSLATE_IRON_ORE] = pl.config.getInt("deep_iron")
        map[Material.DEEPSLATE_GOLD_ORE] = pl.config.getInt("deep_gold")
        map[Material.DEEPSLATE_EMERALD_ORE] = pl.config.getInt("deep_emerald")
        map[Material.DEEPSLATE_DIAMOND_ORE] = pl.config.getInt("deep_diamond")
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

    fun total(): Int {
        var total = 0
        for (int in map.values) total += int
        return total
    }

    fun checkPercent(player: Player) {
        val countMap = hashMapOf(
            Pair(Material.DEEPSLATE, 0),
            Pair(Material.DEEPSLATE_COAL_ORE, 0),
            Pair(Material.DEEPSLATE_LAPIS_ORE, 0),
            Pair(Material.DEEPSLATE_COPPER_ORE, 0),
            Pair(Material.DEEPSLATE_IRON_ORE, 0),
            Pair(Material.DEEPSLATE_GOLD_ORE, 0),
            Pair(Material.DEEPSLATE_EMERALD_ORE, 0),
            Pair(Material.DEEPSLATE_DIAMOND_ORE, 0),
        )
        val count = Random.nextInt(1000) * 1000
        for (int in 1..count) {
            val material = getOre()
            countMap[material] = countMap[material]!! + 1
        }
        val total = total()
        " ".toPlayer(player)
        "§c${javaClass.simpleName} total - $total".toPlayer(player)
        "§9Check count - §b$count".toPlayer(player)
        for (m in countMap) {
            val material = m.key
            val percent = (m.value / count.toDouble() * 100).round(3)
            val default = map[m.key] ?: 0
            val defaultPercent = (default / total.toDouble() * 100).round(3)
            "§6$material §f| §e${m.value} §f| §a$percent% §f| §b$default §f| §d$defaultPercent%".toPlayer(player)
        }
    }
}