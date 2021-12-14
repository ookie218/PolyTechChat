package com.ookie.polytechchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Extend Adapter of RecyclerView AND Pass in the custom ViewHolder
//User Adapter must take in context & List
class UserAdapter(val context: Context, var userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    //List to manipualate
    //val filteredListFull: ArrayList<User> = null!!


    //Extend UserViewHolder we created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        //Create a view using LayuotInflater and inflate the layout
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        //get user from UserList
        val currentUser = userList[position]

        //textName from UserViewHolder we created
        holder.textName.text = currentUser.name

        //setOnClickListeners *using CONTEXT (which is defined in adapter class constructor)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name", currentUser.name)
            intent.putExtra("uid", currentUser.uID)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    //Create ViewHolder and pass it in what we extended
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Initialize views of User Layout for ViewHolder
        val textName = itemView.findViewById<TextView>(R.id.txt_name) //From user_layout.xml
    }


    fun updateData(users: ArrayList<User>){
        userList = users
        notifyDataSetChanged() //Method of User adapter
    }

   /* Commenting out code for filterable

   override fun getFilter(): Filter {
        return userFilter()
    
    }


    private val userFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<User>? = null

            if (constraint == null || constraint.isEmpty()) {
                filteredList?.addAll(filteredListFull)

            } else {
                var filterPattern = constraint.toString().lowercase().trim()

                for (user: User in constraint) {
                    if (user.name?.lowercase()!!.contains(filterPattern)) {
                        filteredList?.add(user)
                    }

                }
            }

            //Must return something: Create filtered results to return
            var results: FilterResults? = null
            results?.values = filteredList

            return results!!
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            userList.clear()
            userList.addAll(results?.values as Collection<User>)
            notifyDataSetChanged()

        }

    }*/

}