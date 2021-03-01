package it.polito.ic2020.did_kidbalanceapplication.database

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import it.polito.ic2020.did_kidbalanceapplication.ChildActivity
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.rv_weights.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.days

class ChildWeightsAdapter: RecyclerView.Adapter<ChildWeightsAdapter.MyViewHolder>(){
    private var userList= emptyList<Float>()
    private var dateList= emptyList<Long>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_weights,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =userList[position]
        val dateItem = dateList[position]
        val dat = convertLongToTime(dateItem)
        holder.itemView.weight.text= currentItem.toString()
        holder.itemView.date_weighted.text = dat
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user: List<Float>, data: List<Long>){
        this.userList=user
        this.dateList=data
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return format.format(date)
    }
}