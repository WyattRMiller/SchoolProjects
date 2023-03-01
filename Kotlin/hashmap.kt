import java.io.File

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         5/16/2022
 *  Assignment:   Hashmaps
 *  Class Number: CIS 282
 *  Description:  A menu-driven program that interacts with a set of products.
 ************************************************************/

fun main(args: Array<String>) {

  //Create empty hashmap, then read products.txt into the hashmap
  val productFile = "src/products.txt"
  val productHashMap = HashMap<Int, Pair<String, Double>>()

  var inputFD = File(productFile).forEachLine {
    val parts = it.split(",")
    productHashMap[parts[0].toInt()] = Pair(parts[1], parts[2].toDouble())
  }

  //Create menu
  val menuArray = arrayOf(
    "View all products",
    "Add a new product",
    "Delete a product",
    "Update a product",
    "View highest priced product",
    "View lowest priced product",
    "View sum of all product prices",
    "Exit\n"
  )
  val prompt = "Please enter your selection: "

  val quitOption = menuArray.size
  var userChoice = 0
  while (userChoice != quitOption) {
    userChoice = menu(menuArray, prompt)

    when (userChoice) {
      1 -> viewProducts(productHashMap)
      2 -> newProduct(productHashMap)
      3 -> deleteProduct(productHashMap)
      4 -> updateProduct(productHashMap)
      5 -> getProductTop(productHashMap)
      6 -> getProductBottom(productHashMap)
      7 -> productsSum(productHashMap)
      else -> {
        if (userChoice != quitOption) {
          println("ERROR: Menu option entered does not exist")
        }
      }
    }

  }

  //Print hashmap into file, then close and save the file.
  val fd = File(productFile).printWriter()
  for (key in productHashMap.keys) {
    fd.println("$key,${productHashMap[key]!!.first},${"%.2f".format(productHashMap[key]!!.second)}")
  }
  fd.close()

}

fun menu(menuArray: Array<String>, prompt: String): Int {

  //Create menu function
  for ((index, item) in menuArray.withIndex()) {
    println("${index + 1}. $item")
  }

  print(prompt)

  val userChoice = readLine()!!.toInt()

  return userChoice

}

fun viewProducts(productHashMap: HashMap<Int, Pair<String, Double>>) {

  //Print out each item of the hashmap formatted neatly.
  println("Item     Description     Price")
  println("-----    --------------  ------")
  for (key in productHashMap.keys.sorted()) {
    println(
      "${key.toString().padEnd(9)}${productHashMap[key]!!.first.padEnd(16)}$${
        "%.2f".format
          (productHashMap[key]!!.second)
      }"
    )
  }

}

fun newProduct(productHashMap: HashMap<Int, Pair<String, Double>>): HashMap<Int, Pair<String, Double>> {

  //Prompt user for new item information. Check if hashmap already has generated ID
  //If it does contain the ID re-generate it until it is unique
  print("Please enter the description of the new item: ")
  val description = readLine()!!.toString()

  print("Please enter the price for $description: ")
  val price = readLine()!!.toDouble()

  var id = (100..999).random()

  while (productHashMap.contains(id)) {
    id = (100..999).random()
  }

  productHashMap[id] = Pair(description, price)
  println("New record has been added")

  return productHashMap
}

fun deleteProduct(productHashMap: HashMap<Int, Pair<String, Double>>): HashMap<Int, Pair<String, Double>> {

  //Display all products so user may see ID's
  viewProducts(productHashMap)

  //Allow user to select ID then delete it from the hashmap, then return the new hashmap to main()
  print("Please choose a record to delete: ")
  val userChoice = readLine()!!.toInt()

  if (productHashMap.contains(userChoice)) {
    productHashMap.remove(userChoice)
    println("Record $userChoice has been deleted")
  } else if (userChoice !in productHashMap) {
    println("ERROR: Record $userChoice does not exist")
  }

  return productHashMap
}

fun updateProduct(productHashMap: HashMap<Int, Pair<String, Double>>): HashMap<Int, Pair<String, Double>> {

  //Display all products so user may see ID's
  viewProducts(productHashMap)

  //Allow user to enter ID and rewrite values for that ID, then return updated hashmap to main()
  print("Please choose a record to update: ")
  val userChoice = readLine()!!.toInt()

  if (productHashMap.contains(userChoice)) {

    print("Please enter a new description: ")
    val newDescription = readLine()!!.toString()

    print("Please enter a new price: ")
    val newPrice = readLine()!!.toDouble()

    productHashMap[userChoice] = Pair(newDescription, newPrice)
    println("Record $userChoice has been updated")

  } else if (userChoice !in productHashMap) {
    println("ERROR: Record $userChoice does not exist")
  }

  return productHashMap
}

fun getProductTop(productHashMap: HashMap<Int, Pair<String, Double>>) {

  //Find most expensive item in hashmap, then return the item with full information
  var topProduct = Double.MIN_VALUE
  var topProductID = 0

  for ((productID, price) in productHashMap) {
    if (price.second > topProduct) {
      topProduct = price.second
      topProductID = productID
    }
  }

  println("Item     Description     Price")
  println("-----    --------------  ------")
  println(
    "${topProductID.toString().padEnd(9)}${productHashMap[topProductID]!!.first.padEnd(16)}" +
        "$${"%.2f".format(productHashMap[topProductID]!!.second)}\n"
  )
}

fun getProductBottom(productHashMap: HashMap<Int, Pair<String, Double>>) {

  //Find the cheapest item in hashmap, then return the item with full information
  var bottomProduct = Double.MAX_VALUE
  var bottomProductID = 0

  for ((productID, price) in productHashMap) {
    if (price.second < bottomProduct) {
      bottomProduct = price.second
      bottomProductID = productID
    }
  }

  println("Item     Description     Price")
  println("-----    --------------  ------")
  println(
    "${bottomProductID.toString().padEnd(9)}${productHashMap[bottomProductID]!!.first.padEnd(16)}" +
        "$${"%.2f".format(productHashMap[bottomProductID]!!.second)}\n"
  )
}

fun productsSum(productHashMap: HashMap<Int, Pair<String, Double>>) {

  //Add the money values of all items and print it.
  var totalPrice = 0.0
  for (price in productHashMap.keys) {
    totalPrice += productHashMap[price]!!.second
  }

  println("The total of all products is: $$totalPrice")
}