package org.example
import java.util.UUID

val registeredIDs: MutableList<Int> = mutableListOf(0)

enum class BookCategory {
    NOVELA, POESIA, TEATRO, ACCION, FANTASIA, POLICIACA, TERROR, CIENCIA_FICCION;
}

fun showCategories() {
    var index = 1
    for (category in BookCategory.values()) {
        println("$index. $category")
        index++
    }
}

fun requestInt(input: String): Int {
    var temp = input

    while (temp.toIntOrNull() == null) {
        println("Please, enter a valid choice.")
        temp = readln()
    }
    return temp.toInt()
}

fun checkBookID(books: MutableList<Book>, inputID: String): Boolean {
    for (i in books) {
        if (i.ID.toString() == inputID)
            return true
    }
    return false
}

fun checkUserID(partners: MutableList<Partner>, inputID: Int): Boolean {
    for (i in partners) {
        if (i.ID == inputID)
            return true
    }
    return false
}

fun bookByID(bookID:String, books: MutableList<Book>): Book? {
    for (book in books) {
        if(book.ID.toString() == bookID)
            return book
    }
    return null
}

fun partnerByID(partnerID: Int, partners: MutableList<Partner>): Partner? {
    for (partner in partners) {
        if (partner.ID == partnerID)
            return partner
    }
    return null
}

class Book constructor(_title: String, _author: String,_category: BookCategory) {
    var title = _title
    var author = _author
    val ID = UUID.randomUUID()
    val category = _category.toString()
    var available = true

    init {
        if (title.length < 4)
            title = "Unknown Title"
        if(author.length < 3)
            title = "Unknown Author"
    }

    fun printInfo() {
        println("Title: ${title.uppercase()}")
        println("Author: ${author.uppercase()}")
        println("Category: ${category.uppercase()}")
        println("Is Available: $available")
        println("Book ID: $ID")
    }
}

class Partner constructor(_name: String, _lastname: String) {
    var name = _name
    var lastname = _lastname
    val ID = registeredIDs.last() + 1
    val lendedBooks: MutableList<Book> = mutableListOf()

    init {
        if(name.length < 3)
            name = "Unknown"
        if(lastname.length < 3)
            lastname = "Unknown"
    }
}

class Library {
    val books: MutableList<Book> = mutableListOf()
    val partners: MutableList<Partner> = mutableListOf()

    fun bookRegister() {
        print("Write the title of the book: ")
        val titleBook = readln()
        print("Write the author: ")
        val authorBook = readln()
        var choice: Int
        var category: BookCategory
        do {
            println("Choose the category: ")
            showCategories()
            choice = requestInt(readln())
            when (choice) {
                1 -> {
                    category = BookCategory.NOVELA
                    break
                }
                2 -> {
                    category = BookCategory.POESIA
                    break
                }
                3 -> {
                    category = BookCategory.TEATRO
                    break
                }
                4 -> {
                    category = BookCategory.ACCION
                    break
                }
                5 -> {
                    category = BookCategory.FANTASIA
                    break
                }
                6 -> {
                    category = BookCategory.POLICIACA
                    break
                }
                7 -> {
                    category = BookCategory.TERROR
                    break
                }
                8 -> {
                    category = BookCategory.CIENCIA_FICCION
                    break
                }
                else -> {
                    println("Invalid category")
                    continue
                }
            }
        } while (true)
        val anewBook = Book(titleBook, authorBook, category)
        books.add(anewBook)
        println("Book added succesfully")
        println(books)
    }

    fun partnerRegister() {
        print("Write partner name: ")
        val partnerName = readln()
        print("Write partner lastname: ")
        val partnerUsername = readln()
        val newPartner = Partner(partnerName, partnerUsername)
        registeredIDs.add(newPartner.ID)
        partners.add(newPartner)
        println(partners)
    }

    fun lendABook () {
        print("Introduce the book ID: ")
        val bookID = readln()
        print("Introduce the user ID: ")
        val partnerID = requestInt(readln())
        if (!checkBookID(books, bookID)) {
            println("Book not found.")
        }
        else if (!checkUserID(partners, partnerID))
            println("User not found.")
        else {
            val selectedBook = bookByID(bookID, books)
            val selectedPartner = partnerByID(partnerID, partners)
            if(selectedBook!!.available) {
                selectedBook.available = false
                selectedPartner!!.lendedBooks.add(selectedBook)
                println("${selectedPartner.name} ${selectedPartner.lastname} pick the book ${selectedBook.title}")
            } else
                println("The book is not available.")
        }
    }

    fun returnABook () {
        print("Introduce the book ID: ")
        val bookID = readln()
        print("Introduce the user ID: ")
        val partnerID = requestInt(readln())
        if (!checkBookID(books, bookID)) {
            println("Book not found.")
        }
        else if (!checkUserID(partners, partnerID))
            println("User not found.")
        else {
            val bookToReturn = bookByID(bookID, books)
            val partner = partnerByID(partnerID, partners)
            if(!bookToReturn!!.available) {
                partner!!.lendedBooks.remove(bookToReturn)
                bookToReturn.available = true
                println("${partner.name} ${partner.lastname} returned the book ${bookToReturn.title}")
            }
        }
    }

    fun findBookByTitle() {
        print("Write the title of the book: ")
        val inputTitle = readln()
        for (book in books) {
            if (book.title.contains(inputTitle)) {
                println(book.printInfo())
                println("---------------------------------")
            }
        }
    }


}

fun main() {
    val libreria = Library()
    libreria.bookRegister()
    libreria.bookRegister()
    libreria.findBookByTitle()
}
