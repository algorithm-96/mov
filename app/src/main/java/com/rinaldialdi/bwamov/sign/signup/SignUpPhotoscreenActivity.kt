package com.rinaldialdi.bwamov.sign.signup

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.rinaldialdi.bwamov.home.HomeActivity
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*
import java.util.*

class SignUpPhotoscreenActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        tv_welcome.text = "Selamat datang\n"+intent.getStringExtra("nama")

        iv_add.setOnClickListener {

            if (statusAdd){

                statusAdd = false
                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_add_profile)
                iv_profile.setImageResource(R.drawable.user_pic)

            } else {

                Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()

            }
        }

        btn_home.setOnClickListener {
            finishAffinity()

            val goHome = Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_save.setOnClickListener {

            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            val ref = storageReference.child("image/"+UUID.randomUUID().toString())
            ref.putFile(filePath)
                .addOnSuccessListener {

                    progressDialog.dismiss()
                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()

                    ref.downloadUrl.addOnSuccessListener {
                        preferences.setValues("url", toString())
                    }
                    finishAffinity()

                    val goHome = Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java)
                    startActivity(goHome)

                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener {
                    taskSnapshot -> val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Upload"+progress.toInt()+" %")
                }

        }


    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan foto profile", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        Toast.makeText(this, "ingin upload nanti? klik tombol upload nanti saja", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val bitmap = data?.extras?.get("data") as Bitmap

            filePath = data.getData()!!
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

            btn_save.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_baseline_delete_24)
        }
    }
}