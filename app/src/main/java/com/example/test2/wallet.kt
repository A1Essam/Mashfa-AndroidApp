package com.example.test2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.activity_wallet.view.*

class wallet : AppCompatActivity() {

    var myref =FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_wallet)

        val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
        var qury = pref57.getString("profleinfo", "0")
        var useremail=qury






        gohomefwallet.setOnClickListener({
            var go = Intent(this, home::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })


        gosessionfwallet.setOnClickListener({
            var go = Intent(this, sessions::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })


        gosettingfwallet.setOnClickListener({
            var go = Intent(this, admin::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })

        groubwmode.setOnClickListener({
            if (groubwmode.isChecked) {
                addtothegroup.visibility = View.VISIBLE
            } else {
                addtothegroup.visibility = View.GONE

            }
        })


        myref.child("cliants").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                userbalance.text=p0.child(useremail!!).child("balance").value.toString()+" LE"

                addtogroup.setOnClickListener({
                    if (transfertouser.text.toString().trim().length>10 && p0.hasChild(mailwithoutdots(addedperson.text.toString().trim()))) {
                        var addedusergroupwallet =
                            p0.child(mailwithoutdots(addedperson.text.toString().trim()))
                                .child("onWalletof").value.toString()

                        if (addedusergroupwallet.split(",").contains(useremail)){
                            errorMessage.text = "This user added before!"
                            errorMessage.visibility = View.VISIBLE
                        }else {
                            myref.child("cliants")
                                .child(mailwithoutdots(addedperson.text.toString().trim()))
                                .child("onWalletof")
                                .setValue(addedusergroupwallet + useremail + ",")

                            errorMessage.text = "Done! Uuser added successfully."
                            errorMessage.visibility = View.VISIBLE
                        }

                        }else{
                        errorMessage.text="No user with this Email!"
                        errorMessage.visibility=View.VISIBLE
                    }
                })

                transfer.setOnClickListener({


                    if ( transfertouser.text.toString().trim().length>10 && p0.hasChild(mailwithoutdots(transfertouser.text.toString().trim()))   ) {
                        if (useremail==mailwithoutdots(transfertouser.text.toString().trim())){
                            errorMessage.text = "You can't transfer to your self!"
                            errorMessage.visibility = View.VISIBLE
                        }else {
                            if (ammount.text.toString().toInt() <= userbalance.text.toString().toInt()) {


                                var transferdpersonbalabnce =
                                    p0.child(mailwithoutdots(transfertouser.text.toString().trim()))
                                        .child("balance").value.toString()

                                myref.child("cliants")
                                    .child(mailwithoutdots(useremail))
                                    .child("balance")
                                    .setValue((userbalance.text.toString().toInt() - ammount.text.toString().toInt()).toString())

                                myref.child("cliants")
                                    .child(mailwithoutdots(transfertouser.text.toString().trim()))
                                    .child("balance")
                                    .setValue((transferdpersonbalabnce.toInt() + ammount.text.toString().toInt()).toString())

                                errorMessage.text = "Done, Money transferd successfully!"
                                errorMessage.visibility = View.VISIBLE
                            } else {
                                errorMessage.text = "Sorry, You don't have enough money!"
                                errorMessage.visibility = View.VISIBLE
                            }
     }
                    }else{
                        errorMessage.text="No user with this Email!"
                        errorMessage.visibility=View.VISIBLE
                    }

                    })

            }

        })

    }

    fun mailwithoutdots( mail:String):String{
        var result =""
        for (x in mail){
            if(x!='.')result+=x
        }
        return result
    }
}


