package it.polito.ic2020.did_kidbalanceapplication.database

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.polito.ic2020.did_kidbalanceapplication.ChildActivity
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.android.synthetic.main.fragment_add_child.view.*

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){
    private var userList= emptyList<ChildWeight>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =userList[position]
        holder.itemView.name.text= currentItem.nome
        holder.itemView.picture.setImageResource(currentItem.picture)
        holder.itemView.setOnClickListener( View.OnClickListener() {
            //findNavController(holder.itemView).navigate(R.id.action_homeFragment_to_navigation_child)
            val `in` = Intent(holder.itemView.context, ChildActivity::class.java)
            `in`.putExtra("name", currentItem.nome)
            println(`in`.extras.toString())
            println("ciao")
            holder.itemView.context.startActivity(`in`)
            }

        )

    }

    override fun getItemCount(): Int {
       return userList.size
    }

    fun setData(user: List<ChildWeight>){
        this.userList=user
        notifyDataSetChanged()
    }
}