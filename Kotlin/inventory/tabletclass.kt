package inventory

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         11/6/2022
 *  Assignment:   Part Store Inventory
 *  Class Number: CIS 283
 *  Description:   A program that implements the basics of a part store inventory system.
 ************************************************************/

class Tablet(
    name: String, retailPrice: Double, cost: Double, quantityInStock: Int,
    quantitySold: Int, detailDescription: String, partCategory: String, var screenSize: String,
    var mbOfRam: Int, var sdSlot: Boolean
) : Parts(
    name, retailPrice, cost, quantityInStock,
    quantitySold, detailDescription, partCategory
) {

    override fun toTab(): String {
        return super.toTab() + "$screenSize\t$mbOfRam\t$sdSlot"
    }

    override fun toString(): String {
        return super.toString() + screenSize.padEnd(10) + mbOfRam.toString().padEnd(10) + sdSlot.toString().padEnd(10)
    }
}