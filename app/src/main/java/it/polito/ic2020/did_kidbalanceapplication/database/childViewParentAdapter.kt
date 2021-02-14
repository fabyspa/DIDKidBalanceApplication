package it.polito.ic2020.did_kidbalanceapplication.database

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import it.polito.ic2020.did_kidbalanceapplication.ChildActivity
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.rv_parent_view.*
import kotlinx.android.synthetic.main.rv_parent_view.view.*

class childViewParentAdapter: RecyclerView.Adapter<childViewParentAdapter.MyViewHolder>(){
    private var userList= emptyList<ChildWeight>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_parent_view,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem =userList[position]
        holder.itemView.nome.text= currentItem.nome
        holder.itemView.foto.setImageResource(currentItem.picture)
        holder.itemView.setOnClickListener( View.OnClickListener() {
            val idPressed = bundleOf("id_pressed" to currentItem.id, "name_pressed" to currentItem.nome)
            findNavController(holder.itemView).navigate(R.id.action_child_list_parentFragment_to_GGraphFragment2, idPressed)

            println("ciao")
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