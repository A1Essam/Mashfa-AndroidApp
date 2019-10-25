package com.example.test2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash)


        val time = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
        var qury = time.getString("firsttime", "0")
        var qurymail = time.getString("profleinfo", "0")

        if (qury=="0"){
            val ed57 = time.edit()
            ed57.putString("firsttime","1" )
            ed57.commit()

            Handler().postDelayed({
                startActivity(Intent(this , intro::class.java))

            },2500)


        }else{

            Handler().postDelayed({

                if (qurymail!="0")
                startActivity(Intent(this , home::class.java))
                else
                    startActivity(Intent(this , login::class.java))


            },2500)

        }





    }
}
