package de.dasphiller.extensions.message

import de.dasphiller.extensions.extensions.mm
import net.kyori.adventure.text.Component

object MiniMessageHelper {
    fun message(message: String): Component {
        return mm.deserialize(message)
    }
}