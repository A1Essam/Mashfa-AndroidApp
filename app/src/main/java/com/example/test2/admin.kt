package com.example.test2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_admin.*

class admin : AppCompatActivity() {
    var myRef = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_admin)

        val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
        var qury = pref57.getString("profleinfo", "0")
        var useremail=qury

        gohomefsetting.setOnClickListener({
            var go = Intent(this, home::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })

        gosessionfsetting.setOnClickListener({
            var go = Intent(this, sessions::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })

        gowalletfsetting.setOnClickListener({
            var go = Intent(this, wallet::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            go.putExtra("useremail",useremail)
            this.startActivity(go)
        })


        logout.setOnClickListener({

            val pref57 = getSharedPreferences("ActivityPREF5", Context.MODE_PRIVATE)
            val ed57 = pref57.edit()
            ed57.putString("profleinfo", "0")
            ed57.commit()


            var go = Intent(this, login::class.java)
            this.startActivity(go)
        })


        myRef.child("cliants").child(useremail!!).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                var data = p0.value as HashMap<String,String>

                fname.setText(data["firstName"].toString())
                sname.setText(data["secondName"].toString())
                mail.setText(data["email"].toString())





            }

        })














    }
}
