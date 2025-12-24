package com.jthl.morekmptwo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.init
import io.github.vinceglb.filekit.manualFileKitCoreInitialization


var actualInstance: AndroidPlatformService? = null

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        FileKit.manualFileKitCoreInitialization(this)
        setActualPlatformService(this)
        FileKit.init(this)
        setContent {
            App()
        }
    }

    fun setActualPlatformService(context: Context) {
        actualInstance = AndroidPlatformService(context.applicationContext)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}