package com.example.test2

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_number_ofpayment.*

class numberOFpayment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_number_ofpayment)

        val bundl=intent.extras

        var paymentNumber =bundl!!.getString("paymentNumber")
        numnum.text=paymentNumber

        var font1= Typeface.createFromAsset(assets, "Raleway-Black.ttf")
        var font2= Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        var font3= Typeface.createFromAsset(assets, "Raleway-Light.ttf")
        var font5= Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")
        var font6= Typeface.createFromAsset(assets, "Raleway-Medium.ttf")

        numhead.setTypeface(font2)
        numsubhead.setTypeface(font6)

        numnum.setTypeface(font1)

        numplz.setTypeface(font5)

        gohome.setOnClickListener({
            var go = Intent(this , home::class.java)
            startActivity(go)
        })

    }
}
