package com.gigadrivegroup.mksdemo

import com.gigadrivegroup.kotlincommons.feature.CommonsManager
import com.gigadrivegroup.kotlincommons.feature.bind
import com.gigadrivegroup.kotlincommons.feature.initDependencyInjection
import com.gigadrivegroup.kotlincommons.manager.DatabaseManager
import kr.entree.spigradle.annotations.SpigotPlugin
import org.bukkit.plugin.java.JavaPlugin

@SpigotPlugin
public class MKSDemo : JavaPlugin() {
    public var listenerEnabled: Boolean = true

    override fun onEnable() {
        // init dependency injection
        initDependencyInjection()
        bind(this)

        // save default config
        saveDefaultConfig()

        // load enabled value
        listenerEnabled = config.getBoolean("enabled")

        Logger.info("Plugin marked as enabled: $listenerEnabled")
        if (!listenerEnabled) {
            return
        }

        // load database manager
        try {
            val databaseManager =
                DatabaseManager(
                    config.getString("mysql.host") ?: "localhost",
                    config.getInt("mysql.port"),
                    config.getString("mysql.name") ?: "database",
                    config.getString("mysql.user") ?: "root",
                    config.getString("mysql.password") ?: "password")

            bind(databaseManager)

            val createTable =
                databaseManager.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS `coordinates` (world VARCHAR(32) NOT NULL, x INT NOT NULL, y INT NOT NULL, z INT NOT NULL, CONSTRAINT coordinates_pk PRIMARY KEY (world, x, y, z));")
            createTable.execute()
            createTable.close()
        } catch (e: Exception) {
            Logger.error("An error occurred while connecting to the database.")
            Logger.error("${e.javaClass.simpleName}: ${e.message}")
            Logger.error("Please make sure all values are correct and then restart the server.")

            server.pluginManager.disablePlugin(this)
            return
        }
    }

    override fun onDisable() {
        CommonsManager.shutdown()
    }
}
