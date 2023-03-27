package com.wyattrussellmiller.pig

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class leaderboardAdapter(private var leaderboardItemList: List<leaderboardItem>) :
    RecyclerView.Adapter<leaderboardAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var item: TextView = view.findViewById(R.id.leaderboardItemText)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val leaderboardItem = leaderboardItemList[position]
        holder.item.text = leaderboardItem.toString()
        holder.item.typeface = Typeface.MONOSPACE

        if (leaderboardItem.winner == "You") {
            holder.item.setTextColor(Color.BLUE)
        } else {
            holder.item.setTextColor(Color.RED)
        }
    }

    override fun getItemCount(): Int {
        return leaderboardItemList.size
    }
}