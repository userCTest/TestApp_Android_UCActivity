package com.usercentrics.test_app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.usercentrics.sdk.UsercentricsActivity
import com.usercentrics.sdk.models.common.UserOptions


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setingsIds: cmp v1 - egLMgjg9j
        //             cmp v2 - Insi1P6_p
        //                      3NdtofNZ6
        //                      eE6nmpe4v
        //                      tbdaymyIU
        val settingsId = "o_i8tHKTp"

        val bar: android.app.ActionBar? = getActionBar()
        bar?.setTitle("SettingsId - $settingsId")

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
}