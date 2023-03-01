package inventory

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         11/6/2022
 *  Assignment:   Part Store Inventory
 *  Class Number: CIS 283
 *  Description:   A program that implements the basics of a part store inventory system.
 ************************************************************/

class Computer(
    name: String, retailPrice: Double, cost: Double, quantityInStock: Int,
    quantitySold: Int, detailDescription: String, partCategory: String, var gbOfRam: Int,
    var hddSize: Int, var processorSpeed: String
) : Parts(
    name, retailPrice, cost, quantityInStock,
    quantitySold, detailDescription, partCategory
) {

    override fun toTab(): String {
        return super.toTab() + "$gbOfRam\t$hddSize\t$processorSpeed"
    }

    override fun toString(): String {
        return super.toString() + gbOfRam.toString().padEnd(10) + hddSize.toString().padEnd(10) + processorSpeed.padEnd(
            10
        )
    }
}