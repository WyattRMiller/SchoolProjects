/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         4/23/2022
 *  Assignment:   Right Triangle
 *  Class Number: CIS 282
 *  Description:  A program that takes user input and returns a right triangle.
 *                The size of the triangle depends on the user input.
 ************************************************************/

fun main(args: Array<String>) {

  //Ask user how large they would like their triangle.
  //Store user input in the "size" variable.
  print("How big do you want your triangles? ")
  val size = readLine()!!.toInt()


  //Create count variable for the loops.
  var count = 0


  //Create if/else statement for top of triangle. If true print top, if false do nothing.
  if (size >= 1) {
    println("*")
  }

  //Create while loop to control the amount of carriage returns and sides of the triangle.
  //Create for loop inside the while loop to control spacing.
  while (count <= (size - 3)) {
    print("*") //Print left side of triangle.

    for (i in 1..count) {
      print(" ") //Print spacing, the count variable increments, so the spaces do as well.
    }

    println("*") //Print the right side of the triangle and start a new line.
    count++ //Increment count variable.
  }

  //Print base of triangle.
  for (i in 1..size) {
    print("*")
  }

  //Start new line for next triangle.
  println()

  //Create loop for number triangle.
  for (i in 1..size) { //Create outer loop for carriage returns.
    for (ii in 1..i) { //Create inner loop for printing numbers.
      print("$ii") //Print numbers.
    }
    println() //Start new line.
  }

}
