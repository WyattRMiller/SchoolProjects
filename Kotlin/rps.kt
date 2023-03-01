/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         4/30/2022
 *  Assignment:   Rock Paper Scissors
 *  Class Number: CIS 282
 *  Description:  A program that takes user input and compares
 *                it to a random number, depending on the result
 *                the computer will tell you if you won, lost, or tied.
 ************************************************************/

fun main(args: Array<String>) {
  //Explain program to user
  println("Welcome to Rock-Paper-Scissors!")
  println("This is a simple game of choosing one of either Rock, Paper, or Scissors against the computer's choice")
  println("The rules are:")
  println("  Rock beats Scissors")
  println("  Scissors beats paper")
  println("  Paper beats Rock")
  println()

  //Create counter variables globally, so they don't reset each loop
  var wins = 0
  var ties = 0
  var loses = 0

  do {
    //Ask if they would like to play
    //IF yes continue, IF no end code, ELSE ask again
    print("Would you like to play? (Y or N) ")
    var enterGame = readLine()!!.toString()

    if (enterGame == "N" || enterGame == "n") {
      println("Sorry you didn't want to play with me!")
    }
    if (enterGame != "Y" && enterGame != "y" && enterGame != "N" && enterGame != "n") {
      println()
      println("Please enter a valid selection")
      println()
    }

    if (enterGame == "Y" || enterGame == "y") {

      do {
        //Ask user if they would like to select rock, paper, scissors, or quit.
        //IF rock, paper, or scissors continue, ELSE quit
        println()
        println("1. Rock")
        println("2. Paper")
        println("3. Scissors")
        println("4. Quit")
        print("Enter your choice: ")

        //Store user input into variable
        var userChoice = readLine()!!.toInt()

        //Add linebreak for easier reading
        println()

        //Generate random number for computer
        var botChoice = (1..3).random()

        //Create empty sting variables for messages dependent on userchoice and botchoice
        var userText = ""
        var botText = ""

        when (userChoice) {
          1 -> userText = "You chose: rock"
          2 -> userText = "You chose: paper"
          3 -> userText = "You chose: scissors"
        }

        when (botChoice) {
          1 -> botText = "The computer chose: rock"
          2 -> botText = "The computer chose: paper"
          3 -> botText = "The computer chose: scissors"
        }

        //Compare user input and random number, deciding if the user wins, loses, or ties.
        //Track wins, loses, and ties
        if (userChoice == botChoice) {
          println(botText)
          println(userText)
          println("You tied!")
          ties++
        } else if (userChoice == 1 && botChoice == 2) {
          println(botText)
          println(userText)
          println("You lost!")
          loses++
        } else if (userChoice == 1 && botChoice == 3) {
          println(botText)
          println(userText)
          println("You won!")
          wins++
        } else if (userChoice == 2 && botChoice == 3) {
          println(botText)
          println(userText)
          println("You lost!")
          loses++
        } else if (userChoice == 2 && botChoice == 1) {
          println(botText)
          println(userText)
          println("You won!")
          wins++
        } else if (userChoice == 3 && botChoice == 1) {
          println(botText)
          println(userText)
          println("You lost!")
          loses++
        } else if (userChoice == 3 && botChoice == 2) {
          println(botText)
          println(userText)
          println("You won!")
          wins++
        } else if (userChoice == 4) {
          println("Thank you for playing!")
        } else {
          println("ERROR: Please enter a valid selection")
        }

        //Display wins, loses, and ties
        println()
        println("---STATS---")
        println("Wins:  $wins")
        println("Ties:  $ties")
        println("Loses: $loses")

        //Loop code
      } while (userChoice != 4)
      enterGame = "N"

    }

  } while (enterGame != "N" && enterGame != "n")

}