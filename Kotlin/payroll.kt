/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         4/15/2022
 *  Assignment:   Payroll
 *  Class Number: CIS 282
 *  Description:  A program that takes information from the user,
 *                performs math on the information, then returns results.
 ************************************************************/

fun main(args: Array<String>) {

  //Ask the user for their name.
  //Take user input and store it in the "name" variable.
  print("Enter Employee's name: ")
  val name = readLine()!!

  //Ask user for the amount of hours they worked this week.
  //Take user input and store it in the "hoursWorked" variable.
  print("Enter number of hours worked in a week: ")
  val hoursWorked = readLine()!!.toDouble()

  //Ask user for the hourly pay rate.
  //Take user input and store it in the "hourPay" variable.
  print("Enter hourly pay rate: ")
  val hourPay = readLine()!!.toDouble()

  //Ask user for federal tax.
  //Take user input and store it in "fedTax" variable.
  print("Enter federal tax withholding rate: ")
  val fedTax = readLine()!!.toDouble()

  //Ask user for state tax.
  //Take user input and store it in the "stateTax" variable.
  print("Enter state tax withholding rate: ")
  val stateTax = readLine()!!.toDouble()


  //Create totalPay by multiplying hoursWorked by hourPay.
  //Create totalFed.
  //Create totalState.
  //Create totalTax.
  //Create fedTax in %## format.
  //Create stateTax in %## format.
  //Create new total pay.
  val totalPay = (hoursWorked * hourPay)
  val totalFed = (totalPay * fedTax)
  val totalState = (totalPay * stateTax)
  val totalTax = (totalFed + totalState)
  val fedPercent = (fedTax * 100)
  val statePercent = (stateTax * 100)
  val netPay = (totalPay - totalTax)


  //Print the users name.
  //Print hours worked.
  //Print the hourly pay rate.
  //Print total pay.
  println("\nEmployee Name:  $name")
  println("Hours Worked:  $hoursWorked")
  println("Pay Rate:  $" + "%.2f".format(hourPay))
  println("Gross Pay:  $" + "%.2f".format(totalPay))


  //Add deduction information.
  //Display federal tax info.
  //Display state tax info.
  //Display print total tax deduction.
  //Print new total pay.
  println("\nDeductions:")
  println("   Federal Withholding ($fedPercent%): $" + "%.2f".format(totalFed))
  println("   State Withholding ($statePercent%): $" + "%.2f".format(totalState))
  println("   Total Deduction:  " + "%.2f".format(totalTax))
  println("Net Pay: " + "%.2f".format(netPay))
}