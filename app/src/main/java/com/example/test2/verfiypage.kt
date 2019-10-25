package com.example.test2

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_verfiypage.*

class verfiypage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_verfiypage)

        var font1= Typeface.createFromAsset(assets, "Raleway-Black.ttf")
        var font2= Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        var font3= Typeface.createFromAsset(assets, "Raleway-Light.ttf")
        var font4= Typeface.createFromAsset(assets, "Raleway-Thin.ttf")
        var font5= Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")

        vertxt1.setTypeface(font2)
        vertxt2.setTypeface(font3)

        verbtn.setOnClickListener({
            startActivity(Intent(this,login::class.java))
        })





    }
}
