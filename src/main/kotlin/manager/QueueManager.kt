package com.gigadrivegroup.mksdemo.manager

import com.gigadrivegroup.kotlincommons.feature.CommonsManager
import com.gigadrivegroup.kotlincommons.feature.inject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.bukkit.Location

public class QueueManager : CommonsManager() {
    private val coordinatesManager: CoordinatesManager by inject()

    /** The queue holding the queued [QueueEntry] objects. */
    public val queue: Channel<QueueEntry> = Channel()

    init {
        GlobalScope.launch {
            queue.consumeEach {
                when (it.action) {
                    QueueEntryAction.ADD -> coordinatesManager.insertCoordinates(it.location)
                    QueueEntryAction.REMOVE -> coordinatesManager.deleteCoordinates(it.location)
                }
            }
        }
    }

    override fun shutdown() {
        super.shutdown()

        queue.close()
    }

    /** Adds a specific [entry] to the [queue]. */
    public fun addToQueue(entry: QueueEntry) {
        GlobalScope.launch { queue.send(entry) }
    }
}

/**
 * An entry in the queue which will be executed on the database.
 * @param location The associated coordinates for this entry
 * @param action Whether to add to or remove this entry from the database
 */
public class QueueEntry(public val location: Location, public val action: QueueEntryAction)

/** The action to execute on a [QueueEntry]. */
public enum class QueueEntryAction {
    /** Adds the [QueueEntry] to the database. */
    ADD,

    /** Removes the [QueueEntry] from the database. */
    REMOVE
}
