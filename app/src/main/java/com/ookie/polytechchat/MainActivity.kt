package com.ookie.polytechchat

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var tempUserList: ArrayList<User> // We will use this one to display query results
    private var adapter: UserAdapter ?= null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBReference: DatabaseReference

    //Test the search function
    private lateinit var searchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize Authorization
        mAuth = FirebaseAuth.getInstance()
        mDBReference = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        tempUserList = ArrayList()

        adapter = UserAdapter(this, userList)

        //Define RecyclerView
        userRecyclerView = findViewById(R.id.userRecyclerView)
        //Set Layout Manager
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        //Set Adapter
        userRecyclerView.adapter = adapter

        //*Read data stored from RecyclerView*

        //Get child node, and add an event listener (while implementing methods)
        mDBReference.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //Since method is called ONLY when data is changed; clear the screen and re-populate it
                userList.clear()
                for (postSnapshot in snapshot.children){

                    //Create USER OBJECT and add it to list since there are multiple users - type is User Object
                    val currentUser = postSnapshot.getValue(User::class.java)

                    //Make sure it doesn't show who's logged in
                    if(mAuth.currentUser?.uid != currentUser?.uID) {
                        userList.add(currentUser!!)
                    }

                }
                //Notify Adapter
                adapter?.updateData(userList)

            }

            override fun onCancelled(error: DatabaseError) {
                //Not going to use for this app...
            }

        })


        tempUserList.addAll(userList)
        //adapter = UserAdapter(this, tempUserList)

/*
        searchButton = findViewById(R.id.search_button)
        //Test searching on a new screen
        searchButton.setOnClickListener {
            val searchIntent = Intent(this@MainActivity, SearchActivity::class.java)
            finish()
            startActivity(searchIntent)
        }
*/
    }

    //Inflate the options menu (Pass in XML as well as menu variable)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)


        //Search functionality
        val item = menu?.findItem(R.id.searchAction)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println(newText)
                tempUserList.clear()
                val searchText = newText?.lowercase(Locale.getDefault())?.trim()
                if (searchText!!.isNotEmpty()) {
                    println(userList.toString())
                    userList.forEach {
                        if (it.name?.lowercase(Locale.getDefault())!!.contains(searchText)) {

                            tempUserList.add(it)

                        }
                    }

                    userRecyclerView.adapter = adapter
                    adapter?.userList = tempUserList
                   // adapter?.updateData(tempUserList)

                    adapter?.notifyDataSetChanged()


                    //userRecyclerView.adapter!!.notifyDataSetChanged()

                } else {

                    //If text is empty, inflate entire list
                    tempUserList.clear()
                    tempUserList.addAll(userList)
                    userRecyclerView.adapter!!.notifyDataSetChanged()

                }

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Check which item was clicked
        if (item.itemId == R.id.logout) {
            //write logic for logout
            mAuth.signOut() //FireBase method

            //Send them back to login screen
            val intent = Intent(this@MainActivity, Login::class.java)
            finish()
            startActivity(intent)
            return true
        }

        return true

    }

    private fun getUserData(){


    }

}