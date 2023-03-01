import java.io.File

/************************************************************
 *  Name:         Wyatt Miller
 *  Date:         10/9/2022
 *  Assignment:   Project: Books Class.
 *  Class Number: 283
 *  Description:  An application used for managing books
 ************************************************************/

class Book(var title: String, var author: String, var publicationYear: Int, var numberOfPages: Int, var isbn: String) {

    override fun toString(): String {
        val name = title + " ".repeat(30)

        return ("${name.take(30)} ${author.padEnd(17)} ${publicationYear.toString().padEnd(6)} " +
                "${numberOfPages.toString().padStart(3)} ${isbn.padEnd(13)} https://www.biblio.com/$isbn\n")


    }

    fun toTab(): String {
        return ("$title\t$author\t$publicationYear\t$numberOfPages\t$isbn")
    }


}

fun main() {

    val books = mutableListOf<Book>()
    val txtFile = "src/main/kotlin/books.txt"
    var inputFD = File(txtFile).forEachLine {
        val part = it.split("\t")
        books.add(Book(part[0], part[1], part[2].toInt(), part[3].toInt(), part[4]))
    }

    val menuArray = arrayOf(
        " View all Books",
        " Add book",
        " Update book",
        " Delete book",
        " View book with most pages",
        " View book with least pages",
        " View books with pages greater than or equal to 200",
        " View books with pages less than 200",
        " View books with pages between 50-300 inclusive",
        "Exit\n"
    )

    val prompt = "Please enter your selection: "
    val quitOption = menuArray.size
    var userChoice = 0

    while (userChoice != quitOption) {
        userChoice = getMenu(menuArray, prompt)

        when (userChoice) {
            1 -> println(viewBook(false, books))
            2 -> {
                addOrUpdate(true, books) + println("Book has been added.")
            }

            3 -> {
                addOrUpdate(false, books) + println("Book has been updated.")
            }

            4 -> {
                deleteBook(books) + println("Book has been deleted.")
            }

            5 -> println(mostPages(books))
            6 -> println(leastPages(books))
            7 -> println(over200(books))
            8 -> println(under200(books))
            9 -> println(inbetween50and300(books))
            else -> {
                if (userChoice != quitOption) {
                    println("ERROR: Menu option entered does not exist")
                }
            }
        }

    }

    val fd = File(txtFile).printWriter()
    var c = 0
    for (i in books) {
        fd.println(
            Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toTab()
        )
        c++
    }

    fd.close()

}

fun viewBook(bool: Boolean, books: MutableList<Book>): String {

    var str = ""
    var c = 0

    for (i in books) {
        if (bool) {
            str += "${c + 1}. "
            str += Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toString()
        } else {
            str += Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toString()
        }
        c++
    }

    return "Geeks Publishing, Inc.\n" +
            "Name                           Author          Pub Yr Pages ISBN          URL\n" +
            "------------------------------ --------------- ------ ----- ------------- -------\n" +
            str
}

fun addOrUpdate(bool: Boolean, books: MutableList<Book>): MutableList<Book> {

    var userChoice = ""
    if (!bool) {
        println(viewBook(true, books))
        print("Please enter your selection: ")
        userChoice = readLine()!!

        while (userChoice == "") {
            print("Please enter a valid choice: ")
            userChoice = readLine()!!
        }
    }



    print("Enter title: ")
    var title = readLine()
    while (title == "") {
        print("Please enter a valid choice: ")
        title = readLine()
    }

    print("Enter author: ")
    var author = readLine()
    while (author == "") {
        print("Please enter a valid choice: ")
        author = readLine()
    }

    print("Enter Year: ")
    var publicationYear = readLine()
    while (publicationYear == "" || publicationYear!!.toInt() < 1600) {
        print("Please enter a valid choice, year must be greater then 1600: ")
        publicationYear = readLine()
    }

    print("Enter number of pages: ")
    var numberOfPages = readLine()
    while (numberOfPages == "" || numberOfPages!!.toInt() < 1) {
        print("Please enter a valid choice, must have more then one page: ")
        numberOfPages = readLine()
    }

    print("Enter isbn: ")
    var isbn = readLine()
    while (isbn == "") {
        print("Please enter a valid choice: ")
        isbn = readLine()
    }

    if (bool) {
        books.add(
            Book(
                title!!.toString(),
                author!!.toString(),
                publicationYear.toInt(),
                numberOfPages.toInt(),
                isbn!!.toString()
            )
        )
    } else {
        books[userChoice.toInt() - 1] = (Book(
            title!!.toString(),
            author!!.toString(),
            publicationYear.toInt(),
            numberOfPages.toInt(),
            isbn!!.toString()
        ))
    }

    return books
}

fun deleteBook(books: MutableList<Book>): MutableList<Book> {
    println(viewBook(true, books))

    print("Please enter your selection: ")
    var c = readLine()

    while (c == "") {
        print("Please enter a valid choice: ")
        c = readLine()
    }

    books.remove(books[c!!.toInt() - 1])

    return books
}

fun mostPages(books: MutableList<Book>): String {

    var str = ""
    var c = 0
    var most = 0
    for (i in books) {
        if (books[c].numberOfPages > most) {
            most = books[c].numberOfPages
            str = Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toString()
        }
        c++
    }

    return "Geeks Publishing, Inc.\n" +
            "Name                           Author          Pub Yr Pages ISBN          URL\n" +
            "------------------------------ --------------- ------ ----- ------------- -------\n" +
            str
}

fun leastPages(books: MutableList<Book>): String {

    var str = ""
    var c = 0
    var least = Int.MAX_VALUE
    for (i in books) {
        if (books[c].numberOfPages < least) {
            least = books[c].numberOfPages
            str = Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toString()
        }
        c++
    }

    return "Geeks Publishing, Inc.\n" +
            "Name                           Author          Pub Yr Pages ISBN          URL\n" +
            "------------------------------ --------------- ------ ----- ------------- -------\n" +
            str
}

fun over200(books: MutableList<Book>): String {

    var str = ""
    var c = 0
    for (i in books) {
        if (books[c].numberOfPages >= 200) {
            str += Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toString()
        }
        c++
    }

    return "Geeks Publishing, Inc.\n" +
            "Name                           Author          Pub Yr Pages ISBN          URL\n" +
            "------------------------------ --------------- ------ ----- ------------- -------\n" +
            str
}

fun under200(books: MutableList<Book>): String {

    var str = ""
    var c = 0
    for (i in books) {
        if (books[c].numberOfPages < 200) {
            str += Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toString()
        }
        c++
    }

    return "Geeks Publishing, Inc.\n" +
            "Name                           Author          Pub Yr Pages ISBN          URL\n" +
            "------------------------------ --------------- ------ ----- ------------- -------\n" +
            str
}

fun inbetween50and300(books: MutableList<Book>): String {

    var str = ""
    var c = 0
    for (i in books) {
        if (books[c].numberOfPages in 50..300) {
            str += Book(
                books[c].title,
                books[c].author,
                books[c].publicationYear,
                books[c].numberOfPages,
                books[c].isbn
            ).toString()
        }
        c++
    }

    return "Geeks Publishing, Inc.\n" +
            "Name                           Author          Pub Yr Pages ISBN          URL\n" +
            "------------------------------ --------------- ------ ----- ------------- -------\n" +
            str
}

fun getMenu(menuArray: Array<String>, prompt: String): Int {

    for ((index, item) in menuArray.withIndex()) {
        println("${index + 1}. $item")
    }

    print(prompt)
    var retChoice = readLine()

    while (retChoice == "") {
        print("Please enter a valid choice: ")
        retChoice = readLine()
    }

    return retChoice!!.toInt()

}