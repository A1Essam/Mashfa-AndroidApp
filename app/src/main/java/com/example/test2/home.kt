package com.example.test2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.tect.view.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class home : AppCompatActivity() {

    var item_arry = ArrayList<doctorinfo>()
    var myRef = FirebaseDatabase.getInstance().reference
    var adpt : adptr?=null
    var mapofspiner = HashMap<String, String>()
    var useremail:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_home)




        val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
        var qury = pref57.getString("profleinfo", "0")
        useremail=qury


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


        mapofspiner["placetype"]="clinic"

        speciality()
        covernAndCity()

        hosbtn.setOnClickListener({
            mapofspiner["placetype"]="hospital"

            hosbtn.setBackgroundResource(R.drawable.signinenabled)
            clibtn.setBackgroundResource(R.drawable.edittextafterwrite)

            hosbtn.setTextColor(Color.parseColor("#ffffff"))
            clibtn.setTextColor(Color.parseColor("#a800ff"))

            item_arry.clear()
            adpt!!.notifyDataSetChanged()
            load()

            bookdaymenu.visibility=View.GONE


        })

        clibtn.setOnClickListener({
            mapofspiner["placetype"]="clinic"
            clibtn.setBackgroundResource(R.drawable.signinenabled)
            hosbtn.setBackgroundResource(R.drawable.edittextafterwrite)

            clibtn.setTextColor(Color.parseColor("#ffffff"))
            hosbtn.setTextColor(Color.parseColor("#a800ff"))

            item_arry.clear()
            adpt!!.notifyDataSetChanged()

            load()

            bookdaymenu.visibility=View.GONE


        })




        adpt=adptr(item_arry , this)
        drlist.adapter=adpt

        mapofspiner["placetype"]="clinic"


        load()




    }

    var out = 0

    fun load(){


        out = 0
        myRef.child("doctors").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                    item_arry.clear()
                    var tb = p0.value as HashMap<String,Any>
                    for (key in tb.keys){
                        var oneitm = tb[key] as HashMap<String, Any>
                        var dayslimts = oneitm["dayLimits"] as HashMap<String, String>


                    if (mapofspiner?.get("covern")=="Governorate"){

                        if ( oneitm["placetype"] == mapofspiner?.get("placetype")
                            && oneitm["speciality"] == mapofspiner?.get("speciality")
                        ) {

                            item_arry.add(
                                doctorinfo(
                                    oneitm["name"].toString()
                                    , oneitm["email"].toString()
                                    , oneitm["speciality"].toString()
                                    , oneitm["covern"].toString()
                                    , oneitm["city"].toString()
                                    , oneitm["placetype"].toString()
                                    , oneitm["price"].toString()

                                    , drAvalibleDays( dayslimts["friday"].toString() , dayslimts["saturday"].toString()
                                    ,dayslimts["sunday"].toString() ,dayslimts["monday"].toString()
                                    ,dayslimts["tuesday"].toString() ,dayslimts["wednesday"].toString()
                                    ,dayslimts["thursday"].toString())
                                )

                            )

                        }

                    }
                    else if(mapofspiner?.get("city")=="city"){

                        if ( oneitm["placetype"] == mapofspiner?.get("placetype")
                            && oneitm["covern"] == mapofspiner?.get("covern")
                            && oneitm["speciality"] == mapofspiner?.get("speciality")
                        ) {

                            item_arry.add(
                                doctorinfo(
                                    oneitm["name"].toString()
                                    , oneitm["email"].toString()
                                    , oneitm["speciality"].toString()
                                    , oneitm["covern"].toString()
                                    , oneitm["city"].toString()
                                    , oneitm["placetype"].toString()
                                    , oneitm["price"].toString()
                                    , drAvalibleDays( dayslimts["friday"].toString() , dayslimts["saturday"].toString()
                                        ,dayslimts["sunday"].toString() ,dayslimts["monday"].toString()
                                        ,dayslimts["tuesday"].toString() ,dayslimts["wednesday"].toString()
                                        ,dayslimts["thursday"].toString())
                                )
                            )

                        }

                    }
                    else {
                        if (oneitm["city"] == mapofspiner?.get("city")
                            && oneitm["covern"] == mapofspiner?.get("covern")
                            && oneitm["placetype"] == mapofspiner?.get("placetype")
                            && oneitm["speciality"] == mapofspiner?.get("speciality")

                        ) {

                            item_arry.add(
                                doctorinfo(
                                    oneitm["name"].toString()
                                    , oneitm["email"].toString()
                                    , oneitm["speciality"].toString()
                                    , oneitm["covern"].toString()
                                    , oneitm["city"].toString()
                                    , oneitm["placetype"].toString()
                                    , oneitm["price"].toString()
                                    , drAvalibleDays( dayslimts["friday"].toString() , dayslimts["saturday"].toString()
                                        ,dayslimts["sunday"].toString() ,dayslimts["monday"].toString()
                                        ,dayslimts["tuesday"].toString() ,dayslimts["wednesday"].toString()
                                        ,dayslimts["thursday"].toString())
                                )
                            )

                        }
                    }
                    }

                adpt!!.notifyDataSetChanged()

            }

        })



    }

    fun speciality(){

        var specialityarray = arrayOf("Neurology","Pediatrics","Orthopedics")

        var adapter = ArrayAdapter(
            this@home,
            android.R.layout.simple_spinner_item,
            specialityarray
        )

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        specialityspinner.adapter = adapter;

        specialityspinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                mapofspiner["speciality"]=parent.getItemAtPosition(position).toString()
                load()
                bookdaymenu.visibility=View.GONE


            }
            override fun onNothingSelected(parent: AdapterView<*>){
            }
        }

    }

    fun covernAndCity(){
        val colors = arrayOf("Governorate","Cairo","Dakahlia","Beni Suef")
        var adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            colors // Array
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        mohafzatspinner.adapter = adapter;
        mohafzatspinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                mapofspiner["covern"]=parent.getItemAtPosition(position).toString()
                load()

                if (parent.getItemAtPosition(position).toString() =="Governorate") {
                    cityspinnerlayout.visibility=View.GONE
                }else{
                    cityspinnerlayout.visibility=View.VISIBLE
                }

                    if (parent.getItemAtPosition(position).toString() =="Cairo"){

                    var dakhlia = arrayOf("city","Abdeen","Maadi","El Shorouk","Zamalek")

                    // Initializing an ArrayAdapter
                    var adapter2 = ArrayAdapter(
                        this@home, // Context
                        android.R.layout.simple_spinner_item, // Layout
                        dakhlia // Array
                    )

                    // Set the drop down view resource
                    adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

                    // Finally, data bind the spinner object with dapter
                    cityspiner.adapter = adapter2;

                    // Set an on item selected listener for spinner object
                    cityspiner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                            // Display the selected item text on text view

                            mapofspiner["city"]=parent.getItemAtPosition(position).toString()
                            load()

                        }

                        override fun onNothingSelected(parent: AdapterView<*>){
                            // Another interface callback
                        }
                    }
                }

                if (parent.getItemAtPosition(position).toString() =="Dakahlia"){

                    var dakhlia = arrayOf("city","El Mansoura","Dikirnis","Shirbin")

                    // Initializing an ArrayAdapter
                    var adapter2 = ArrayAdapter(
                        this@home, // Context
                        android.R.layout.simple_spinner_item, // Layout
                        dakhlia // Array
                    )

                    // Set the drop down view resource
                    adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

                    cityspiner.adapter = adapter2;

                    cityspiner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                            mapofspiner["city"]=parent.getItemAtPosition(position).toString()
                            load()
                            bookdaymenu.visibility=View.GONE

                        }

                        override fun onNothingSelected(parent: AdapterView<*>){
                        }
                    }
                }

                if (parent.getItemAtPosition(position).toString() =="Beni Suef"){

                    var dakhlia = arrayOf("city","Sumusta","Nasser")

                    // Initializing an ArrayAdapter
                    var adapter2 = ArrayAdapter(
                        this@home, // Context
                        android.R.layout.simple_spinner_item, // Layout
                        dakhlia // Array
                    )

                    // Set the drop down view resource
                    adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

                    // Finally, data bind the spinner object with dapter
                    cityspiner.adapter = adapter2;

                    // Set an on item selected listener for spinner object
                    cityspiner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){

                            mapofspiner["city"]=parent.getItemAtPosition(position).toString()
                            load()
                            bookdaymenu.visibility=View.GONE

                        }

                        override fun onNothingSelected(parent: AdapterView<*>){
                            // Another interface callback
                        }
                    }
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>){
            }
        }

    }


    inner class adptr : BaseAdapter {

        var item_arry=ArrayList<doctorinfo>()
        var con : Context?=null


        constructor(item_arry:ArrayList<doctorinfo> , con : Context){
            this.item_arry=item_arry
            this.con=con

        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

            val one_item=item_arry[p0]

            val gettect = con!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val tect = gettect.inflate(R.layout.tect, null)


            tect.tectname.text=one_item.name

            var isSelected= 0

            tect.setOnClickListener({

                if (out == 1 && isSelected==0){

                 Toast.makeText(this@home,"You Can't Select two doctor at one time!",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }


                if (isSelected==0) {



                    tect.tectbg.setBackgroundResource(R.drawable.doctorbgtectselected)
                    tect.tectname.setTextColor(Color.parseColor("#ffffff"))
                    tect.tectimg.setImageResource(R.drawable.untitled2)
                    isSelected=1
                    out=1



                    var Drday =""
                    var dayofdr = ArrayList<String>()
                    var day = one_item.dayLimits!!.Friday!!.split(" ")
                    if (day[0].toInt()>day[1].toInt()){
                        dayofdr!!.add("Turn " +(day[1].toInt()+1).toString() + " in " + "Friday")
                    }
                     day = one_item.dayLimits!!.Monday!!.split(" ")
                    if (day[0].toInt()>day[1].toInt()){
                        dayofdr!!.add("Turn " + (day[1].toInt()+1).toString() + " in " +  "Monday")
                    }
                     day = one_item.dayLimits!!.Saturday!!.split(" ")
                    if (day[0].toInt()>day[1].toInt()){
                        dayofdr!!.add("Turn " + (day[1].toInt()+1).toString() + " in " + "Saturday")
                    }
                     day = one_item.dayLimits!!.Sunday!!.split(" ")
                    if (day[0].toInt()>day[1].toInt()){
                        dayofdr!!.add("Turn " + (day[1].toInt()+1).toString() + " in " +  "Sunday")
                    }
                     day = one_item.dayLimits!!.Thursday!!.split(" ")
                    if (day[0].toInt()>day[1].toInt()){
                        dayofdr!!.add("Turn " + (day[1].toInt()+1).toString() + " in " +  "Thursday")
                    }
                     day = one_item.dayLimits!!.Tuesday!!.split(" ")
                    if (day[0].toInt()>day[1].toInt()){
                        dayofdr!!.add("Turn " + (day[1].toInt()+1).toString() + " in " + "Tuesday")
                    }
                     day = one_item.dayLimits!!.Wednesday!!.split(" ")
                    if (day[0].toInt()>day[1].toInt()){
                        dayofdr!!.add("Turn " + (day[1].toInt()+1).toString() + " in " +  "Wednesday")
                    }

                    var adapter = ArrayAdapter(
                        this@home,
                        android.R.layout.simple_spinner_item,
                        dayofdr!!
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                    AvaDaySpinner.adapter = adapter;
                    AvaDaySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                        override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){


                            Drday=parent.getItemAtPosition(position).toString()

                        }
                        override fun onNothingSelected(parent: AdapterView<*>){
                        }
                    }



                    bookdr.setOnClickListener({

                        var go = Intent(this@home , paymentMethod::class.java)

                        go.putExtra("dremail",one_item.email)
                        go.putExtra("day",Drday + " " +day[0])
                        go.putExtra("useremail",useremail)

                        startActivity(go)

                    })


                    bookprice.text=one_item.price+ " LE"
                    bookdaymenu.visibility=View.VISIBLE



                }
                else{
                    tect.tectbg.setBackgroundResource(R.drawable.doctortectbg)
                    tect.tectname.setTextColor(Color.parseColor("#a800ff"))
                    tect.tectimg.setImageResource(R.drawable.untitled1)
                    out=0
                    isSelected=0

                    bookdaymenu.visibility=View.GONE

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


    fun font(){
        var font1= Typeface.createFromAsset(assets, "Raleway-Black.ttf")
        var font2= Typeface.createFromAsset(assets, "Raleway-Bold.ttf")
        var font3= Typeface.createFromAsset(assets, "Raleway-Light.ttf")
        var font4= Typeface.createFromAsset(assets, "Raleway-Thin.ttf")
        var font5= Typeface.createFromAsset(assets, "Raleway-SemiBold.ttf")



    }

}
