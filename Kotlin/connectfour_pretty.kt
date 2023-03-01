/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         06/01/2022
 *  Assignment:   Connect Four
 *  Class Number: CIS 282
 *  Description:  A game where you play against the computer
 *                while trying to get four in a row
 ************************************************************/

fun main(args: Array<String>) {
  var board = Array(8) { Array(8) { "." } }

  do {
    printBoard(board)
    addPiece(board,"X")
    checkWin(board)

    if (checkWin(board)) {
      printBoard(board)
      println("X won!")
    }

    if (!checkWin(board)) {
      printBoard(board)
      addPiece(board, "O")
      checkWin(board)

      if (checkWin(board)) {
        printBoard(board)
        println("O won!")
      }
    }
  } while (!checkWin(board))

}

private fun printBoard(board: Array<Array<String>>) {
  println("╔═══╦═══╦═══╦═══╦═══╦═══╦═══╦═══╗")
  println("║ 1 ║ 2 ║ 3 ║ 4 ║ 5 ║ 6 ║ 7 ║ 8 ║")
  println("╠═══╬═══╬═══╬═══╬═══╬═══╬═══╬═══╣")
  for (row in board.indices) {
    for (col in 0 until board[row].size) {
      print("║ ${board[row][col]} ")
    }
    println("║")
  }
  println("╚═══╩═══╩═══╩═══╩═══╩═══╩═══╩═══╝")
}

private fun addPiece(board: Array<Array<String>>, letter: String): Array<Array<String>> {
  var row = 7
  var colChoice = 0

  if (letter == "X") {
    do {

      print("Please select a column to place your piece: ")
      colChoice = readLine()!!.toInt()

      if (colChoice < 1 || colChoice > 8) {
        println("ERROR: Please select a VALID column to place your piece")
        println()
      } else if (board[0][colChoice - 1] == "X" || board[0][colChoice - 1] == "O") {
        println("ERROR COLUMN FULL: Please select a different column")
        println()
        colChoice = 0
      }
    } while (colChoice < 1 || colChoice > 8)
  }

  if (letter == "O") {
    colChoice = (1..8).random()
  }

  while (board[row][colChoice - 1] == "X" || board[row][colChoice - 1] == "O") {
    row--
  }

  board[row][colChoice - 1] = letter
  return board
}

private fun checkWin(board: Array<Array<String>>): Boolean {
  var retBoolean = false

  if (checkWinHorizontal(board)) {
    retBoolean = true
  } else if (checkWinVertical(board)) {
    retBoolean = true
  } else if (checkWinSlash(board)) {
    retBoolean = true
  } else if (checkWinBackSlash(board)) {
    retBoolean = true
  }

  return retBoolean
}

private fun checkWinHorizontal(board: Array<Array<String>>): Boolean {
  var retBoolean = false
  var charCounterX = 0
  var charCounterO = 0
  var col = 0
  var row = 0

  while (col < board.size && row < board.size) {

    if (board[row][col] == "X") {
      charCounterX++
    } else {
      charCounterX = 0
    }

    if (board[row][col] == "O") {
      charCounterO++
    } else {
      charCounterO = 0
    }

    col++

    if (charCounterX >= 4 || charCounterO >= 4) {
      retBoolean = true
    }
    if (col == 8) {
      row++
      col = 0
      charCounterX = 0
      charCounterO = 0
    }
  }
  return retBoolean
}

private fun checkWinVertical(board: Array<Array<String>>): Boolean {
  var retBoolean = false
  var charCounterX = 0
  var charCounterO = 0
  var col = 0
  var row = 0

  while (row < board.size && col < board.size) {

    if (board[row][col] == "X") {
      charCounterX++
    } else {
      charCounterX = 0
    }

    if (board[row][col] == "O") {
      charCounterO++
    } else {
      charCounterO = 0
    }

    row++
    if (charCounterX >= 4 || charCounterO >= 4) {
      retBoolean = true
    }
    if (row == 8) {
      col++
      row = 0
      charCounterX = 0
      charCounterO = 0
    }
  }
  return retBoolean
}

private fun checkWinSlash(board: Array<Array<String>>): Boolean {
  var retBoolean = false
  var charCounterX = 0
  var charCounterO = 0
  var col = 0
  var row = 7
  var counter1 = 0
  var counter2 = 0

  while (col in 0..7 && row in 0..7) {

    if (board[row][col] == "X") {
      charCounterX++
    } else {
      charCounterX = 0
    }

    if (board[row][col] == "O") {
      charCounterO++
    } else {
      charCounterO = 0
    }

    if (charCounterX >= 4 || charCounterO >= 4) {
      retBoolean = true
    }

    if (row == 0 && counter1 <= 7) {
      charCounterX = 0
      charCounterO = 0
      col = -1
      row = 7 - counter1
      counter1++
    }

    if (row == (0 + counter2) && counter1 == 8) {
      charCounterX = 0
      charCounterO = 0

      col = 0 + counter2
      row = 8
      counter2++
    }
    row--
    col++

  }
  return retBoolean
}

private fun checkWinBackSlash(board: Array<Array<String>>): Boolean {
  var retBoolean = false
  var charCounterX = 0
  var charCounterO = 0
  var col = 7
  var row = 7
  var counter1 = 0
  var counter2 = 0

  while (col in 0..7 && row in 0..7) {

    if (board[row][col] == "X") {
      charCounterX++
    } else {
      charCounterX = 0
    }

    if (board[row][col] == "O") {
      charCounterO++
    } else {
      charCounterO = 0
    }

    if (charCounterX >= 4 || charCounterO >= 4) {
      retBoolean = true
    }

    if (row == 0 && counter1 <= 7) {
      charCounterX = 0
      charCounterO = 0
      col = 8
      row = 8 - counter1
      counter1++
    }

    if (row == (0 + counter2) && counter1 == 8) {
      charCounterX = 0
      charCounterO = 0

      col = 8 - counter2
      row = 8
      counter2++
    }
    row--
    col--

  }
  return retBoolean
}