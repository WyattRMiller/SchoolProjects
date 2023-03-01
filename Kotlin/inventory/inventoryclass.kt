package inventory

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         11/6/2022
 *  Assignment:   Part Store Inventory
 *  Class Number: CIS 283
 *  Description:   A program that implements the basics of a part store inventory system.
 ************************************************************/

class Inventory(var products: ArrayList<Any>) {

    // create functions for only printing a certain class
    fun computers(): String {
        var retString = ""

        for ((index, i) in products.withIndex()) {
            var computer: Parts = products[index] as Parts
            if (computer.partCategory == "COMPUTER") {
                retString += "${products[index]}\n"
            }
        }
        return retString
    }

    fun tablets(): String {
        var retString = ""

        for ((index, i) in products.withIndex()) {
            var tablet: Parts = products[index] as Parts
            if (tablet.partCategory == "TABLET") {
                retString += "${products[index]}\n"
            }
        }
        return retString
    }

    fun printers(): String {
        var retString = ""

        for ((index, i) in products.withIndex()) {
            var printer: Parts = products[index] as Parts
            if (printer.partCategory == "PRINTER") {
                retString += "${products[index]}\n"
            }
        }
        return retString
    }

    //create function to number products
    fun numberedProducts(): String {
        var retString = ""
        var num = 1

        for ((index, i) in products.withIndex()) {
            retString += "$num. " + products[index] + "\n"
            num++
        }

        return retString
    }

    fun totalInventory(): String {
        var retString = ""
        var totalProfit = 0.0

        for ((index, i) in products.withIndex()) {
            val part: Parts = products[index] as Parts
            retString += "${products[index]}" + "$${"%.2f".format(part.retailPrice - part.cost)}".padStart(15) + "$${
                "%.2f".format(
                    (part.retailPrice - part.cost) * part.quantitySold
                )
            }".padStart(15) + "\n"
            totalProfit += (part.retailPrice - part.cost) * part.quantitySold
        }
        retString += " ".repeat(181) + "--------------\n"
        retString += "$${"%.2f".format(totalProfit)}".padStart(195)

        return retString
    }

    fun detailedProduct(choice: Int): String {

        var retString = ""
        val part: Parts = products[choice] as Parts

        retString += "-".repeat(50) + "\n"
        retString += "Part Category:".padEnd(22) + part.partCategory + "\n"
        retString += "Part Name:".padEnd(22) + part.name + "\n"
        retString += "Retail Price:".padEnd(22) + "%.2f".format(part.retailPrice) + "\n"
        retString += "Cost:".padEnd(22) + "%.2f".format(part.cost) + "\n"
        retString += "QTY In Stock:".padEnd(22) + part.quantityInStock + "\n"
        retString += "QTY Sold:".padEnd(22) + part.quantitySold + "\n"
        retString += "Detailed Description:".padEnd(22) + part.detailDescription + "\n"

        if (part.partCategory == "COMPUTER") {
            val part: Computer = products[choice] as Computer

            retString += "GBs of RAM:".padEnd(22) + part.gbOfRam + "\n"
            retString += "GBs of HD Memory:".padEnd(22) + part.hddSize + "\n"
            retString += "Processor Speed:".padEnd(22) + part.processorSpeed + "\n"
        } else if (part.partCategory == "TABLET") {
            val part: Tablet = products[choice] as Tablet

            retString += "Screen Size:".padEnd(22) + part.screenSize + "\n"
            retString += "MBs of RAM:".padEnd(22) + part.mbOfRam + "\n"
            retString += "SD slot?:".padEnd(22) + part.sdSlot + "\n"
        } else if (part.partCategory == "PRINTER") {
            val part: Printer = products[choice] as Printer

            retString += "Color?:".padEnd(22) + part.color + "\n"
            retString += "Pager Per Minute:".padEnd(22) + part.pagesPerMinute + "\n"
            retString += "Scanner?:".padEnd(22) + part.scanner + "\n"
        }

        retString += "-".repeat(50)

        return retString
    }

    override fun toString(): String {
        var retString = ""

        for ((index, i) in products.withIndex()) {
            retString += "${products[index]}\n"
        }

        return retString
    }
}