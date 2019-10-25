package com.example.test2

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_payment_method.*

class paymentMethod : AppCompatActivity() {

    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_payment_method)

        font()


        gohomefpay.setOnClickListener({
            var go = Intent(this, home::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            this.startActivity(go)
        })

        gosettingfhome.setOnClickListener({
            var go = Intent(this, admin::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            this.startActivity(go)
        })

        gosessionfhome.setOnClickListener({
            var go = Intent(this, sessions::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            this.startActivity(go)
        })
        gowalletfhome.setOnClickListener({
            var go = Intent(this, wallet::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            this.startActivity(go)

        })


        var isOpen =0
        alldrinfo.setOnClickListener({
            if (isOpen==0){
                isOpen=1
                alldrdetails.visibility=View.VISIBLE
            }else{
                isOpen=0
                alldrdetails.visibility=View.GONE
            }
        })


        val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
        var qury = pref57.getString("profleinfo", "0")
        var useremail=qury

        val bundl=intent.extras

        var dremail =    bundl!!.getString("dremail")
        var day =        bundl!!.getString("day")!!.split(" ")





        myRef.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                var data =p0.child("doctors").child(mailwithoutdots(dremail!!)).value as HashMap<String,Any>
                var dayinfo = data["dayLimits"] as HashMap<String, String>


                drpayname.text=data["name"].toString()
                drpayprice.text=data["price"].toString() + " LE"
                drpayspec.text=data["speciality"].toString()
                drpaylocation.text=data["covern"].toString() +" , " + data["city"].toString()
                drpayday.text="Turn Number " +(day[1].toInt()).toString()+" In " + day[3]

                var reservationsID =p0.child("cliantID").value.toString()
                var userBalance =p0.child("cliants").child(useremail!!).child("balance").value.toString()



                paybybalance.setOnClickListener({

                    if (userBalance.toInt() >= data["price"].toString().toInt()) {


                        myRef.child("cliants").child(useremail!!).child("reservations").child(reservationsID)
                            .setValue(sessionInfo(reservationsID, "1","You Balance",day[1]+" "+day[3],mailwithoutdots(dremail!!),useremail))


                        myRef.child("reservations").child(reservationsID)
                            .setValue(sessionInfo(reservationsID, "1","You Balance",day[1]+" "+day[3],mailwithoutdots(dremail!!),useremail))


                        myRef.child("cliantID").setValue((reservationsID.toInt() + 1).toString())
                        myRef.child("doctors").child(mailwithoutdots(dremail!!)).child("dayLimits")
                            .child(day[3].toLowerCase())
                            .setValue(dayinfo[day[3].toLowerCase()]!!.split(" ")[0] + " " + (day[1].toInt() + 1).toString())

                        myRef.child("cliants").child(useremail!!).child("balance").setValue((userBalance.toInt() - data["price"].toString().toInt()).toString())

                        var go = Intent(this@paymentMethod , sessions::class.java)
                        go.putExtra("useremail",useremail)
                        startActivity(go)

                    }else{
                        balancenotenough.visibility=View.VISIBLE

                    }
                })


                paypbfawry.setOnClickListener({
                    myRef.child("cliants").child(useremail!!).child("reservations")
                        .child(reservationsID)
                        .setValue(sessionInfo(reservationsID, "0","Fawry Service",day[1]+" "+day[3],mailwithoutdots(dremail!!),useremail))
                    myRef.child("reservations").child(reservationsID)
                        .setValue(sessionInfo(reservationsID, "0","Fawry Service",day[1]+" "+day[3],mailwithoutdots(dremail!!),useremail))
                    myRef.child("cliantID").setValue((reservationsID.toInt() + 1).toString())
                    myRef.child("doctors").child(mailwithoutdots(dremail!!)).child("dayLimits")
                        .child(day[3].toLowerCase())
                        .setValue(dayinfo[day[3].toLowerCase()]!!.split(" ")[0] + " " + (day[1].toInt() + 1).toString())

                    var go = Intent(this@paymentMethod , numberOFpayment::class.java)
                    go.putExtra("paymentNumber",reservationsID)
                    startActivity(go)
                })


                paybygroubwallet.setOnClickListener({

                        var go = Intent(this@paymentMethod, paybythegroupwallet::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    go.putExtra("useremail",useremail)
                    go.putExtra("amount",data["price"].toString())
                    go.putExtra("dremail",dremail)
                    go.putExtra("dayname",day[3])
                    go.putExtra("turn",day[1])
                    go.putExtra("maxturn",dayinfo[day[3].toLowerCase()]!!.split(" ")[0] )
                        startActivity(go)


                })


            }

        })



    }



    fun font(){
        var font2= Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        var font3= Typeface.createFromAsset(assets, "Raleway-Light.ttf")
        var font5= Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")
        var font6= Typeface.createFromAsset(assets, "Raleway-Medium.ttf")

        payhead.setTypeface(font2)
        paysubhead.setTypeface(font3)

        paypbfawry.setTypeface(font5)
        paybybalance.setTypeface(font5)

        drpayname.setTypeface(font6)
        drpaylocation.setTypeface(font6)
        drpayprice.setTypeface(font6)
        drpayspec.setTypeface(font6)
        drpayday.setTypeface(font6)

        payby.setTypeface(font2)

    }

    fun mailwithoutdots( mail:String):String{
        var result =""
        for (x in mail){
            if(x!='.')result+=x
        }
        return result
    }


}
