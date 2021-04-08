package com.gigadrivegroup.mksdemo

/** An object with basic logging functions. */
public object Logger {
    /** Logs an INFO-level [message] to the console. */
    public fun info(message: String): Unit = println("[mks-demo] $message")

    /** Logs an ERROR-level [message] to the console. */
    public fun error(message: String): Unit = System.err.println("[mks-demo] $message")
}
