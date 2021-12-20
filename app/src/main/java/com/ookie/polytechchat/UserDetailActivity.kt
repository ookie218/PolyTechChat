package com.ookie.polytechchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UserDetailActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        //Initialize Authorization
        mAuth = FirebaseAuth.getInstance()
        mDBReference = FirebaseDatabase.getInstance().getReference()

        val userNameTextView = findViewById<View>(R.id.userNameTV) as TextView
        val roleTextView = findViewById<View>(R.id.userRole) as TextView
        val emailTextView = findViewById<View>(R.id.emailPlacement) as TextView
        val idNumberTextView = findViewById<View>(R.id.idPlacement) as TextView
        val yearTextView = findViewById<View>(R.id.yearPlacement) as TextView
        val departmentTextView = findViewById<View>(R.id.departmentPlacement) as TextView


            //Get child node, and add an event listener (while implementing methods)
            mDBReference.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //Since method is called ONLY when data is changed; clear the screen and re-populate it
                for (postSnapshot in snapshot.children){

                    //Create USER OBJECT and add it to list since there are multiple users - type is User Object
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (currentUser != null) {

                        //Get Values
                        val fullName = currentUser.firstName + " " + currentUser.lastName
                        val role = currentUser.role
                        val email = currentUser.email
                        val id = currentUser.idNumber
                        val year = currentUser.year
                        val department = currentUser.department

                        //Set to textViews
                        userNameTextView.text = fullName
                        roleTextView.text = role
                        emailTextView.text = email
                        idNumberTextView.text = id
                        yearTextView.text = year
                        departmentTextView.text = department

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Not going to use for this app...
            }

        })


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //Check which item was clicked
        if (item.itemId == R.id.logout) {
            //write logic for logout
            mAuth.signOut() //FireBase method

            //Send them back to login screen
            val signOutIntent = Intent(this@UserDetailActivity, Login::class.java)
            finish()
            startActivity(signOutIntent)

            return true

        } else if (item.itemId == R.id.groupChat){
            val groupChatIntent = Intent(this@UserDetailActivity, GroupChatActivity::class.java)
            startActivity(groupChatIntent)

            return true

        }


        return true
    }

}
