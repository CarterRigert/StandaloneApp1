package com.example.app1standalone

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    //variables for name
    private var firstName: String? = null
    private var lastName: String? = null
    private var middleName: String? = null
    //Variables for Ui elements
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etMiddleName: EditText? = null
    private var submitButton: Button? = null
    private var cameraButton: Button? = null

    //ImageView for profile pic
    private var ivProfile: ImageView? = null
    private var pic: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etFirstName = findViewById<View>(R.id.et_firstname) as EditText
        etMiddleName = findViewById<View>(R.id.et_firstname) as EditText
        etLastName = findViewById<View>(R.id.et_firstname) as EditText
        ivProfile = findViewById<View>(R.id.iv_pic) as ImageView
        //Get buttons
        submitButton = findViewById(R.id.button_submit)
        cameraButton = findViewById(R.id.button_pic)
        //setup listners
        submitButton!!.setOnClickListener(this)
        cameraButton!!.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button_submit -> {
                //get string from the edittexts
                etFirstName = findViewById(R.id.et_firstname)
                etLastName = findViewById(R.id.et_lastname)
                etMiddleName = findViewById(R.id.et_middlename)

                firstName = etFirstName!!.text.toString()
                lastName = etLastName!!.text.toString()
                middleName = etMiddleName!!.text.toString()

                if(firstName.isNullOrBlank() || lastName.isNullOrBlank() || middleName.isNullOrBlank()){
                    Toast.makeText(this@MainActivity, "Enter Your Names before proceeding enter none if you dont have one", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, "Congrats for entering names", Toast.LENGTH_SHORT).show()
                    val messageIntent = Intent(this, Submit::class.java)
                    messageIntent.putExtra("FN_STRING", firstName)
                    messageIntent.putExtra("LN_STRING", lastName)
                    this.startActivity(messageIntent)
                }

            }
            R.id.button_pic -> {
                val cameraInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try{
                    cameraActivity.launch(cameraInt)
                }catch(ex: ActivityNotFoundException){
                    //error
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        firstName = etFirstName!!.text.toString()
        lastName = etLastName!!.text.toString()
        middleName = etMiddleName!!.text.toString()
        //might not work
        if(ivProfile!!.drawable != null) {
            pic = (ivProfile!!.drawable as BitmapDrawable).bitmap
        }

        outState.putString("FN_TEXT", firstName)
        outState.putString("LN_TEXT", lastName)
        outState.putString("MN_TEXT", middleName)
        outState.putParcelable("PIC", pic)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle
    ) {
        super.onRestoreInstanceState(savedInstanceState)
        //Restore
        etFirstName!!.setText(savedInstanceState.getString("FN_TEXT"))
        etLastName!!.setText(savedInstanceState.getString("LN_TEXT"))
        etMiddleName!!.setText(savedInstanceState.getString("MN_TEXT"))
        //might not work
        if(Build.VERSION.SDK_INT >= 33) {
            ivProfile!!.setImageBitmap(savedInstanceState.getParcelable("PIC", Bitmap::class.java))

        }


    }
    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
        if(result.resultCode == RESULT_OK){

            ivProfile = findViewById<View>(R.id.iv_pic) as ImageView
            if(Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                ivProfile!!.setImageBitmap(thumbnailImage)
            }else{
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                ivProfile!!.setImageBitmap(thumbnailImage)
            }

        }
    }
}