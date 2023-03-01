/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         5/5/2022
 *  Assignment:   Histogram
 *  Class Number: CIS 282
 *  Description:  Design and implement an application that creates
 *                a histogram that allows you to visually inspect
 *                the frequency distribution of a set of values.
 ************************************************************/

fun main(args: Array<String>) {
  //Create random number array
  //Add random numbers into the array
  var numbers = intArrayOf()
  repeat(200) { numbers += (1..100).random() } //For some reason repeating 200 times only gives 199 results


  //Sort random numbers into variables
  var num0 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 1..10) {
      num0++
    }
  }

  var num1 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 11..20) {
      num1++
    }
  }

  var num2 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 21..30) {
      num2++
    }
  }

  var num3 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 31..40) {
      num3++
    }
  }

  var num4 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 41..50) {
      num4++
    }
  }

  var num5 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 51..60) {
      num5++
    }
  }

  var num6 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 61..70) {
      num6++
    }
  }

  var num7 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 71..80) {
      num7++
    }
  }

  var num8 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 81..90) {
      num8++
    }
  }

  var num9 = 0
  for (number in 1 until numbers.size) {
    if (numbers[number] in 91..100) {
      num9++
    }
  }


  //Create chart
  //Print out results
  println("Range        # Found       Chart")
  println("--------     ----------    -------------------------------------------")

  print(" 1 -  10      |  ${num0.toString().padStart(2)}  |     ")
  print("*".repeat(num0))
  println()

  print("11 -  20      |  ${num1.toString().padStart(2)}  |     ")
  print("*".repeat(num1))
  println()

  print("21 -  30      |  ${num2.toString().padStart(2)}  |     ")
  print("*".repeat(num2))
  println()

  print("31 -  40      |  ${num3.toString().padStart(2)}  |     ")
  print("*".repeat(num3))
  println()

  print("41 -  50      |  ${num4.toString().padStart(2)}  |     ")
  print("*".repeat(num4))
  println()

  print("51 -  60      |  ${num5.toString().padStart(2)}  |     ")
  print("*".repeat(num5))
  println()

  print("61 -  70      |  ${num6.toString().padStart(2)}  |     ")
  print("*".repeat(num6))
  println()

  print("71 -  80      |  ${num7.toString().padStart(2)}  |     ")
  print("*".repeat(num7))
  println()

  print("81 -  90      |  ${num8.toString().padStart(2)}  |     ")
  print("*".repeat(num8))
  println()

  print("91 - 100      |  ${num9.toString().padStart(2)}  |     ")
  print("*".repeat(num9))
  println()

}