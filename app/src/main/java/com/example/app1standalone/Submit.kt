package com.example.app1standalone

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Submit : AppCompatActivity() {
    private var tvsignedIn : TextView? = null
    private var firstname : String? = null
    private var lastname : String? = null
    private var signedInString :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit)

        val receivedIntent = intent
        firstname = receivedIntent.getStringExtra("FN_STRING")
        lastname = receivedIntent.getStringExtra("LN_STRING")
        tvsignedIn = findViewById(R.id.tv_signedin)
        signedInString = "$firstname $lastname is signed in"
        tvsignedIn!!.text = signedInString

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)


        outState.putString("SI_TEXT", signedInString)

    }
    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        //Restore
        tvsignedIn!!.text = savedInstanceState.getString("SI_TEXT")



    }

}