package com.wyattrussellmiller.pig

class leaderboardItem(var winner: String, var score: Int, var date: String) {
    //override toString to display in leaderboard activity
    override fun toString(): String {
        return winner.padEnd(8) + ": ${score.toString().padEnd(3)} $date"
    }
}