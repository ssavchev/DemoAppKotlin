package com.snibble.demoappkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


class MainActivity : AppCompatActivity() {

    class APIColor {
        val red: Float = 0.0f;
        val green: Float = 0.0f;
        val blue: Float = 0.0f;
        val alpha: Float = 0.0f;
    }

    class APIText {
        val text: String = "";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun callAPIColor() {
        val entireJson = URL("http://51.79.20.91/0ss/color.php").readText()

        val mapper = jacksonObjectMapper()

        val color = mapper.readValue<APIColor>(entireJson)
    }
}
