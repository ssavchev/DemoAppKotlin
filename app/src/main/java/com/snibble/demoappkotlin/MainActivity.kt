package com.snibble.demoappkotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


class MainActivity : AppCompatActivity() {

    private val handler: Handler = Handler(Looper.getMainLooper())

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

        callAPIColor()
    }

    fun onTestButtonClick(v: View?) {
        callAPIText()
    }

    fun callAPIColor() {
        val thread = Thread {
            try {
                val entireJson = URL("http://51.79.20.91/0ss/color.php").readText()

                val mapper = jacksonObjectMapper()

                val color = mapper.readValue<APIColor>(entireJson)
                updateAPIColor(color)
            } catch (e: Exception) {
                e.printStackTrace()

                updateBackgroundColor(Color.RED);
            }
        }
        thread.start()
    }

    fun callAPIText() {
        val thread = Thread {
            try {
                val entireJson = URL("http://51.79.20.91/0ss/text.php").readText()

                val mapper = jacksonObjectMapper()

                val text = mapper.readValue<APIText>(entireJson)
                updateAPIText(text)
            } catch (e: Exception) {
                e.printStackTrace()

                updateBackgroundColor(Color.RED);
            }
        }
        thread.start()
    }

    fun updateBackgroundColor(color: Int) {
        handler.post {
            this.window.decorView.setBackgroundColor(color)
        }
    }

    fun updateAPIColor(color: APIColor) {
        handler.post {
            this.window.decorView.setBackgroundColor(Color.valueOf(color.red, color.green, color.blue, color.alpha).toArgb())
            this.findViewById<Button>(R.id.testButton).visibility = View.VISIBLE;
        }
    }

    fun updateAPIText(text: APIText) {
        handler.post {
            // Initialize a new instance of
            val builder = AlertDialog.Builder(this@MainActivity)

            // Set the alert dialog title
            builder.setTitle("Response text")

            // Display a message on alert dialog
            builder.setMessage(text.text);

            // Display a neutral button on alert dialog
            builder.setNeutralButton("Close"){_,_ ->
                // TODO
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }
    }
}
