package it.polito.ic2020.did_kidbalanceapplication.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.rv_parent_view.view.*

class childViewParentAdapter(): RecyclerView.Adapter<childViewParentAdapter.ParentHolder>(){
    private var userList= emptyList<ChildWeight>()

    class ParentHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentHolder {
    return ParentHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_child_list_parent,parent,false))
    }

    override fun onBindViewHolder(holder: ParentHolder, position: Int) {
        val currentItem =userList[position]
        holder.itemView.nome.text= currentItem.nome
        holder.itemView.foto.setImageResource(currentItem.picture)
        holder.itemView.setOnClickListener( View.OnClickListener() {
            findNavController(holder.itemView).navigate(R.id.action_child_list_parentFragment_to_GGraphFragment2)
            /*    val `in` = Intent(holder.itemView.context, ChildActivity::class.java)
            `in`.putExtra("name", currentItem.nome)
            `in`.putExtra("id", currentItem.id)
            println(`in`.extras.toString())
            println("ciao")
            holder.itemView.context.startActivity(`in`)

*/
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