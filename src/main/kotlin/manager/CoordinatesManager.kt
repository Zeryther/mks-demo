package com.gigadrivegroup.mksdemo.manager

import com.gigadrivegroup.kotlincommons.feature.CommonsManager
import com.gigadrivegroup.kotlincommons.feature.inject
import com.gigadrivegroup.kotlincommons.manager.DatabaseManager
import com.gigadrivegroup.mksdemo.Logger
import org.bukkit.Location

/** Manages inserting coordinates into and deleting coordinates from the [DatabaseManager]. */
public class CoordinatesManager : CommonsManager() {
    private val databaseManager: DatabaseManager by inject()

    /** Saves the specified [location] to the [DatabaseManager]. */
    public fun insertCoordinates(location: Location) {
        try {
            val ps =
                databaseManager.prepareStatement(
                    "INSERT INTO `coordinates` (`world`,`x`,`y`,`z`) VALUES(?,?,?,?)")
            ps.setString(1, location.world?.name ?: "world")
            ps.setInt(2, location.blockX)
            ps.setInt(3, location.blockY)
            ps.setInt(4, location.blockZ)
            ps.execute()
            databaseManager.closeResources(ps)
        } catch (e: Exception) {
            Logger.error("Failed to log coordinates at $location")
            Logger.error("${e.javaClass.simpleName}: ${e.message}")
        }
    }

    /** Deletes the specified [location] from the [DatabaseManager]. */
    public fun deleteCoordinates(location: Location) {
        try {
            val ps =
                databaseManager.prepareStatement(
                    "DELETE FROM `coordinates` WHERE `world` = ? AND `x` = ? AND `y` = ? AND `z` = ?")
            ps.setString(1, location.world?.name ?: "world")
            ps.setInt(2, location.blockX)
            ps.setInt(3, location.blockY)
            ps.setInt(4, location.blockZ)
            ps.execute()
            databaseManager.closeResources(ps)
        } catch (e: Exception) {
            Logger.error("Failed to log coordinates at $location")
            Logger.error("${e.javaClass.simpleName}: ${e.message}")
        }
    }
}
