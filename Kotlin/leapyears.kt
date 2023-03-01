fun main(args: Array<String>) {
  print("Enter start year: ")
  var year0 = readLine()!!.toInt()
  print("Enter end year: ")
  var year1 = readLine()!!.toInt()
  println()
  println(leapYear(year0, year1))
}

fun leapYear(year0: Int, year1: Int): String {

  var lYears = intArrayOf()
  var rText = ""
  var bool = true

  for (i in year0..year1){
    if (i % 4 == 0 && i % 100 != 0) {
      lYears += i
    }
    if (i % 400 == 0) {
      lYears += i
    }
  }

  for (i in lYears.indices) {
    if (bool){
      rText += lYears[i]
      bool = false
    } else {
      rText += ", ${lYears[i]}"
    }
  }

  return rText
}