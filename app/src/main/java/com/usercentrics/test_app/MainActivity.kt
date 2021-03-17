package com.usercentrics.test_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.usercentrics.sdk.*
import com.usercentrics.sdk.models.common.*


class MainActivity : AppCompatActivity() {

    // setingsIds: cmp v1 - egLMgjg9j
    //             cmp v2 - Insi1P6_p
    //                      3NdtofNZ6
    //                      eE6nmpe4v
    //                      tbdaymyIU
    //             tcf2   - opn4EGC0S
    //             tcf2   - ZDQes7xES
    //             tcf2   - EA4jnNPb9
    //             ccpa   - syV5G8hMG
    private val settingsId = "ZDQes7xES"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userOptions = UserOptions(
            controllerId = null,
            defaultLanguage = null,
            version = null,
            debugMode = null,
            predefinedUI = true,
            timeoutMillis = 30000,
            noCache = null
        )

        UsercentricsActivity.start(this, settingsId, userOptions)

        val btn_close_app= findViewById<View>(R.id.btn_close_app)
        btn_close_app.setOnClickListener{
            this.finishAffinity()
        }
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
}