import java.io.File

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         12/3/2022
 *  Assignment:   Word Search
 *  Class Number: CIS 283
 *  Description:  A program that takes a list of words, randomly places them in a grid, and creates a puzzle.
 ************************************************************/

class Puzzle(var fileName: String) {
    val board = Array(45) { Array(45) { "." } }
    var puzzleKey = ""
    var puzzle = ""
    var letters = ArrayList<String>()


    fun createPuzzle() {
        placeWords()
        puzzleKey = displayPuzzle()
        fillPuzzle()
        puzzle = displayPuzzle()
    }

    private fun placeWords() {
        var row = (0..44).random()
        var col = (0..44).random()
        var direction = (0..7).random()

        var placement: ArrayList<Pair<Int, Int>>
        val words = ArrayList<String>()

        var inc1 = 0
        var inc2 = 0

        var inputFD = File(fileName).forEachLine {
            var word = ""
            for (i in it) {
                if (i.toString() != " ") {
                    word += i.toString().uppercase()
                    if (!letters.contains(i.toString().uppercase())) {
                        letters += i.toString().uppercase()
                    }
                }
            }
            words.add(word)
        }

        words.sortBy { words -> words.length }
        words.reverse()

        while (inc1 < 45) {

            if (checkPlacement(row, col, direction, words[inc1])) {

                placement = direction(row, col, direction, words[inc1])

                for (i in words[inc1]) {
                    board[placement[inc2].first][placement[inc2].second] = i.toString()
                    inc2++
                }

                inc2 = 0
                inc1++

            }
            row = (0..44).random()
            col = (0..44).random()
            direction = (0..7).random()
        }
    }

    private fun checkPlacement(row: Int, col: Int, direction: Int, word: String): Boolean {
        var retBool = false
        val checkNums = direction(row, col, direction, word)
        var increment = 0
        var firstCheck = false
        var secondCheck = false

        when (direction) {
            0 -> {
                if (row > word.length) {
                    firstCheck = true
                }
            }

            1 -> {
                if (row > word.length && col < 44 - word.length) {
                    firstCheck = true
                }
            }

            2 -> {
                if (col < 44 - word.length) {
                    firstCheck = true
                }
            }

            3 -> {
                if (row < 44 - word.length && col < 44 - word.length) {
                    firstCheck = true
                }
            }

            4 -> {
                if (row < 44 - word.length) {
                    firstCheck = true
                }
            }

            5 -> {
                if (row < 44 - word.length && col > word.length) {
                    firstCheck = true
                }
            }

            6 -> {
                if (col > word.length) {
                    firstCheck = true
                }
            }

            7 -> {
                if (row > word.length && col > word.length) {
                    firstCheck = true
                }
            }
        }

        if (firstCheck) {
            for (i in word.indices) {
                secondCheck =
                    (board[checkNums[increment].first][checkNums[increment].second] == "." ||
                            board[checkNums[increment].first][checkNums[increment].second] == word[increment].toString())
                increment++
            }
        }

        if (firstCheck && secondCheck) {
            retBool = true
        }

        return retBool
    }

    private fun fillPuzzle() {
        var inc1 = 0
        var inc2 = 0

        while (inc2 < 45) {
            while (inc1 < 45) {
                if (board[inc1][inc2] == ".") {
                    board[inc1][inc2] = letters.random()
                }
                inc1++
            }
            inc1 = 0
            inc2++
        }
    }

    fun displayWordList(): String {
        var retString = "Find the following 45 words:\n\n" + " ".repeat(20)
        var lineCtr = 0
        val parts = ArrayList<String>()

        var inputFD = File(fileName).forEachLine {
            parts.add(it)
        }

        for (i in parts) {
            retString += parts[lineCtr].padEnd(20)
            lineCtr++
            if (lineCtr % 3 == 0) {
                retString += "\n" + " ".repeat(20)
            }
        }
        return retString
    }

    fun displayPuzzle(): String {
        var retString = ""
        for (row in board.indices) {
            for (col in 0 until board[row].size) {
                retString += ("${board[row][col]} ")
            }
            retString += "\n"
        }
        return retString
    }

    fun displayPuzzleKey(): String {
        return puzzleKey
    }

    private fun direction(row: Int, col: Int, direction: Int, word: String): ArrayList<Pair<Int, Int>> {
        val list = ArrayList<Pair<Int, Int>>()
        var inc = 0

        for (i in word) {
            when (direction) {
                0 -> list.add(Pair((row - inc), col))
                1 -> list.add(Pair((row - inc), (col + inc)))
                2 -> list.add(Pair(row, (col + inc)))
                3 -> list.add(Pair((row + inc), (col + inc)))
                4 -> list.add(Pair((row + inc), col))
                5 -> list.add(Pair((row + inc), (col - inc)))
                6 -> list.add(Pair(row, (col - inc)))
                7 -> list.add(Pair((row - inc), (col - inc)))
            }
            inc++
        }
        return list
    }

}

fun main() {
    val puzzle = Puzzle("src/main/kotlin/words.txt")

    puzzle.createPuzzle()
    println(puzzle.displayPuzzleKey())
    println(puzzle.displayWordList())
    println(puzzle.displayPuzzle())
    println(puzzle.displayWordList())
    puzzleTOFile(puzzle)
    puzzleKeyToFile(puzzle)
}

fun puzzleTOFile(puzzle: Puzzle) {
    val fd = File("src/main/kotlin/puzzle.txt").printWriter()
    fd.println(puzzle.puzzle)
    fd.println(puzzle.displayWordList())
    fd.close()
}

fun puzzleKeyToFile(puzzle: Puzzle) {
    val fd = File("src/main/kotlin/puzzleKey.txt").printWriter()
    fd.println(puzzle.puzzleKey)
    fd.println(puzzle.displayWordList())
    fd.close()
}