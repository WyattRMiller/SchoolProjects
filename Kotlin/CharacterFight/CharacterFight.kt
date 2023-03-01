import java.io.File

private val maximumHP = 100
private val maximumSTR = 50
private val maximumAgility = 10

class Character(
    var name: String, var race: String, var hp: Int, var currentHP: Int, var str: Int,
    var agility: Int, var weapon: Weapon, var armor: Armor
) {


    fun currentStatus(): String {
        return "$name has $currentHP left out of $hp"
    }

    fun reviveCharacter() {
        currentHP = hp
    }

    fun reduceHits(damage: Int) {
        currentHP -= damage
    }

    override fun toString(): String {
        return "Name: $name\nRace: $race\nHP: $hp\nCurrent HP: $currentHP\nStrength: $str\nAgility: $agility\nWeapon: $weapon\nArmor: $armor"
    }
}

class Dice(var numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}

open class Item(var name: String)

open class Weapon(name: String, var damageHits: Int) : Item(name) {

    override fun toString(): String {
        return "$name Damage: $damageHits"
    }
}

open class Armor(name: String, var protectionHits: Int) : Item(name) {

    override fun toString(): String {
        return "$name Protection: $protectionHits"
    }
}

class Menu(var menuItems: Array<String>, var prompt: String) {

    var quit = menuItems.size
    fun displayMenu(): Int {
        for ((index, item) in menuItems.withIndex()) {
            println("${index + 1}. $item")
        }

        print(prompt)
        var retChoice = readLine()

        while (retChoice == "") {
            print("Please enter a valid choice: ")
            retChoice = readLine()
        }
        if (retChoice!!.toInt() == 0 || retChoice.toInt() > quit) {
            println("ERROR: Menu option entered does not exist")


        }
        return retChoice.toInt()
    }

}

fun main() {

    var characterOne = Character("", "", 0, 0, 0, 0, Weapon("", 0), Armor("", 0))
    var characterTwo = Character("", "", 0, 0, 0, 0, Weapon("", 0), Armor("", 0))

    val menuArray = Menu(
        arrayOf(
            "Load character 1",
            "Load character 2",
            "Fight",
            "Exit\n"
        ), "Please enter your selection: "
    )

    do {
        val choice = menuArray.displayMenu()

        when (choice) {
            1 -> {
                characterOne = loadCharacter()
            }

            2 -> {
                characterTwo = loadCharacter()
            }

            3 -> fight(characterOne, characterTwo)
        }

        if (characterOne.name != "") {
            menuArray.menuItems[0] = "Character 1: ${characterOne.name}"
        }
        if (characterTwo.name != "") {
            menuArray.menuItems[1] = "Character 2: ${characterTwo.name}"
        }

    } while (choice != menuArray.quit)

}

fun loadCharacter(): Character {

    val retCharacter: Character
    var name = ""
    var race = ""
    var hp = 0
    var currentHP = 0
    var str = 0
    var agility = 0
    var weapon = Weapon("", 0)
    var armor = Armor("", 0)

    print("Enter name of character file: ")
    var characterInput = readLine()!!
    characterInput = "src/main/kotlin/$characterInput"

    var fileLine = 0
    var inputFD = File(characterInput).forEachLine {
        val part1 = it.split(",")
        val part2 = it.split(", ")
        if (fileLine == 0) {
            name = part1[0]
            race = part1[1]
            hp = part2[1].toInt()
            currentHP = part2[1].toInt()
            str = part2[2].toInt()
            agility = part2[3].toInt()

        } else if (fileLine == 1) {
            weapon = Weapon(part2[0], part2[1].toInt())
        } else if (fileLine == 2) {
            armor = Armor(part2[0], part2[1].toInt())
        }
        fileLine++
    }

    if (hp > maximumHP)
        hp = maximumHP

    if (str > maximumSTR)
        str = maximumSTR

    if (agility > maximumAgility)
        agility = maximumAgility

    retCharacter = Character(name, race, hp, currentHP, str, agility, weapon, armor)

    return retCharacter
}

fun fight(character1: Character, character2: Character) {
    var dAgile1 = Dice(character1.agility).roll()
    var dAgile2 = Dice(character2.agility).roll()
    val d4 = Dice(4)
    val d8 = Dice(8)
    val d10 = Dice(10)
    val d15 = Dice(15)
    var counter = 1

    var attackingCharacter = character1

    while (dAgile1 == dAgile2) {
        dAgile1 = Dice(character1.agility).roll()
        dAgile2 = Dice(character2.agility).roll()
    }

    if (dAgile1 < dAgile2) {
        attackingCharacter = character2
    }

    while (attackingCharacter.currentHP > 0) {


        println("${attackingCharacter.name} fights with the ${attackingCharacter.weapon.name}:")

        if (d10.roll() < attackingCharacter.agility) {

            val hit = attackingCharacter.str * (1.0 / d4.roll()) + (attackingCharacter.weapon.damageHits) / d8.roll()

            val armorSave: Int = if (attackingCharacter.name == character1.name)
                character2.armor.protectionHits / d15.roll()
            else
                character1.armor.protectionHits / d15.roll()

            var damage = hit - armorSave

            if (damage < 1)
                damage = 0.0

            if (attackingCharacter.name == character1.name)
                character2.reduceHits(damage.toInt())
            else
                character1.reduceHits(damage.toInt())

            if (character1.currentHP < 0)
                character1.currentHP = 0
            else if (character2.currentHP < 0)
                character2.currentHP = 0

            if (attackingCharacter.name == character1.name) {
                println("             Hit: ${hit.toInt()}    ${character2.name}'s armor saved $armorSave points")
                println("${character2.name}'s hits are reduced by ${damage.toInt()} points.")
                println(character2.currentStatus())
            } else {
                println("             Hit: ${hit.toInt()}    ${character1.name}'s armor saved $armorSave points")
                println("${character1.name}'s hits are reduced by ${damage.toInt()} points.")
                println(character1.currentStatus())
            }

        } else {
            println("             Misses!")
        }

        attackingCharacter = if (attackingCharacter.name == character1.name)
            character2
        else
            character1


        if (attackingCharacter.currentHP > 0 && (counter % 2) == 0) {
            println("Hit return to continue ...")
            var continueFight = readLine()
        }
        counter++
    }

    if (character1.currentHP > 0)
        println("\n${character1.name} WINS!")
    else
        println("\n${character2.name} WINS!")

    println("-------------------------")
    println(character1.currentStatus())
    println(character2.currentStatus())
    println("-------------------------")

    character1.reviveCharacter()
    character2.reviveCharacter()
}
