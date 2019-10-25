package com.example.test2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.util.Pair
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_paybythegroupwallet.*
import kotlinx.android.synthetic.main.walletgrouptect.view.*

class paybythegroupwallet : AppCompatActivity() {

    var myRef = FirebaseDatabase.getInstance().reference
    var adpt :adptr?=null
    var item_arry = ArrayList<Pair<String,String>>()
    var amount :String?=null
    var useremail :String?=null
    var dremail :String?=null
    var dayname :String?=null
    var turn :String?=null
    var maxturns :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_paybythegroupwallet)



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



        val bundl=intent.extras

        val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
        var qury = pref57.getString("profleinfo", "0")
        useremail=qury


        amount=bundl!!.getString("amount")
        dremail=bundl!!.getString("dremail")
        dayname=bundl!!.getString("dayname")
        turn=bundl!!.getString("turn")
        maxturns=bundl!!.getString("maxturn")


        adpt=adptr(item_arry , this)
        groubwalletlist.adapter=adpt



        myRef.child("cliants").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
               var data = p0.child(useremail!!).child("onWalletof").value.toString()
                item_arry.clear()

                for (x in data.split(",")){
                    if (x!="") {

                        var mail = x
                        var name = p0.child(mail).child("firstName").value.toString() +" "+ p0.child(mail).child("secondName").value.toString()
                        item_arry.add(Pair(mail!!,name!!))
                    }
                }

                adpt!!.notifyDataSetChanged()
            }

        })






    }

    var out = 0
    inner class adptr : BaseAdapter {

        var item_arry=ArrayList<Pair<String,String>>()
        var con : Context?=null


        constructor(item_arry:ArrayList<Pair<String,String>> , con : Context){
            this.item_arry=item_arry
            this.con=con

        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val one_item=item_arry[p0]

            val gettect = con!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater




                val tect = gettect.inflate(R.layout.walletgrouptect, null)


                var font2 = Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
                var font3 = Typeface.createFromAsset(assets, "Raleway-Light.ttf")
                var font5 = Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")
                var font6 = Typeface.createFromAsset(assets, "Raleway-Medium.ttf")

                pfprice.setTypeface(font2)
            pfhead1.setTypeface(font3)
            pfwnae.setTypeface(font2)



                tect.walletname.text=one_item.second

                var isopened =0
            tect.setOnClickListener({


                if (out==1 && isopened==0){
                    Toast.makeText(this@paybythegroupwallet,"Can't select two iteams in one time!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if (isopened==0) {
                    poppay.visibility = View.VISIBLE
                    pfwnae.text = one_item.second
                    pfprice.text = amount +" LE"
                    balancentenoght.visibility=View.GONE

                    tect.tectwalletbg.setBackgroundResource(R.drawable.doctorbgtectselected)
                    tect.txthead.setTextColor(Color.parseColor("#ffffff"))
                    tect.walletname.setTextColor(Color.parseColor("#ffffff"))
                    tect.imggrop.setImageResource(R.drawable.wait_group)


                    paybywalletbtn.setOnClickListener({
                        myRef.addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onCancelled(p0: DatabaseError) {
                            }

                            override fun onDataChange(p0: DataSnapshot) {

                                var walletbalnce= p0.child("cliants").child(one_item.first!!).child("balance").value.toString()
                                var reservationsID = p0.child("cliantID").value.toString()
                                if (walletbalnce.toInt()>=amount!!.toInt()){

                                    myRef.child("cliants").child(one_item.first!!).child("balance").setValue((walletbalnce.toInt()-amount!!.toInt()).toString())


                                    myRef.child("cliants").child(useremail!!).child("reservations").child(reservationsID)
                                        .setValue(sessionInfo(reservationsID, "1","Group Wallet",turn+" "+dayname,mailwithoutdots(dremail!!),useremail!!))


                                    myRef.child("reservations").child(reservationsID)
                                        .setValue(sessionInfo(reservationsID, "1","Group Wallet",turn+" "+dayname,mailwithoutdots(dremail!!),useremail!!))


                                    myRef.child("cliantID").setValue((reservationsID.toInt() + 1).toString())

                                    myRef.child("doctors").child(mailwithoutdots(dremail!!)).child("dayLimits")
                                        .child(dayname!!.toLowerCase())
                                        .setValue(maxturns + " " + (turn!!.toInt() + 1).toString())


                                    var go = Intent(this@paybythegroupwallet, sessions::class.java)
                                    go.putExtra("useremail",useremail)
                                    startActivity(go)
                                }else{
                                    balancentenoght.visibility=View.VISIBLE
                                }
                            }

                        })
                    })

                    out=1
                    isopened=1

                }else{
                    balancentenoght.visibility=View.GONE

                    poppay.visibility = View.GONE

                    tect.tectwalletbg.setBackgroundResource(R.drawable.doctortectbg)
                    tect.txthead.setTextColor(Color.parseColor("#d686ff"))
                    tect.walletname.setTextColor(Color.parseColor("#a800ff"))
                    tect.imggrop.setImageResource(R.drawable.groupwalleticon)

                    out=0
                    isopened=0
                }
            })




                return tect












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


    fun mailwithoutdots( mail:String):String{
        var result =""
        for (x in mail){
            if(x!='.')result+=x
        }
        return result
    }

}
