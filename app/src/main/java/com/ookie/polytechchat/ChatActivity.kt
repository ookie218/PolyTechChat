package com.ookie.polytechchat

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.content.Intent
import android.net.Uri
import android.util.Log

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.net.URI


class ChatActivity : AppCompatActivity() {

    //Declare
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var attachmentButton: ImageView

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDBReference: DatabaseReference
    private lateinit var mFirebaseStorage: FirebaseStorage
    private lateinit var mChatPhotosStorageRef : StorageReference

    var PICK_IMAGE_INTENT = 1

    //Used to create a unique "room" for sender/receiver to make sure messages are private
    //Used var because these may change
    var receiverRoom: String? = null
    var senderRoom: String? = null

    val senderUid = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //Retrieve intent data (from user adapter? Has to be since name pops up on support bar before messages are even passed / started)
        val name = intent.getStringExtra("name")

        //Updating using sender/receiver "rooms"
        val receiverUid = intent.getStringExtra("uid") // instead of val uid = intent.getStringExtra("uid")

        //UID of current logged in user
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDBReference = FirebaseDatabase.getInstance().getReference()
        //mMessagesDatabaseReference = mDBReference.getReference().child("messages")
        mFirebaseStorage = FirebaseStorage.getInstance()
        mChatPhotosStorageRef = mFirebaseStorage.getReference().child("chat_photos");


        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        //Set title to name of person you're talking to
        supportActionBar?.title = name

        //Initialize
        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sendButton)
        attachmentButton = findViewById(R.id.attachmentButton)

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)


        //Set LayoutManager and adapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = messageAdapter


        //logic for adding data to RecyclerView
        mDBReference.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()//List must be cleared everytime this is called so values don't repeat

                    //Use snapshot to get values and show it
                    for (postSnapshot in snapshot.children) { //Traverse children of databaseSnapshot\

                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!) // Add it to the list and make sure it's null safe
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })


        //add message to database
        sendButton.setOnClickListener {

            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            //Get DB reference, under chats path, under senderRoom, under messages
            //with -push... See's if this is successful and sends the message to both the sender and receiver room
            mDBReference.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDBReference.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            //After message is sent, Clear the textbox
            messageBox.setText("")
        }

        //Intent to choose photo from local storage
        attachmentButton.setOnClickListener {

                var intent =  Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_IMAGE_INTENT);
        }


    }


    //Send Photos over Firebase
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_INTENT && resultCode == RESULT_OK) {
            //var selectedImageUri : Uri = data.data
            //var photoRef : StorageReference = mChatPhotosStorageRef.child(selectedImageUri.get)


            var selectedImageUri: Uri = data?.getData()!!

            // Get a reference to store file at chat_photos/<FILENAME>
            var photoRef: StorageReference =
                mChatPhotosStorageRef.child(selectedImageUri.getLastPathSegment()!!)

            photoRef.putFile(selectedImageUri).addOnSuccessListener { taskSnapshot ->
                //var downloadUrl = taskSnapshot?.

                val downloadUrl = mChatPhotosStorageRef.downloadUrl;
                Log.d("FirebaseManager", "Upload Successful")

                var chatMessage = Message(null, senderUid , downloadUrl.toString())

                mDBReference.push().setValue(chatMessage);


            }

        }

    }

}