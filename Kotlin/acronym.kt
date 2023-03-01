fun main(args: Array<String>){
  print("Please enter what you would like to get turned into an acronym: ")
  var phrase = readLine()!!.toString()
  println(acronym(phrase))
}

fun acronym(phrase: String): String {

  var a = ""
  var first = true
  var bool = false

  for (i in phrase) {

    if (first){
      a += i.uppercase()
      first = false
    }

    if (i.toString() == " "){
      bool = true
    }

    if (bool && i.toString() != " "){
      a += i.uppercase()
      bool = false
    }

  }

  return a
}