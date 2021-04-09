package com.gigadrivegroup.mksdemo.listener

import com.gigadrivegroup.kotlincommons.feature.inject
import com.gigadrivegroup.mksdemo.ext.isTrapdoor
import com.gigadrivegroup.mksdemo.manager.QueueEntry
import com.gigadrivegroup.mksdemo.manager.QueueEntryAction
import com.gigadrivegroup.mksdemo.manager.QueueManager
import org.bukkit.block.data.type.TrapDoor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

/** Listens for the [PlayerInteractEvent] to add trapdoors to the [QueueManager.queue]. */
public class PlayerInteractListener : Listener {
    private val queueManager: QueueManager by inject()

    @EventHandler
    public fun onInteract(event: PlayerInteractEvent) {
        val clickedBlock = event.clickedBlock ?: return

        if (event.action != Action.RIGHT_CLICK_BLOCK || !clickedBlock.type.isTrapdoor) {
            return
        }

        val trapdoor = clickedBlock.blockData as TrapDoor

        val action =
            if (trapdoor.isOpen) {
                QueueEntryAction.ADD
            } else {
                QueueEntryAction.REMOVE
            }

        queueManager.addToQueue(QueueEntry(clickedBlock.location, action))
    }
}
