
package com.rinaldialdi.bwamov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.sign.signin.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername:String
    lateinit var sNama:String
    lateinit var sPassword:String
    lateinit var sEmail:String

    private lateinit var mFirebaseInstance : FirebaseDatabase
    private lateinit var mDatabase  : DatabaseReference
    private lateinit var mDatabaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        btn_lanjut.setOnClickListener {

            sUsername = edt_username.text.toString()
            sNama = edt_name.text.toString()
            sPassword = edt_password.text.toString()
            sEmail = edt_email.text.toString()

            if (sUsername.equals("")){
                edt_username.error="silahkan isi username anda"
                edt_username.requestFocus()
            } else if (sNama.equals("")){
                edt_name.error="silahkan isi nama anda"
                edt_name.requestFocus()
            } else if (sPassword.equals("")){
                edt_password.error="silahkan isi password anda"
                edt_password.requestFocus()
            } else if (sEmail.equals("")){
                edt_email.error="silahkan isi email anda"
                edt_email.requestFocus()
            } else {
                saveUsername(sUsername, sPassword, sNama, sEmail)
            }

        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sNama: String, sEmail: String) {

        val user = User()

        user.username   = sUsername
        user.password   = sPassword
        user.nama       = sNama
        user.email      = sEmail

        if (sUsername != null){

            checkingUser (sUsername, user)
        }
    }

    private fun checkingUser(sUsername: String, data: User) {

        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){

                    mDatabaseReference.child(sUsername).setValue(data)

                    val SignUpPhotoscreen = Intent(this@SignUpActivity,
                        SignUpPhotoscreenActivity::class.java).putExtra("nama", data.nama)
                            startActivity(SignUpPhotoscreen)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity,""+databaseError.message, Toast.LENGTH_SHORT).show()
            }


        })



    }
}