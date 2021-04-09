package com.gigadrivegroup.mksdemo.ext

import org.bukkit.Material

/**
 * Checks whether this [Material] is a trapdoor. Uses name string comparison to support future
 * versions.
 */
public val Material.isTrapdoor: Boolean
    get() = this.name == "TRAP_DOOR" || this.name.endsWith("_TRAPDOOR")
