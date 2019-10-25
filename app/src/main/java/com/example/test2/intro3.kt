package com.example.test2

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_intro3.*

class intro3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_intro3)


        var font2 = Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        var font3 = Typeface.createFromAsset(assets, "Raleway-Light.ttf")
        var font5 = Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")
        var font6 = Typeface.createFromAsset(assets, "Raleway-Medium.ttf")


        i3head1.setTypeface(font2)
        subhead.setTypeface(font3)

        nextbtn.setOnClickListener({

            startActivity(Intent(this,joinActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        })






    }
}
