fun main(args: Array<String>) {

  print("Please enter the number of quarters: ")
  val quarters = readLine()!!.toInt()

  print("Please enter the number of dimes: ")
  val dimes = readLine()!!.toInt()

  print("Please enter the number of nickels: ")
  val nickels = readLine()!!.toInt()

  print("Please enter the number of pennies: ")
  val pennies = readLine()!!.toInt()

  coinInfo(quarters, dimes, nickels, pennies)


}

fun coinInfo(quarters: Int, dimes: Int, nickels: Int, pennies: Int){

  var qCents = 0.0
  var dCents = 0.0
  var nCents = 0.0
  var pCents = 0.0

  for (i in 1..quarters) {
    qCents += 0.25
  }

  for (i in 1..dimes) {
    dCents += 0.10
  }

  for (i in 1..nickels) {
    nCents += 0.05
  }

  for (i in 1..pennies) {
    pCents += 0.01
  }

  val total = qCents + dCents + nCents + pCents

  return println("You have $quarters quarters, $dimes dimes, $nickels nickels, and $pennies pennies for a total of $" +
      "$total")
}