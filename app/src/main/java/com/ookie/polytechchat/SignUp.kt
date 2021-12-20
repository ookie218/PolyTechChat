package com.ookie.polytechchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var editFirstName : EditText
    private lateinit var editLastName : EditText
    private lateinit var editEmail : EditText
    private lateinit var editPassword : EditText
    //private lateinit var editIdNumber : EditText
    private lateinit var title : String
    private lateinit var editDepartment : EditText
    private lateinit var btnSignUp : Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDBReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide() //Hides action bar at top of App

        mAuth = FirebaseAuth.getInstance()

        editFirstName = findViewById(R.id.editText_FirstName)
        editLastName = findViewById(R.id.editText_LastName)
        editEmail = findViewById(R.id.editText_email)
        editPassword = findViewById(R.id.editText_password)
        editDepartment = findViewById(R.id.editText_Department)
        //editIdNumber = findViewById(R.id.idNumber) The user doesn't need to see this
        btnSignUp = findViewById(R.id.btn_signup)

        btnSignUp.setOnClickListener {
            //Get details of User
            val firstName = editFirstName.text.toString()
            val lastName = editLastName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            val idNumber = (0..10000).random().toString() //Random number under 10,000

            signUp(firstName, lastName, email, password, idNumber)

            if(it is RadioButton){
                val checked = it.isChecked

                when(it.getId()){
                    R.id.studentRadioButton ->

                        if(checked){
                            title = "Student"
                            editDepartment.clearFocus()
                        }

                    R.id.facultyRadioButton ->
                        if(checked){
                            title = "Faculty"

                        }
                }
            }




        }

    }

    private fun signUp(firstName: String, lastName: String, email: String, password: String, idNumber: String) {
        //Logic for Signing up User
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home

                    addUserToDatabase(firstName, lastName, email, password, idNumber, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(firstName: String, lastName: String, email: String, password: String, idNumber: String, uid: String) {
        //Initialize DB Reference
        mDBReference = FirebaseDatabase.getInstance().getReference()

        //"user" is parent node in path
        mDBReference.child("user").child(uid).setValue(User(firstName, lastName, email, password, idNumber, uid))

    }


}