package com.example.test2

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_join.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class joinActivity : AppCompatActivity() {

    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_join)

        fonts()


        firstname.addTextChangedListener(filterTextWatcher);
        secondname.addTextChangedListener(filterTextWatcher);
        email.addTextChangedListener(filterTextWatcher);
        password.addTextChangedListener(filterTextWatcher);


        signin.setOnClickListener({


            if (password.text.toString().length<6){
                passweak.visibility=View.VISIBLE

            }else {
                wait.visibility=View.VISIBLE
                myRef.child("cliants").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        if (p0.hasChild(mailwithoutdots(email.text.toString().trim()))) {
                            mailusedbefore.setVisibility(View.VISIBLE)
                            wait.visibility=View.GONE

                        } else {


                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString().trim() ,password.text.toString().trim())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful){
                                        FirebaseAuth.getInstance().currentUser!!.sendEmailVerification()
                                            .addOnCompleteListener { task ->
                                                if (task.isSuccessful){
                                                    myRef.child("cliants")
                                                        .child(mailwithoutdots(email.text.toString().trim()))
                                                        .setValue(
                                                            cliantinfo(
                                                                firstname.text.toString().trim()
                                                                , secondname.text.toString().trim()
                                                                , email.text.toString().trim()
                                                                ,"0"
                                                                ,","
                                                            ,","

                                                            )
                                                        )

                                                    startActivity(Intent(this@joinActivity,verfiypage::class.java))

                                                }else{
                                                    passweak.visibility=View.VISIBLE
                                                    passweak.text=task.exception!!.message
                                                    wait.visibility=View.GONE


                                                }
                                            }

                                    }else{
                                        passweak.visibility=View.VISIBLE
                                        passweak.text=task.exception!!.message
                                        wait.visibility=View.GONE

                                    }
                                }




                        }
                    }
                })


            }



        })







        haveacc2.setOnClickListener({
            var go = Intent(this ,login::class.java)
            startActivity(go)
        })


        haveaccount1.setOnClickListener({
            var go = Intent(this ,login::class.java)
            startActivity(go)
        })






    }





    private val filterTextWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            password.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s!!.length<6){
                        passweak.visibility=View.VISIBLE
                        password.setBackgroundResource(R.drawable.passwordweak)

                    }else{
                        passweak.visibility=View.GONE

                        password.setBackgroundResource(R.drawable.edittextafterwrite)

                    }
                }

            })

            var count =0

            mailusedbefore.setVisibility(View.GONE)

            var arrayOFediditText = ArrayList<EditText>()
            arrayOFediditText.add(firstname)
            arrayOFediditText.add(secondname)
            arrayOFediditText.add(email)
            arrayOFediditText.add(password)

            for (x in arrayOFediditText){
                if (x.text.length>0){
                    x.setBackgroundResource(R.drawable.edittextafterwrite)
                    count++
                }else{
                    x.setBackgroundResource(R.drawable.edittextbeforewrite)
                }
            }

            if(count==4 && password.text.toString().length>=6) {
                signin.setEnabled(true)
                signin.setBackgroundResource(R.drawable.signinenabled)

            }else{
                signin.setEnabled(false)
                signin.setBackgroundResource(R.drawable.sginindisabled)

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

        joinustext.setTypeface(font2)
        slogentext.setTypeface(font3)
        textView7.setTypeface(font3)
        textView6.setTypeface(font3)

        firstname.setTypeface(font5)
        secondname.setTypeface(font5)
        email.setTypeface(font5)
        passweak.setTypeface(font3)


    }
}
