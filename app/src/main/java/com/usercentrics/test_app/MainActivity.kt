package com.ruimgreis.test_app_ucactivity

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.usercentrics.sdk.*
import com.usercentrics.sdk.models.common.*
import com.usercentrics.sdk.ui.PredefinedUISettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val LOG_TAG = "ViewActivity"
    // var to contain insjected settingsId
    private lateinit var settingsId: String
    // val to contain the activity launcher, to be used to start the UsercentricsActivityLauncher in method
    // showCMP
    private val usercentricsActivityLauncher =
        registerForActivityResult(UsercentricsActivityContract()) { services ->
            Log.d(MainActivity::class.simpleName, "registerForActivityResult services: $services")
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hides soft input keyboard
        input_settings_id.showSoftInputOnFocus = false
        // version input
        txt_version.text =  getVersionName()
        // go button input to get settingsId
        btn_go.setOnClickListener(){ _ ->
            if(input_settings_id.text.toString().isNullOrEmpty()){
                Toast.makeText(this,
                    "Please insert a SettingsId",
                    Toast.LENGTH_LONG).show()
            } else {
                settingsId = input_settings_id.text.toString().trim()
                // Launch CMP
                showCMP(settingsId)
            }
        }

        // btn close app
        val btn_close_app= findViewById<View>(R.id.btn_close_app)
        btn_close_app.setOnClickListener{
            this.finishAffinity()
        }
    }

    /**
     * gets version of SDK being used
     */
    private fun getVersionName(): String {
        val pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0)
        return pInfo.versionName ?: "test"
    }

    // shows CMP, using UsercentricsActivityLauncher
    // https://docs.usercentrics.com/cmp_in_app_sdk/latest/predefined_ui/present/#presenting-the-cmp
    private fun showCMP(settingsId: String) {

        val userOptions = UserOptions(
            controllerId = null,
            defaultLanguage = null,
            version = null,
            predefinedUI = true,
            timeoutMillis = null,
            noCache = null,
            loggerLevel = UCLoggerLevel.DEBUG
        )

//        val customFont = Typeface.createFromAsset(assets, "times_new_roman.ttf") // Font from assets + ttf
//        val customFont = Typeface.createFromAsset(assets, "inter_regular.ttf") // Font from assets + ttf
        // FOR THIS TO WORK, ONE NEEDS TO HAVE THE font/poppins.xml AND values/font_certs.xml FILES
        val customFont = ResourcesCompat.getFont(this, R.font.poppins)!! // Font from internet + xml

        val predefinedUISettings = PredefinedUISettings(
            showCloseButton = false,
           customFont = customFont
        )

        val arguments = UsercentricsActivityArguments(
            settingsId = settingsId,
            userOptions = userOptions,
            predefinedUISettings = predefinedUISettings
        )

        usercentricsActivityLauncher.launch(arguments)
    }
}