fun main() {
    println("Enter the number of rows")
    val rows = readln().toInt() + 1
    println("Enter the number of seats in each row")
    val seatPerRow = readln().toInt() + 1

    var seatInRow: Int
    var seatNumber: Int
    var ticket = 0
    var totalEarnings = 0

    val list: MutableList<MutableList<String>> = cinema(rows, seatPerRow)
    val totalSeats = totalSeatNumber(list)

    while(true){

        println("Cinema:")
        displaySeats(list)

        loop@ while(true) {
//  commands
            println("1. Show the seats")
            println("2. Buy a ticket")
            println("3. Statistics")
            println("0. Exit")

            val command = readln().toInt()

            if (command == 0) {
                return
            } else if (command == 1) {
                println("Cinema:")
                displaySeats(list)
                break@loop
            } else if (command == 2) {
                println("Enter a row number:")
                seatInRow = readln().toInt()
                println("Enter a seat number in that row:")
                seatNumber = readln().toInt()
                while (seatInRow+1 > rows || seatNumber+1 > seatPerRow){
                    println("Wrong input!")
                    println("Enter a row number:")
                    seatInRow = readln().toInt()
                    println("Enter a seat number in that row:")
                    seatNumber = readln().toInt()
                }
                while(list[seatInRow][seatNumber] == "B"){
                    println("That ticket has already been purchased")
                    println("Enter a row number:")
                    seatInRow = readln().toInt()
                    println("Enter a seat number in that row:")
                    seatNumber = readln().toInt()
                }



                ticket = ticketPrice(seatInRow, seatNumber, totalSeats, rows, seatPerRow)
                println("Ticket price: \$$ticket")
                totalEarnings += ticket

                println("Cinema:")
                seatingCinema(seatInRow, seatNumber, list)
            } else if (command == 3) {
                var boughtTickets = 0
                for (i in 1 until rows){
                    for(y in 1 until seatPerRow)
                        if(list[i][y] == "B"){
                            boughtTickets++
                        }
                }
                println("Number of purchased tickets: $boughtTickets")

                earning(totalSeats, rows, seatPerRow)
                val percentage: Double = (100 / totalSeats.toDouble() * boughtTickets).toDouble()

                println("Percentage: ${"%.2f".format(percentage)}%")


                println("Current income: \$$totalEarnings")
                println("Total income: \$${earning(totalSeats, rows, seatPerRow)}")
            }
        }
    }
}

fun cinema(rows: Int, seats: Int): MutableList<MutableList<String>> {
//  creates mutList 2d (rows,columns), sets S as element in list
    val list = MutableList(rows) { MutableList(seats) { "S" } }
//  first row to count numbers
    for (i in 0..list[0].lastIndex) list[0].set(i, i.toString())
//  set first list[0][)] to empty space string
    list[0].set(0, " ")
//  first column of all rows set to count numbers
    for (i in 1..list.lastIndex) list[i].set(0, i.toString())
    return list
}

//  display seats in 2d array
fun displaySeats(list: MutableList<MutableList<String>>) {
    for (i in 0..list.lastIndex) println(list[i].joinToString(" "))
}

// count total seat number
fun totalSeatNumber(list: MutableList<MutableList<String>>): Int {
    var seatNumber = 0
    for (i in 0 until list.size - 1) {
        seatNumber += list[i].size-1
    }
    return seatNumber
}

// calculate earnings
fun earning(totalSeats: Int, rows: Int, seatPerRow: Int): Int {
    val frontHalfSeats: Int
    val backHalfSeats: Int

    if (totalSeats <= 60) {
        return totalSeats * 10
    } else {
        frontHalfSeats = ((rows-1) / 2) * (seatPerRow-1) * 10
        backHalfSeats = (totalSeats - ((rows-1) / 2 * (seatPerRow-1))) * 8

    }
    return frontHalfSeats + backHalfSeats
}

//calculate specific ticket price
fun ticketPrice (seatInRow: Int, seatNumber: Int, totalSeats: Int, rows: Int, seatPerRow: Int): Int{
    val frontHalfRows = (rows-1) / 2
    val backHalfRow = (rows-1) - frontHalfRows

    if (totalSeats <= 60) {
        return 10
    } else if (seatInRow <= frontHalfRows) {
        return 10
    } else return 8
}

fun seatingCinema(seatInRow: Int, seatNumber: Int, list: MutableList<MutableList<String>> ){
    list[seatInRow].set(seatNumber, "B")
    for (i in 0..list.lastIndex) println(list[i].joinToString(" "))

}