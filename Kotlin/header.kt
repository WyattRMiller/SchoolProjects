/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         April 8, 2022
 *  Assignment:   Headers
 *  Class Number: CIS 282
 *  Description:  A quick program to create a custom header for later  assignments
 ************************************************************/

fun main(args: Array<String>) {

  print("Please enter your name: ")
  var name = readLine()

  print("Please enter the date: ")
  var date = readLine()

  print("Please enter the Assignment: ")
  var assignment = readLine()

  print("Please enter the class number: ")
  var classNumber = readLine()

  print("Please enter the description: ")
  var description = readLine()

  println("/************************************************************")
  println(" *  Name:         $name")
  println(" *  Date:         $date")
  println(" *  Assignment:   $assignment")
  println(" *  Class Number: $classNumber")
  println(" *  Description:  $description")
  println("************************************************************/")
}