package com.example.test2

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_login)

        fonts()

        logeditpass.addTextChangedListener(filterTextWatcher)
        logedtitmail.addTextChangedListener(filterTextWatcher)

        forgetpass.setOnClickListener({
            startActivity(Intent(this,forgetpassword::class.java))
        })

        logbtnlogin.setOnClickListener({
            logwait.visibility=View.VISIBLE

            myRef.child("cliants").addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {

                    if (p0.hasChild(mailwithoutdots(mailwithoutdots(logedtitmail.text.toString().trim())))){
                       var correctPass = p0.child(mailwithoutdots(logedtitmail.text.toString().trim())).child("password").value.toString()

                            FirebaseAuth.getInstance().signInWithEmailAndPassword(logedtitmail.text.toString().trim(),logeditpass.text.toString().trim())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful){
                                        if(FirebaseAuth.getInstance().currentUser!!.isEmailVerified){

                                            val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
                                            val ed57 = pref57.edit()
                                            ed57.putString("profleinfo", mailwithoutdots(mailwithoutdots(logedtitmail.text.toString().trim())))
                                            ed57.commit()

                                            var go = Intent(this@login, home::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                            startActivity(go)

                                        }else{
                                            error.visibility=View.VISIBLE
                                            error.text = "Account not verfiy!"
                                            logwait.visibility=View.GONE

                                        }

                                    }else{
                                        var errorMes = task.exception!!.message.toString()
                                        error.visibility=View.VISIBLE
                                        if (errorMes=="The password is invalid or the user does not have a password.")
                                            errorMes="Wrong password!"
                                        logwait.visibility=View.GONE

                                        error.text = errorMes
                                    }
                                }



                    }else{
                        logtxtuncorrect.visibility=View.VISIBLE
                        error.visibility=View.GONE
                        logwait.visibility=View.GONE

                    }

                }

            })
        })


        logtxtnewacc1.setOnClickListener({
            var go = Intent(this , joinActivity::class.java)
            startActivity(go)
        })
        logtxtnewacc2.setOnClickListener({
            var go = Intent(this , joinActivity::class.java)
            startActivity(go)
        })

    }




    private val filterTextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            var count =0

            logtxtuncorrect.setVisibility(View.GONE)
            error.setVisibility(View.GONE)
            logwait.setVisibility(View.GONE)

            var arrayOFediditText = ArrayList<EditText>()
            arrayOFediditText.add(logedtitmail)
            arrayOFediditText.add(logeditpass)


            for (x in arrayOFediditText){
                if (x.text.length>0){
                    x.setBackgroundResource(R.drawable.edittextafterwrite)
                    count++
                }else{
                    x.setBackgroundResource(R.drawable.edittextbeforewrite)
                }
            }

            if(count==2) {
                logbtnlogin.setEnabled(true)
                logbtnlogin.setBackgroundResource(R.drawable.signinenabled)

            }else{
                logbtnlogin.setEnabled(false)
                logbtnlogin.setBackgroundResource(R.drawable.sginindisabled)

            }

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable) {

        }
    }

    fun mailwithoutdots( mail:String):String{
        var result =""
        for (x in mail){
            if(x!='.')result+=x
        }
        return result
    }

    fun fonts(){
        var font1= Typeface.createFromAsset(assets, "Raleway-Black.ttf")
        var font2= Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        var font3= Typeface.createFromAsset(assets, "Raleway-Light.ttf")
        var font4= Typeface.createFromAsset(assets, "Raleway-Thin.ttf")
        var font5= Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")

        logtxtlogin.setTypeface(font2)
        logtxtslogen.setTypeface(font3)

        textView11.setTypeface(font3)
        textView12.setTypeface(font3)


    }

}
