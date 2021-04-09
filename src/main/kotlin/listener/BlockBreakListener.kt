package com.gigadrivegroup.mksdemo.listener

import com.gigadrivegroup.kotlincommons.feature.inject
import com.gigadrivegroup.mksdemo.manager.QueueEntry
import com.gigadrivegroup.mksdemo.manager.QueueEntryAction
import com.gigadrivegroup.mksdemo.manager.QueueManager
import org.bukkit.block.data.type.TrapDoor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/** Listens for the [BlockBreakEvent] to remove trapdoors from the database. */
public class BlockBreakListener : Listener {
    private val queueManager: QueueManager by inject()

    @EventHandler
    public fun onBreak(event: BlockBreakEvent) {
        val trapdoor = event.block.blockData as TrapDoor
        if (!trapdoor.isOpen) {
            return
        }

        queueManager.addToQueue(QueueEntry(event.block.location, QueueEntryAction.REMOVE))
    }
}
