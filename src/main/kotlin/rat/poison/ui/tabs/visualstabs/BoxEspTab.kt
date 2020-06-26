﻿package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.boxEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.binds.BindsRelatedCheckBox
import rat.poison.visualsMap

class BoxEspTab : Tab(false, false) {
    private val table = VisTable()

    var map = visualsMap()
    //Init labels/sliders/boxes that show values here
    val skeletonEsp = BindsRelatedCheckBox(curLocalization["ENABLE_SKELETON_ESP"], "SKELETON_ESP", nameInLocalization = "ENABLE_SKELETON_ESP")
    val showTeamSkeleton = VisCheckBoxCustom(curLocalization["TEAMMATES"], "SKELETON_SHOW_TEAM", nameInLocalization = "TEAMMATES")
    val showEnemiesSkeleton = VisCheckBoxCustom(curLocalization["ENEMIES"], "SKELETON_SHOW_ENEMIES", nameInLocalization = "ENEMIES")
    val boxEsp = BindsRelatedCheckBox(curLocalization["ENABLE_BOX_ESP"], "ENABLE_BOX_ESP", nameInLocalization = "ENABLE_BOX_ESP")
    val boxShowHealth = VisCheckBoxCustom(curLocalization["HEALTH_BASED"], "BOX_SHOW_HEALTH", "HEALTH_BASED")
    val boxEspDetails = VisCheckBoxCustom(curLocalization["ENABLE_BOX_ESP_DETAILS"], "BOX_ESP_DETAILS", nameInLocalization = "ENABLE_BOX_ESP_DETAILS")
    val boxEspHealth = VisCheckBoxCustom(curLocalization["HEALTH"], "BOX_ESP_HEALTH", nameInLocalization = "HEALTH")
    val boxEspHealthPos = VisSelectBox<String>()
    val boxEspArmor = VisCheckBoxCustom(curLocalization["ARMOR"], "BOX_ESP_ARMOR", nameInLocalization = "ARMOR")
    val boxEspArmorPos = VisSelectBox<String>()
    val boxEspName = VisCheckBoxCustom(curLocalization["NAME"], "BOX_ESP_NAME", nameInLocalization = "NAME")
    val boxEspNamePos = VisSelectBox<String>()
    val boxEspWeapon = VisCheckBoxCustom(curLocalization["WEAPON"], "BOX_ESP_WEAPON", nameInLocalization = "WEAPON")
    val boxEspWeaponPos = VisSelectBox<String>()
    val boxDetailColor = VisColorPickerCustom(curLocalization["BOX_DETAILS_TEXT_COLOR"], "BOX_DETAILS_TEXT_COLOR", nameInLocalization = "BOX_DETAILS_TEXT_COLOR")
    val boxEspAmmo = VisCheckBoxCustom(curLocalization["AMMO"], "BOX_ESP_AMMO", "AMMO")
    val boxEspAmmoPos = VisSelectBox<String>()
    val boxEspHelmet = VisCheckBoxCustom(curLocalization["HELMET"], "BOX_ESP_HELMET", "HELMET")
    val boxEspHelmetPos = VisSelectBox<String>()
    val boxEspKevlar = VisCheckBoxCustom(curLocalization["KEVLAR"], "BOX_ESP_KEVLAR", "KEVLAR")
    val boxEspKevlarPos = VisSelectBox<String>()
    val boxEspScoped = VisCheckBoxCustom(curLocalization["SCOPED"], "BOX_ESP_SCOPED", "SCOPED")
    val boxEspScopedPos = VisSelectBox<String>()
    val boxEspFlashed = VisCheckBoxCustom(curLocalization["FLASHED"], "BOX_ESP_FLASHED", "FLASHED")
    val boxEspFlashedPos = VisSelectBox<String>()

    val showTeamBox = VisCheckBoxCustom(" ", "BOX_SHOW_TEAM")
    val boxTeamColor = VisColorPickerCustom(curLocalization["TEAMMATES"], "BOX_TEAM_COLOR", nameInLocalization = "TEAMMATES")

    val showEnemiesBox = VisCheckBoxCustom(" ", "BOX_SHOW_ENEMIES")
    val boxEnemyColor = VisColorPickerCustom(curLocalization["ENEMIES"], "BOX_ENEMY_COLOR", nameInLocalization = "ENEMIES")

    val showDefusers = VisCheckBoxCustom(" ", "BOX_SHOW_DEFUSERS")
    val boxDefuserColor = VisColorPickerCustom(curLocalization["DEFUSERS"], "BOX_DEFUSER_COLOR", nameInLocalization = "DEFUSERS")

    val showWeapons = VisCheckBoxCustom(" ", "BOX_SHOW_WEAPONS")
    val boxWeaponsColor = VisColorPickerCustom(curLocalization["WEAPONS"], "BOX_WEAPONS_COLOR", "WEAPONS")

    fun updateMap() {
        map = visualsMap()
    }

    init {
        //Create Box ESP Health Pos Selector
        boxEspHealthPos.setItems(curLocalization["LEFT"], curLocalization["RIGHT"])
        boxEspHealthPos.selected = when (curSettings["BOX_ESP_HEALTH_POS"].replace("\"", "")) {
            "L" -> curLocalization["LEFT"]
            else -> curLocalization["RIGHT"]
        }
        boxEspHealthPos.changed { _, _ ->
            curSettings["BOX_ESP_HEALTH_POS"] = map[boxEspHealthPos.selected].first()
            true
        }

        //Create Box ESP Armor Pos Selector
        boxEspArmorPos.setItems(curLocalization["LEFT"], curLocalization["RIGHT"])
        boxEspArmorPos.selected = when (curSettings["BOX_ESP_ARMOR_POS"].replace("\"", "")) {
            "L" -> curLocalization["LEFT"]
            else -> curLocalization["RIGHT"]
        }
        boxEspArmorPos.changed { _, _ ->
            curSettings["BOX_ESP_ARMOR_POS"] = map[boxEspArmorPos.selected].first()
            true
        }

        //Create Box ESP Name Pos Selector
        boxEspNamePos.setItems(curLocalization["TOP"], curLocalization["BOTTOM"])
        boxEspNamePos.selected = when (curSettings["BOX_ESP_NAME_POS"].replace("\"", "")) {
            "T" -> curLocalization["TOP"]
            else -> curLocalization["BOTTOM"]
        }
        boxEspNamePos.changed { _, _ ->
            curSettings["BOX_ESP_NAME_POS"] = map[boxEspNamePos.selected].first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspWeaponPos.setItems(curLocalization["TOP"], curLocalization["BOTTOM"])
        boxEspWeaponPos.selected = when (curSettings["BOX_ESP_WEAPON_POS"].replace("\"", "")) {
            "T" -> curLocalization["TOP"]
            else -> curLocalization["BOTTOM"]
        }
        boxEspWeaponPos.changed { _, _ ->
            curSettings["BOX_ESP_WEAPON_POS"] = map[boxEspWeaponPos.selected].first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspHelmetPos.setItems(curLocalization["LEFT"], curLocalization["RIGHT"])
        boxEspHelmetPos.selected = when (curSettings["BOX_ESP_HELMET_POS"].replace("\"", "")) {
            "L" -> curLocalization["LEFT"]
            else -> curLocalization["RIGHT"]
        }
        boxEspHelmetPos.changed { _, _ ->
            curSettings["BOX_ESP_HELMET_POS"] = map[boxEspHelmetPos.selected].first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspKevlarPos.setItems(curLocalization["LEFT"], curLocalization["RIGHT"])
        boxEspKevlarPos.selected = when (curSettings["BOX_ESP_KEVLAR_POS"].replace("\"", "")) {
            "L" -> curLocalization["LEFT"]
            else -> curLocalization["RIGHT"]
        }
        boxEspKevlarPos.changed { _, _ ->
            curSettings["BOX_ESP_KEVLAR_POS"] = map[boxEspKevlarPos.selected].first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspAmmoPos.setItems(curLocalization["TOP"], curLocalization["BOTTOM"])
        boxEspAmmoPos.selected = when (curSettings["BOX_ESP_AMMO_POS"].replace("\"", "")) {
            "T" -> curLocalization["TOP"]
            else -> curLocalization["BOTTOM"]
        }
        boxEspAmmoPos.changed { _, _ ->
            curSettings["BOX_ESP_AMMO_POS"] = map[boxEspAmmoPos.selected].first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspScopedPos.setItems(curLocalization["LEFT"], curLocalization["RIGHT"])
        boxEspScopedPos.selected = when (curSettings["BOX_ESP_SCOPED_POS"].replace("\"", "")) {
            "L" -> curLocalization["LEFT"]
            else -> curLocalization["RIGHT"]
        }
        boxEspScopedPos.changed { _, _ ->
            curSettings["BOX_ESP_SCOPED_POS"] = map[boxEspScopedPos.selected].first()
            true
        }

        //Create Box ESP Weapon Pos Selector
        boxEspFlashedPos.setItems(curLocalization["LEFT"], curLocalization["RIGHT"])
        boxEspFlashedPos.selected = when (curSettings["BOX_ESP_FLASHED_POS"].replace("\"", "")) {
            "L" -> curLocalization["LEFT"]
            else -> curLocalization["RIGHT"]
        }
        boxEspFlashedPos.changed { _, _ ->
            curSettings["BOX_ESP_FLASHED_POS"] = map[boxEspFlashedPos.selected].first()
            true
        }

        table.padLeft(25F)
        table.padRight(25F)

        table.add(skeletonEsp).left().row()
        table.add(showTeamSkeleton).padRight(225F - showTeamSkeleton.width).left()
        table.add(showEnemiesSkeleton).padRight(225F - showEnemiesSkeleton.width).left().row()
        table.addSeparator().colspan(2)
        table.add(boxEsp).left().row()
        table.add(boxEspDetails).left().row()
        table.add(boxShowHealth).left()
        table.add(boxEspHealth).left()
        table.add(boxEspHealthPos).left().row()
        table.add(boxEspArmor).left()
        table.add(boxEspArmorPos).left().row()
        table.add(boxEspName).left()
        table.add(boxEspNamePos).left().row()
        table.add(boxEspWeapon).left()
        table.add(boxEspWeaponPos).left().row()
        table.add(boxEspAmmo).left()
        table.add(boxEspAmmoPos).left().row()
        table.add(boxEspHealth).left()
        table.add(boxEspHealthPos).left().row()
        table.add(boxEspArmor).left()
        table.add(boxEspArmorPos).left().row()
        table.add(boxEspHelmet).left()
        table.add(boxEspHelmetPos).left().row()
        table.add(boxEspKevlar).left()
        table.add(boxEspKevlarPos).left().row()
        table.add(boxEspScoped).left()
        table.add(boxEspScopedPos).left().row()
        table.add(boxEspFlashed).left()
        table.add(boxEspFlashedPos).left().row()

        table.add(boxDetailColor).width(175F - boxDetailColor.width).padRight(50F).row()

        var tmpTable = VisTable()
        tmpTable.add(showTeamBox)
        tmpTable.add(boxTeamColor).width(175F - showTeamBox.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(showEnemiesBox)
        tmpTable.add(boxEnemyColor).width(175F - showEnemiesBox.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showDefusers)
        tmpTable.add(boxDefuserColor).width(175F - showEnemiesBox.width).padRight(50F)

        table.add(tmpTable).left().row()

        tmpTable = VisTable()
        tmpTable.add(showWeapons)
        tmpTable.add(boxWeaponsColor).width(175F - showEnemiesBox.width).padRight(50F)
        table.add(tmpTable).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["BOX_ESP_TAB_NAME"]
    }
}

fun boxEspTabUpdate() {
    boxEspTab.apply {
        updateMap()

        boxEsp.update()
        boxEspDetails.update()
        boxShowHealth.update()
        boxEspHealth.update()
        boxEspArmor.update()
        boxEspName.update()
        boxEspWeapon.update()
        boxEspHelmet.update()
        boxEspKevlar.update()
        boxEspAmmo.update()
        boxEspScoped.update()
        boxEspFlashed.update()
        boxDetailColor.update()
        skeletonEsp.update()
        showTeamSkeleton.update()
        showEnemiesSkeleton.update()
        showTeamBox.update()
        showEnemiesBox.update()
        boxTeamColor.update()
        boxEnemyColor.update()
        boxDefuserColor.update()
        boxDefuserColor.updateTitle()
        boxDetailColor.updateTitle()
        boxEnemyColor.updateTitle()
        boxTeamColor.updateTitle()
    }
}