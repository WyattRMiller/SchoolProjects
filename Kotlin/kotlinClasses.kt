/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         10/1/2022
 *  Assignment:   Kotlin Classes
 *  Class Number: 283
 *  Description:  A program that utilizes classes
 ************************************************************/

fun main(args: Array<String>) {
    //Use Person class
    val dave = Person("Dave", "Jones", 58, "Gray", "Green")
    println(dave)

    dave.lastName = "Smith"
    println(dave)

    //Use Address class
    val address = Address("1801 E Green St.", "Suite 103", "Spokane", "WA", 99201)
    println(address)

    address.city = "Seattle"
    println(address)

    //Use Character class
    val daveTheElf = Character("Dave", "Elf", 1000, 100)
    println(daveTheElf)

    daveTheElf.addWeapon("Bow")
    daveTheElf.addWeapon("Arrows")
    daveTheElf.addWeapon("Small Dagger")
    daveTheElf.addClothing("Leather Coat")
    daveTheElf.addClothing("Leather Shoes")
    daveTheElf.addClothing("Leather Hat")
    println(daveTheElf)

    daveTheElf.dropWeapon("Bow")
    daveTheElf.dropClothing("Leather Hat")
    println(daveTheElf)
}

class Person(var firstName: String, var lastName: String, var age: Int, var hairColor: String, var eyeColor: String) {
    override fun toString(): String {
        return "" +
                "PERSON:\n" +
                "    First Name: $firstName\n" +
                "    Last Name : $lastName\n" +
                "    Age       : $age\n" +
                "    Hair Color: $hairColor\n" +
                "    Eye Color : $eyeColor"
    }
}

class Address(var line1: String, var line2: String, var city: String, var state: String, var zip: Int) {
    override fun toString(): String {
        return "" +
                "ADDRESS:\n" +
                "    Line 1 : $line1\n" +
                "    Line 2 : $line2\n" +
                "    City   : $city\n" +
                "    State  : $state\n" +
                "    Zip    : $zip"
    }
}

class Character(var name: String, var race: String, var hitPoints: Int, var gold: Int) {
    var weaponsList = mutableListOf<String>()
    var clothingList = mutableListOf<String>()

    fun addWeapon(weapon: String) {
        weaponsList.add(weapon)

    }

    fun dropWeapon(weapon: String) {
        weaponsList.remove(weapon)
    }

    fun addClothing(clothing: String) {
        clothingList.add(clothing)
    }

    fun dropClothing(clothing: String) {
        clothingList.remove(clothing)
    }

    fun writeList(list: MutableList<String>): String {
        var num = 1
        var retString = ""

        for (i in list) {

            if (num < list.size) {
                retString += "$i, "
            } else {
                retString += i
            }

            num++
        }
        return retString
    }

    override fun toString(): String {
        return "" +
                "CHARACTER:\n" +
                "   Character name    : $name\n" +
                "   Character race    : $race\n" +
                "   Character HP      : $hitPoints\n" +
                "   Character gold    : $gold\n" +
                "   Character weapons : ${writeList(weaponsList)}\n" +
                "   Character clothing: ${writeList(clothingList)}"
    }
}