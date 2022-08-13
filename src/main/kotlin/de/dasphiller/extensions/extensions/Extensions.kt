package de.dasphiller.extensions.extensions

import net.axay.kspigot.extensions.onlinePlayers
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.nio.file.Files
import kotlin.io.path.div
import org.bukkit.Material
import org.bukkit.Material.AIR
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.entity.EntityType

fun location(world: String, x: Int, y: Int, z: Int): Location {
    if (!Bukkit.getWorlds().contains(Bukkit.getWorld(world))) throw NullPointerException("World $world is null!")
    return Location(Bukkit.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble())
}
fun dropItem(location: Location, item: Material) {
    if (item == AIR) throw NullPointerException("Air can't drop")
    location.block.world.dropItem(location, ItemStack(item))
}

fun deleteWorld(world: String) {
    val worldPath = Bukkit.getWorldContainer().toPath() / world
    try {
        Files.walk(worldPath).sorted(Comparator.reverseOrder()).forEach {
            Files.delete(it)
        }
    } catch (e: Exception) {
        INSTANCE.logger.warning("An Error occured while trying to delete the world files ($world)")
        INSTANCE.logger.warning(e.stackTraceToString())
    }
    Files.createDirectories(worldPath)
    Files.createDirectories(worldPath / "data")
    Files.createDirectories(worldPath / "datapacks")
    Files.createDirectories(worldPath / "playerdata")
    Files.createDirectories(worldPath / "poi")
    Files.createDirectories(worldPath / "region")
}

val mm = MiniMessage.miniMessage()

val INSTANCE = KSpigotMainInstance

fun spawnEntity(location: Location, entity: EntityType) {
    location.world.spawnEntity(location, entity)
}

fun world(world: String): World? {
    if (!Bukkit.getWorlds().contains(Bukkit.getWorld(world))) throw NullPointerException("World $world is null")
    return Bukkit.getWorld(world)
}

fun createWorld(world_name: String, environment: World.Environment) {
    val world: World?
    val worldCreator = WorldCreator(world_name).environment(environment)
    world = worldCreator.createWorld()
}

fun resetWorlds() {
    onlinePlayers.forEach {
        it.kick(
            mm.deserialize("<color:#93c47d>The worlds will get deleted now")
        )
    }
    Bukkit.getWorlds().forEach {
        deleteWorld(it.name)
    }
    Bukkit.spigot().restart()
}

fun color(color: String): String {
    return when (color) {
        "red" -> "<color:#cc0000>"
        "lightblue" -> "<color:#2986cc>"
        "darkblue" -> "<color:#0b5394>"
        "yellow" -> "<color:#ffd966>"
        "gray" -> "<color:#999999>"
        else -> "<color:#3d85c6>"
    }
}