package inventory

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         11/6/2022
 *  Assignment:   Part Store Inventory
 *  Class Number: CIS 283
 *  Description:   A program that implements the basics of a part store inventory system.
 ************************************************************/

open class Parts(
    var name: String, var retailPrice: Double, var cost: Double, var quantityInStock: Int,
    var quantitySold: Int, var detailDescription: String, var partCategory: String
) {

    open fun toTab(): String {
        return "$partCategory\t$name\t$retailPrice\t$cost\t$quantityInStock\t$quantitySold\t$detailDescription\t"
    }

    override fun toString(): String {
        return partCategory.padEnd(15) + name.padEnd(25) + "$${"%.2f".format(retailPrice)}".padEnd(15) +
                "$${"%.2f".format(cost)}".padEnd(15) + quantityInStock.toString().padEnd(15) + quantitySold.toString()
            .padEnd(10) +
                detailDescription.padEnd(40)
    }
}