package inventory

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         11/6/2022
 *  Assignment:   Part Store Inventory
 *  Class Number: CIS 283
 *  Description:   A program that implements the basics of a part store inventory system.
 ************************************************************/

class Printer(
    name: String, retailPrice: Double, cost: Double, quantityInStock: Int,
    quantitySold: Int, detailDescription: String, partCategory: String, var color: Boolean,
    var pagesPerMinute: Int, var scanner: Boolean
) : Parts(
    name, retailPrice, cost, quantityInStock,
    quantitySold, detailDescription, partCategory
) {

    override fun toTab(): String {
        return super.toTab() + "$color\t$pagesPerMinute\t$scanner"
    }

    override fun toString(): String {
        return super.toString() + color.toString().padEnd(10) + pagesPerMinute.toString().padEnd(10) +
                scanner.toString().padEnd(10)
    }
}