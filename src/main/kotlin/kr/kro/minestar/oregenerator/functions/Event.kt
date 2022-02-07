package kr.kro.minestar.oregenerator.functions

import kr.kro.minestar.oregenerator.functions.ore.DeepOrePercentage
import kr.kro.minestar.oregenerator.functions.ore.OrePercentage
import kr.kro.minestar.utility.location.Axis
import kr.kro.minestar.utility.location.addAxis
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent

object Event : Listener {

    private val fence = listOf(
        Material.OAK_FENCE,
        Material.BIRCH_FENCE,
        Material.SPRUCE_FENCE,
        Material.JUNGLE_FENCE,
        Material.ACACIA_FENCE,
        Material.DARK_OAK_FENCE,
        Material.CRIMSON_FENCE,
        Material.WARPED_FENCE,
        Material.NETHER_BRICK_FENCE,
    )
    private val deepFence = listOf(
        Material.CRIMSON_FENCE,
        Material.WARPED_FENCE,
        Material.NETHER_BRICK_FENCE,
    )

    @EventHandler
    fun spawnOre(e: BlockFromToEvent) {
        if (e.block.type != Material.WATER) return
        if (e.face != BlockFace.DOWN) return
        val bottomBlock = e.toBlock.location.addAxis(Axis.Y, -1).block.type
        if (!fence.contains(bottomBlock)) return
        e.isCancelled = true
        if (deepFence.contains(bottomBlock)) e.toBlock.type = DeepOrePercentage.getOre()
        else e.toBlock.type = OrePercentage.getOre()
    }
}