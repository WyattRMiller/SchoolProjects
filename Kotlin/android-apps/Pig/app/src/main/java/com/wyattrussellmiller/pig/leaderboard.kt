package com.wyattrussellmiller.pig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

//Create variable for using leaderboard.txt file
const val LEADERBOARD_RECORDS_FILE = "leaderboardrecords.txt"

class leaderboard : AppCompatActivity() {
    //create initial leaderboard variables to use later
    var leaderboardList = ArrayList<leaderboardItem>()
    private lateinit var leaderboardListAdapter: leaderboardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        //Set up leader board recycle view
        val lbRecyclerView: RecyclerView = findViewById(R.id.leaderboard)
        leaderboardListAdapter = leaderboardAdapter(leaderboardList)

        lbRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        lbRecyclerView.itemAnimator = DefaultItemAnimator()
        lbRecyclerView.adapter = leaderboardListAdapter

        //check if you need to write to the file
        val extras = intent.extras
        if (extras != null) {
            toCSV(extras.getString("winner")!!, extras.getInt("score"), extras.getString("date")!!)
        }

        //read the leaderboard into the recycle view
        readLeaderboard()
    }

    //swap to game activity
    fun ShowGameOnClick(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //write into txt file
    private fun toCSV(winner: String, score: Int, date: String) {
        val fileOutputStream: FileOutputStream =
            openFileOutput(LEADERBOARD_RECORDS_FILE, MODE_APPEND)
        val leaderboardRecordFile = OutputStreamWriter(fileOutputStream)
        leaderboardRecordFile.write("$winner,$score,$date\n")
        leaderboardRecordFile.close()
    }

    //read from leaderboard
    private fun readLeaderboard() {
        val file = File(filesDir, LEADERBOARD_RECORDS_FILE)
        if (file.exists()) {
            File(filesDir, LEADERBOARD_RECORDS_FILE).forEachLine {
                val parts = it.split(",")
                var leaderboardItem = leaderboardItem(parts[0], parts[1].toInt(), parts[2])
                leaderboardList.add(leaderboardItem)
            }
            leaderboardList.reverse()
            leaderboardListAdapter.notifyDataSetChanged()
        }
    }
}