package com.example.test2

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgetpassword.*

class forgetpassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_forgetpassword)


        var font1= Typeface.createFromAsset(assets, "Raleway-Black.ttf")
        var font2= Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        var font3= Typeface.createFromAsset(assets, "Raleway-Light.ttf")
        var font4= Typeface.createFromAsset(assets, "Raleway-Thin.ttf")
        var font5= Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")

        forgtxt1.setTypeface(font2)
        forgtxt2.setTypeface(font3)


        button.setOnClickListener({
            FirebaseAuth.getInstance().sendPasswordResetEmail(editText.text.toString().trim())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Done! Check your mail please.",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this , login::class.java))
                    }else{
                        Toast.makeText(this,task.exception!!.message,Toast.LENGTH_LONG).show()

                    }
                }

        })

    }
}
