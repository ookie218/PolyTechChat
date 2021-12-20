package com.ookie.polytechchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    private lateinit var role : String
    private lateinit var year : String
    private lateinit var editDepartment : EditText
    private lateinit var studentRadioButton : RadioButton
    private lateinit var facultyRadioButton : RadioButton
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
        studentRadioButton = findViewById(R.id.studentRadioButton)
        facultyRadioButton = findViewById(R.id.facultyRadioButton)
        btnSignUp = findViewById(R.id.btn_signup)


        //Gray out editText unless faculty is checked
        if (facultyRadioButton.isChecked) {
            role = "Faculty"
            year = ""
            editDepartment.isEnabled

        } else {
            role = "Student"
            year = (2022..2026).random().toString()
            editDepartment.isEnabled = false
        }


        btnSignUp.setOnClickListener {
            //Get details of User
            val firstName = editFirstName.text.toString()
            val lastName = editLastName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            var department = editDepartment.text.toString()
            val idNumber = (0..10000).random().toString() //Random number under 10,000


            if (editDepartment.text.isEmpty() || editDepartment.text == null) {
                department = ""
            }

            //Check to make sure all fields have input
            val stringArray = arrayListOf(firstName, lastName, email, password)

            for(string in stringArray){

                if(string == null  || string.isEmpty()){

                    when(string){
                        firstName -> Toast.makeText(this, "Fill In Thw First Name Field",Toast.LENGTH_LONG).show()
                        lastName ->   Toast.makeText(this, "Fill In The Last Name Field",Toast.LENGTH_LONG).show()
                        email  ->   Toast.makeText(this, "Fill In The Email Field",Toast.LENGTH_LONG).show()
                        password -> Toast.makeText(this, "Fill In The Password Field",Toast.LENGTH_LONG).show()
                    }
                }
            }



            signUp(firstName, lastName, email, password, idNumber, role, department, year)

        }

    }




    private fun signUp(firstName: String, lastName: String, email: String, password: String, idNumber: String,
                            role: String, department: String, year: String) {
        //Logic for Signing up User
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //code for jumping to home

                    addUserToDatabase(firstName, lastName, email, password, idNumber, role, department, year, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(firstName: String, lastName: String, email: String, password: String, idNumber: String,
                                  role: String, department: String, year: String, uid: String) {
        //Initialize DB Reference
        mDBReference = FirebaseDatabase.getInstance().getReference()

        //"user" is parent node in path
        mDBReference.child("user").child(uid).setValue(User(firstName, lastName, email, password, idNumber,
            role, department, year, uid))

    }


}