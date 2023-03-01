import java.io.File

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         9/25/2022
 *  Assignment:   Regular Expression
 *  Class Number: 283
 *  Description:  Takes user input to search through file
 ************************************************************/

fun main(args: Array<String>) {
    //add file
    val txtFile = "src/main/kotlin/Enrollment.txt"

    //loop until user exits
do {
    var bool = false

    //get user input
    print("Enter Department: ")
    val department = readLine()!!.toString()

    if (department != "EXIT") {

        print("Enter Class Number: ")
        val departmentnum = readLine()!!.toString()

        //pattern variables to see if data matches, as well to check if class column is empty
        val departmentpattern = """\A.{10}$department""".toRegex()
        val departmentnumpattern = """\A.{16}$departmentnum""".toRegex()
        val checkLine = """\A.{10} """.toRegex()

        //loop through file line by line searching for matches
        var inputFD = File(txtFile).forEachLine {
            //check if additional lines need to be printed
            if (bool && it.contains(checkLine)) {
                println(it)
            } else {
                bool = false
            }
            //check if user input matches line
            if (it.contains(departmentpattern) && it.contains(departmentnumpattern)) {
                println(it)
                bool = true
            }
        }
    }
    //Exit loop if user input = EXIT while asking for department
} while (department != "EXIT")
}