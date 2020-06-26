package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.*
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.iCrossHairID
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.robot
import rat.poison.scripts.aim.boneTrig
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.inMyTeam
import rat.poison.settings.AIM_KEY
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.varUtil.strToBool
import java.awt.event.InputEvent.BUTTON1_DOWN_MASK

private var callingInShot = false
var triggerInShot = false

fun boneTrigger() = every(10) {
    if (DANGER_ZONE || me.dead()) {
        return@every
    }

    if (curSettings["ENABLE_TRIGGER"].strToBool() && checkFlags("ENABLE_TRIGGER")) {
        val wep = me.weapon()

        val bFOV : Int; val bDELAY : Int; val bINCROSS : Boolean; val bINFOV : Boolean; val bAIMBOT : Boolean

        var prefix = ""

        when {
            wep.pistol -> { prefix = "PISTOL_" }
            wep.rifle -> { prefix = "RIFLE_" }
            wep.shotgun -> { prefix = "SHOTGUN_" }
            wep.sniper -> { prefix = "SNIPER_" }
            wep.smg -> { prefix = "SMG_" }
        }

        if (wep.gun) { //Not 100% this applies to every 'gun'
            bFOV = curSettings[prefix + "TRIGGER_FOV"].toInt()
            bDELAY = curSettings[prefix + "TRIGGER_SHOT_DELAY"].toInt()
            bINCROSS = curSettings[prefix + "TRIGGER_INCROSS"].strToBool()
            bINFOV = curSettings[prefix + "TRIGGER_INFOV"].strToBool()
            bAIMBOT = curSettings[prefix + "TRIGGER_AIMBOT"].strToBool()

            if (wep.sniper) {
                if (!(curSettings["SNIPER_TRIGGER"].strToBool() && ((me.isScoped() && curSettings["ENABLE_SCOPED_ONLY"].strToBool()) || (!curSettings["ENABLE_SCOPED_ONLY"].strToBool())))) {
                    return@every
                }
            }

            val wepEnt = me.weaponEntity()
            if (!me.weapon().knife && wepEnt.bullets() > 0) {
                if (wep.automatic || (!wep.automatic && wepEnt.canFire())) {
                    if (keyReleased(AIM_KEY)) {
                        if (bINCROSS) {
                            val inCross = csgoEXE.uint(me + iCrossHairID)
                            if (inCross > 0) {
                                val ent = clientDLL.uint(ClientOffsets.dwEntityList + (inCross * 0x10) - 0x10)
                                if (!ent.inMyTeam() && !ent.isProtected() && !ent.dead()) {
                                    if (checkFlags("ENABLE_TRIGGER")) {
                                        bTrigShoot(bDELAY, bAIMBOT)
                                        return@every
                                    }
                                }
                            }
                        }

                        if (bINFOV) {
                            val currentAngle = clientState.angle()
                            val position = me.position()
                            val target = findTarget(position, currentAngle, false, bFOV, -2)
                            if (target > 0) {
                                if (!target.dead() && !target.isProtected()) {
                                    if (checkFlags("ENABLE_TRIGGER")) {
                                        bTrigShoot(bDELAY, bAIMBOT)
                                        return@every
                                    }
                                }
                            } else {
                                boneTrig = false
                            }
                        } else {
                            boneTrig = false //that shit handled at end
                        }
                    }
                }
            }
        }

        callingInShot = false
        triggerInShot = false
    } else {
        boneTrig = false
        triggerInShot = false
    }
}

//Initial shot delay
fun bTrigShoot(delay: Int, aimbot: Boolean = false) {
    if (!callingInShot) {
        callingInShot = true
        if (delay > 0) {
            Thread(Runnable {
                Thread.sleep(delay.toLong())
                triggerInShot = true
            }).start()
        } else {
            triggerInShot = true
        }
    }

    if (triggerInShot) {
        boneTrig = aimbot

        clientDLL[dwForceAttack] = 6
        Thread.sleep(1)
    }
}
