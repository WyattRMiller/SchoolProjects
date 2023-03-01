package inventory

import java.io.File

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         11/6/2022
 *  Assignment:   Part Store Inventory
 *  Class Number: CIS 283
 *  Description:   A program that implements the basics of a part store inventory system.
 ************************************************************/

class GetMenu(var menuItems: Array<String>, var prompt: String) {

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

    //create header for printing reports
    var header = ""
    var expandedHeader = ""

    //create variables to print back to the file
    var retHeader = ""
    var retColWidth = ""

    //create arrays for single objects
    var products = ArrayList<Any>()
    val colWidth = ArrayList<Int>()


    //create link to tsv file and create incrementer variable for going through the file
    val itemFile = "src/main/kotlin/inventory/parts_database.tsv"
    var fileIncrement = 0

    //read from file and decide what class each item is.
    var inputFD = File(itemFile).forEachLine {

        //split lines into single values
        val split = it.split("\t")

        if (split[0] == "COMPUTER") {
            products.add(
                Computer(
                    split[1], split[2].toDouble(), split[3].toDouble(), split[4].toInt(),
                    split[5].toInt(), split[6], split[0], split[7].toInt(), split[8].toInt(), split[9]
                )
            )
        } else if (split[0] == "TABLET") {
            products.add(
                Tablet(
                    split[1], split[2].toDouble(), split[3].toDouble(), split[4].toInt(),
                    split[5].toInt(), split[6], split[0], split[7], split[8].toInt(), split[9].toBoolean()
                )
            )
        } else if (split[0] == "PRINTER") {
            products.add(
                Printer(
                    split[1], split[2].toDouble(), split[3].toDouble(), split[4].toInt(),
                    split[5].toInt(), split[6], split[0], split[7].toBoolean(), split[8].toInt(), split[9].toBoolean()
                )
            )
        } else if (fileIncrement == 0) {
            retColWidth = it

            for ((inc, i) in split.withIndex()) {
                colWidth.add(split[inc].toInt())
            }
        } else if (fileIncrement == 1) {
            retHeader = it

            for ((count, i) in split.withIndex()) {
                header += split[count].padEnd(colWidth[count])
                expandedHeader += split[count].padEnd(colWidth[count])
            }
            expandedHeader += "Profit Each".padStart(15) + "Grand Total".padStart(15)

            header += "\n"
            expandedHeader += "\n"

            for ((count, i) in split.withIndex()) {
                header += "-".repeat(colWidth[count] - 1) + " "
                expandedHeader += "-".repeat(colWidth[count] - 1) + " "
            }
            expandedHeader += "-".repeat(15) + " " + "-".repeat(14)
        }
        fileIncrement++
    }

    //create inventory object with all items
    val inventory = Inventory(products)

    //Create arrays to call menu class
    val menuArray = GetMenu(
        arrayOf(
            "List All Parts",
            "Show All of a particular Category",
            "Sell a Part",
            "Increase inventory of a particular part",
            "Update elements of a single part",
            "Add a brand new part to inventory",
            "Completely remove a part from inventory",
            "Show the detail information about a part",
            "Show Total Inventory Report with a new columns of Cost, Retail Price, Quantity Sold, Profit",
            "Exit\n"
        ), "Please enter your selection: "
    )

    val categoryArray = GetMenu(
        arrayOf(
            "Computers",
            "Tablets",
            "Printers\n"
        ), "Please enter a category: "
    )

    //loop menu until user quits
    do {
        val choice = menuArray.displayMenu()

        when (choice) {
            1 -> println(listParts(0, header, inventory))

            2 -> {
                val catNum = categoryArray.displayMenu()
                println(listParts(catNum, header, inventory))
            }

            3 -> {
                println(listParts(4, header, inventory))
                print("Please select a product to sell: ")
                val productNum = readLine()!!.toInt() - 1
                print("How many of the product would you like to sell? ")
                val sellNum = readLine()!!.toInt()

                val product: Parts = products[productNum] as Parts
                product.quantitySold += sellNum
                product.quantityInStock -= sellNum
            }

            4 -> {
                println(listParts(4, header, inventory))
                print("Please select a product to increase: ")
                val productNum = readLine()!!.toInt() - 1
                print("How many of the product would you like to add? ")
                val addNum = readLine()!!.toInt()

                val product: Parts = products[productNum] as Parts
                product.quantityInStock += addNum
            }

            5 -> {
                println(listParts(4, header, inventory))
                updateOrAddPart(0, products)
            }

            6 -> updateOrAddPart(1, products)

            7 -> {
                println(listParts(4, header, inventory))
                print("Please select a product to delete: ")
                val productNum = readLine()!!.toInt() - 1

                products.remove(products[productNum])
            }

            8 -> {
                println(listParts(4, header, inventory))
                print("Please select a product to see details: ")
                val productNum = readLine()!!.toInt() - 1
                println(inventory.detailedProduct(productNum))
            }

            9 -> println(listParts(5, expandedHeader, inventory))

        }
    } while (choice != menuArray.quit)


    //Print inventory into file, then close and save the file.
    val fd = File(itemFile).printWriter()

    fd.println(retColWidth)
    fd.println(retHeader)

    for ((i, part) in products.withIndex()) {
        val product: Parts = products[i] as Parts
        fd.println(product.toTab())
    }
    fd.close()

}

fun listParts(choice: Int, header: String, partsArray: Inventory): String {
    var retString = ""

    for (i in header) {
        if (i.toString() == "_") {
            retString += " "
        } else {
            retString += i
        }
    }

    retString += "\n"

    if (choice == 0) {
        retString += partsArray
    } else if (choice == 1) {
        retString += partsArray.computers()
    } else if (choice == 2) {
        retString += partsArray.tablets()
    } else if (choice == 3) {
        retString += partsArray.printers()
    } else if (choice == 4) {
        retString += partsArray.numberedProducts()
    } else if (choice == 5) {
        retString += partsArray.totalInventory()
    }

    return retString
}

fun updateOrAddPart(choice: Int, products: ArrayList<Any>): ArrayList<Any> {

    //create variables
    var productNum = Int.MAX_VALUE
    var product = Parts("", 0.0, 0.0, 0, 0, "", "")
    var newPartCategory = 0

    var gbOfRam = 0
    var hddSize = 0
    var processorSpeed = ""

    var screenSize = ""
    var mbOfRam = 0
    var sdSlot = true

    var color = true
    var pagesPerMinute = 0
    var scanner = true

    var choice0 = 0
    var choice1 = 0
    var choice2 = 0

    if (choice == 0) {
        print("Please select a product to update: ")
        productNum = readLine()!!.toInt() - 1
        product = products[productNum] as Parts
    } else if (choice == 1) {
        println("What kind of part would you like to add?")
        println("1. COMPUTER")
        println("2. TABLET")
        println("3. PRINTER")
        print("Please enter your choice: ")
        newPartCategory = readLine()!!.toInt()
    }

    print("Please enter a new product name: ")
    var name = readLine()!!.toString()

    print("Please enter a new retail price: ")
    var retailPrice = readLine()!!.toDouble()

    print("Please enter a new cost: ")
    var cost = readLine()!!.toDouble()

    print("Please enter a new quantity in stock: ")
    var quantityInStock = readLine()!!.toInt()

    print("Please enter a new quantity sold: ")
    var quantitySold = readLine()!!.toInt()

    print("Please enter a new description: ")
    var detailDescription = readLine()!!.toString()

    if (product.partCategory == "COMPUTER" || newPartCategory == 1) {
        print("Please enter the new amount of ram in gigabytes: ")
        gbOfRam = readLine()!!.toInt()

        print("Please enter a new hard drive size in gigabytes: ")
        hddSize = readLine()!!.toInt()

        print("Please enter a new processor speed (x.xGhz): ")
        processorSpeed = readLine()!!.toString()

    }

    if (product.partCategory == "TABLET" || newPartCategory == 2) {
        print("Please enter a new screen size: ")
        screenSize = readLine()!!.toString()

        print("Please enter amount of ram in megabytes: ")
        mbOfRam = readLine()!!.toInt()

        println("Please enter if it has an sd slot")
        println("1. true")
        println("2. false")
        print("Please enter your choice: ")
        choice0 = readLine()!!.toInt()
        if (choice0 == 1) {
            sdSlot = true
        } else if (choice0 == 2) {
            sdSlot = false
        }
    }

    if (product.partCategory == "PRINTER" || newPartCategory == 3) {
        println("Please enter if it has color")
        println("1. true")
        println("2. false")
        print("Please enter your choice: ")
        choice1 = readLine()!!.toInt()
        if (choice1 == 1) {
            color = true
        } else if (choice1 == 2) {
            color = false
        }

        print("Please enter a new pages per minute: ")
        pagesPerMinute = readLine()!!.toInt()

        println("Please enter if it has a scanner")
        println("1. true")
        println("2. false")
        print("Please enter your choice: ")
        choice2 = readLine()!!.toInt()
        if (choice2 == 1) {
            scanner = true
        } else if (choice2 == 2) {
            scanner = false
        }
    }

    if (choice == 0) {
        product.name = name
        product.retailPrice = retailPrice
        product.cost = cost
        product.quantityInStock = quantityInStock
        product.quantitySold = quantitySold
        product.detailDescription = detailDescription

        if (product.partCategory == "COMPUTER") {
            val computer: Computer = products[productNum] as Computer
            computer.gbOfRam = gbOfRam
            computer.hddSize = hddSize
            computer.processorSpeed = processorSpeed
        } else if (product.partCategory == "TABLET") {
            val tablet: Tablet = products[productNum] as Tablet
            tablet.screenSize = screenSize
            tablet.mbOfRam = mbOfRam

            if (choice0 == 1) {
                tablet.sdSlot = true
            } else if (choice0 == 2) {
                tablet.sdSlot = false
            }

        } else if (product.partCategory == "PRINTER") {
            val printer: Printer = products[productNum] as Printer

            if (choice1 == 1) {
                printer.color = true
            } else if (choice1 == 2) {
                printer.color = false
            }

            printer.pagesPerMinute = pagesPerMinute

            if (choice2 == 1) {
                printer.scanner = true
            } else if (choice2 == 2) {
                printer.scanner = false
            }
        }
    }

    if (newPartCategory == 1) {
        products.add(
            Computer(
                name,
                retailPrice,
                cost,
                quantityInStock,
                quantitySold,
                detailDescription,
                "COMPUTER",
                gbOfRam,
                hddSize,
                processorSpeed
            )
        )
    } else if (newPartCategory == 2) {
        products.add(
            Tablet(
                name,
                retailPrice,
                cost,
                quantityInStock,
                quantitySold,
                detailDescription,
                "TABLET",
                screenSize,
                mbOfRam,
                sdSlot
            )
        )
    } else if (newPartCategory == 3) {
        products.add(
            Printer(
                name,
                retailPrice,
                cost,
                quantityInStock,
                quantitySold,
                detailDescription,
                "PRINTER",
                color,
                pagesPerMinute,
                scanner
            )
        )
    }

    return products
}