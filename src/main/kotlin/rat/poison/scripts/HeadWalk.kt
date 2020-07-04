package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.forEntities
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.robot
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.every
import rat.poison.utils.varUtil.strToBool
import java.awt.event.KeyEvent
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

private var onEnt = 0L

//Currently just a poc
////Only moves towards the center of the player
//////Keeps up with crouching just fine, but not normal running usually
////////Doesn't predict, isn't accurate

var mePos = Vector()
var onEntPos = Vector()

internal fun headWalk() = every(1) {
    if (!curSettings["HEAD_WALK"].strToBool() || !checkFlags("HEAD_WALK")) return@every

    if (me.dead()) return@every

    if (!keyPressed(KeyEvent.VK_W) && !keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_S) && !keyPressed(KeyEvent.VK_D)) {
        mePos = me.absPosition()
        if (onPlayerHead()) {
            updateCursorEnable()
            if (cursorEnable) return@every

            val pos = Vector(mePos.x - onEntPos.x, mePos.y - onEntPos.y, mePos.z - onEntPos.z)
            val yaw = clientState.angle().y
            val calcYaw = yaw/180*Math.PI

            //Is this even needed?
            val distX = (pos.x * cos(calcYaw) + pos.y * sin(calcYaw)).toInt()
            val distY = (pos.y * cos(calcYaw) - pos.x * sin(calcYaw)).toInt()

            //Gotta be a simpler way
            var wRelease = false
            var aRelease = false
            var sRelease = false
            var dRelease = false
            when {
                distX < 2 -> {
                    robot.keyPress(KeyEvent.VK_W)
                    wRelease = true
                }
                distX > 2 -> {
                    robot.keyPress(KeyEvent.VK_S)
                    sRelease = true
                }
            }

            when {
                distY < 2 -> {
                    robot.keyPress(KeyEvent.VK_A)
                    aRelease = true
                }
                distY > 2 -> {
                    robot.keyPress(KeyEvent.VK_D)
                    dRelease = true
                }
            }
            //Sleep once instead of up to twice
            Thread.sleep(1)
            if (wRelease) {
                robot.keyRelease(KeyEvent.VK_W)
            }
            if (aRelease) {
                robot.keyRelease(KeyEvent.VK_A)
            }
            if (sRelease) {
                robot.keyRelease(KeyEvent.VK_S)
            }
            if (dRelease) {
                robot.keyRelease(KeyEvent.VK_D)
            }
        }
    }
}

internal fun onPlayerHead() : Boolean {
    var entPos : Angle
    onEnt = 0L

    forEntities(ccsPlayer) {
        val entity = it.entity
        if (entity == me || !entity.onGround() || entity.dead()) return@forEntities false

        entPos = entity.absPosition()

        val xDist = abs(mePos.x - entPos.x)
        val yDist = abs(mePos.y - entPos.y)
        //val zDif = mePos.z - entPos.z
        //zDiv in 50.0..75.0

        if (xDist <= 30 && yDist <= 30 && mePos.z > entPos.z) {
            onEnt = entity
            onEntPos = entPos
        }

        if (onEnt != 0L) {
            return@forEntities false
        }

        false
    }

    return when (onEnt) {
        0L -> false
        else -> true
    }
}