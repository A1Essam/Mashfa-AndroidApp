package com.example.test2

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_payment_method.*
import kotlinx.android.synthetic.main.activity_sessions.*
import kotlinx.android.synthetic.main.sessiontect.*
import kotlinx.android.synthetic.main.sessiontect.view.*
import java.lang.Exception

class sessions : AppCompatActivity() {

    var myRef = FirebaseDatabase.getInstance().reference
    var adpt :adptr?=null
    var item_arry = ArrayList<sessionitemsinfo>()
    var useremail :String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_sessions)

        val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
        var qury = pref57.getString("profleinfo", "0")
         useremail=qury

        adpt=adptr(item_arry , this)
        sessionslist.adapter=adpt





        gosettingfsession.setOnClickListener({
            var go = Intent(this, admin::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })
        gohomefsession.setOnClickListener({
            var go = Intent(this, home::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })
        gowalletfsession.setOnClickListener({
            var go = Intent(this, wallet::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)

        })




        myRef.addValueEventListener(object : ValueEventListener {


            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                try {


                    var reserv =
                        p0.child("cliants").child(useremail!!).child("reservations").value as HashMap<String, Any>


                    // Toast.makeText(this@sessions,reserv!!.toString(),Toast.LENGTH_SHORT).show()

                    item_arry.clear()

                    for (reservItem in reserv) {
                        var thisitem = reservItem.value as HashMap<Any, Any>


                        var resveddoctor =
                            p0.child("doctors").child(thisitem["dremail"]!!.toString()).value as HashMap<String, Any>
                        item_arry.add(
                            sessionitemsinfo(
                                thisitem["cliantid"].toString(),
                                resveddoctor["name"].toString(),
                                resveddoctor["price"].toString(),
                                resveddoctor["speciality"].toString(),
                                resveddoctor["covern"].toString() + " " + resveddoctor["city"].toString(),
                                thisitem["time"].toString(),
                                thisitem["method"].toString(),
                                thisitem["stutes"].toString()

                            )
                        )


                    }

                    adpt!!.notifyDataSetChanged()
                }catch (e :Exception){

                }

            }

        })

    }


    inner class adptr : BaseAdapter {

        var item_arry=ArrayList<sessionitemsinfo>()
        var con : Context?=null


        constructor(item_arry:ArrayList<sessionitemsinfo> , con : Context){
            this.item_arry=item_arry
            this.con=con

        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val one_item=item_arry[p0]

            val gettect = con!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater




            if (one_item.stutes=="1") {

                val tect = gettect.inflate(R.layout.sessiontect, null)


                var font2 = Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
                var font3 = Typeface.createFromAsset(assets, "Raleway-Light.ttf")
                var font5 = Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")
                var font6 = Typeface.createFromAsset(assets, "Raleway-Medium.ttf")


                tect.drpayname1.setTypeface(font6)
                tect.drpaylocation1.setTypeface(font6)
                tect.drpayprice1.setTypeface(font6)
                tect.drpayspec1.setTypeface(font6)
                tect.drpayday1.setTypeface(font6)
                tect.paymentmathod.setTypeface(font6)
                tect.reversID.setTypeface(font2)

                tect.drpayname1.text = one_item.dtname
                tect.drpaylocation1.text = one_item.loction
                tect.drpayprice1.text = one_item.price + " LE"
                tect.drpayspec1.text = one_item.speslize
                tect.drpayday1.text = "Turn Number " + (one_item.turn!!.split(" ")[0].toInt()+1).toString() + " in " + one_item.turn!!.split(" ")[1]
                tect.paymentmathod.text = "Confirmed, Payed by " + one_item.method

                tect.reversID.text = "Reservation Code : " + one_item.resevid

                var isOpened = 0

                tect.alldrinfo.setOnClickListener({
                    if (isOpened ==0){
                        tect.alldrdetails.visibility=View.VISIBLE
                        isOpened=1
                    }else{
                        tect.alldrdetails.visibility=View.GONE
                        isOpened=0
                    }
                })




                tect.cancelReserviotion.setOnClickListener({
                    myRef.child("reservations").child(one_item.resevid!!).removeValue()
                    myRef.child("cliants").child(useremail!!).child("reservations").child(one_item.resevid!!).removeValue()

                    myRef.child("cliants").child(useremail!!).child("balance").addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            myRef.child("cliants").child(useremail!!).child("balance").setValue((p0.value.toString().toInt()+one_item.price!!.toInt()).toString())
                        }

                    })

                })

                return tect

            }
            else{

                val tect = gettect.inflate(R.layout.sessiontectnotpayed, null)

                var font2 = Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
                var font3 = Typeface.createFromAsset(assets, "Raleway-Light.ttf")
                var font5 = Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")
                var font6 = Typeface.createFromAsset(assets, "Raleway-Medium.ttf")


                tect.drpayname1.setTypeface(font6)
                tect.drpaylocation1.setTypeface(font6)
                tect.drpayprice1.setTypeface(font6)
                tect.drpayspec1.setTypeface(font6)
                tect.drpayday1.setTypeface(font6)
                tect.paymentmathod.setTypeface(font6)
                tect.reversID.setTypeface(font2)

                tect.drpayname1.text = one_item.dtname
                tect.drpaylocation1.text = one_item.loction
                tect.drpayprice1.text = one_item.price + " LE"
                tect.drpayspec1.text = one_item.speslize

                tect.drpayday1.text =  "Turn Number " + one_item.turn!!.split(" ")[0] + " in " + one_item.turn!!.split(" ")[1]


                tect.paymentmathod.text = "NOT Confirmed, Please payed by " + one_item.method

                tect.reversID.text = "Reservation Code : " + one_item.resevid


                var isOpened = 0
                tect.alldrinfo.setOnClickListener({
                    if (isOpened ==0){
                        tect.alldrdetails.visibility=View.VISIBLE
                        isOpened=1
                    }else{
                        tect.alldrdetails.visibility=View.GONE
                        isOpened=0
                    }
                })


                return tect


            }








        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return item_arry.size
        }


    }







}
