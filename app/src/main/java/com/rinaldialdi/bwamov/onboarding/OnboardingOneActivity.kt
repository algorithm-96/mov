package com.rinaldialdi.bwamov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rinaldialdi.bwamov.R
import com.rinaldialdi.bwamov.sign.signin.SignInActivity
import com.rinaldialdi.bwamov.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding_one.*

class OnboardingOneActivity : AppCompatActivity() {

    lateinit var preferences:Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_one)

        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")){

            finishAffinity()
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)

        }

        btn_lanjut.setOnClickListener {
            var intent = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
            startActivity(intent)
        }

        btn_lewati.setOnClickListener {

            preferences.setValues("onboarding", "1")

            finishAffinity()
            var intent = Intent(this@OnboardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}