package kr.kro.minestar.oregenerator

import kr.kro.minestar.oregenerator.functions.Event
import kr.kro.minestar.oregenerator.functions.ore.DeepOrePercentage
import kr.kro.minestar.oregenerator.functions.ore.OrePercentage
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§9OreGen§f]"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("oregen")?.setExecutor(CMD)
        Bukkit.getPluginManager().registerEvents(Event, this)
        saveResource("config.yml", false)
        OrePercentage.reload()
        DeepOrePercentage.reload()
    }

    override fun onDisable() {
    }
}