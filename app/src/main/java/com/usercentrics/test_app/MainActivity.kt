package com.ruimgreis.test_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.usercentrics.sdk.*
import com.usercentrics.sdk.models.common.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // setingsIds: cmp v1 - egLMgjg9j
    //             cmp v2 - Insi1P6_p
    //                      3NdtofNZ6
    //                      eE6nmpe4v
    //                      tbdaymyIU
    //             tcf2   - opn4EGC0S
    //             tcf2   - ZDQes7xES
    //             tcf2   - EA4jnNPb9
    //             tcf2   - hKTmJ4UVL
    //             ccpa   - syV5G8hMG
    private lateinit var settingsId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // hides soft input keyboard
        input_settings_id.showSoftInputOnFocus = false

        txt_version.text =  getVersionName()

        btn_go.setOnClickListener(){ _ ->
            if(input_settings_id.text.toString().isNullOrEmpty()){
                Toast.makeText(this,
                    "Please insert a SettingsId",
                    Toast.LENGTH_LONG).show()
            } else {
                settingsId = input_settings_id.text.toString() ?: settingsId
                showCMP(settingsId)
            }
        }

        val btn_close_app= findViewById<View>(R.id.btn_close_app)
        btn_close_app.setOnClickListener{
            this.finishAffinity()
        }
    }

    private fun getVersionName(): String {
        val pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0)
        return pInfo.versionName ?: "test"
}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UsercentricsActivity.REQUEST_CODE &&
            resultCode == UsercentricsActivity.RESULT_OK_CODE
        ) {
            val services = UsercentricsActivity.getResult(data)
            for (service in services) {
                Log.i("Consents: ", service.toString());
            }
        }
    }

    private fun showCMP(settingsId: String) {
        val userOptions = UserOptions(
            controllerId = null,
            defaultLanguage = null,
            version = null,
            debugMode = null,
            predefinedUI = true,
            timeoutMillis = null,
            noCache = null
        )

        UsercentricsActivity.start(this, settingsId, userOptions)
    }
}