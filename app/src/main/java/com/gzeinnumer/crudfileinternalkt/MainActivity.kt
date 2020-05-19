package com.gzeinnumer.crudfileinternalkt

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val enterText = findViewById<EditText>(R.id.enterText)

        findViewById<View>(R.id.save).setOnClickListener {
            if (enterText.text.toString().isNotEmpty()) {
                makeFile(enterText.text.toString())
            } else {
                enterText.requestFocus()
                enterText.error = "Tidak Boleh Kosong"
            }
        }

        val readText = findViewById<EditText>(R.id.readText)
        findViewById<View>(R.id.read).setOnClickListener { readText.setText(readFile()) }

        findViewById<View>(R.id.delete).setOnClickListener {
            if (deleteFile()) {
                enterText.setText("")
                readText.setText("")
                Toast.makeText(this@MainActivity, "Success hapus file", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Gagal hapus file", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun makeFile(text: String) {
        val file = File(this@MainActivity.filesDir, "text")
        if (!file.exists()) {
            file.mkdir()
        }
        try {
            val gpxfile = File(file, "sample")
            val writer = FileWriter(gpxfile)
            writer.append(text)
            writer.flush()
            writer.close()
            Toast.makeText(this@MainActivity, "Saved your text", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "Add text to file " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun readFile(): String {
        var myData = ""
        val myExternalFile = File(this@MainActivity.filesDir.toString() + "/text", "sample.txt")
        try {
            val fis = FileInputStream(myExternalFile)
            val `in` = DataInputStream(fis)
            val br = BufferedReader(InputStreamReader(`in`))
            var strLine: String
            while (br.readLine().also { strLine = it } != null) {
                myData = "$myData$strLine".trimIndent()
            }
            br.close()
            `in`.close()
            fis.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this@MainActivity, "Read text to file " + e.message, Toast.LENGTH_SHORT).show()
        }
        return myData
    }

    private fun deleteFile(): Boolean {
        val file = File(this@MainActivity.filesDir.toString() + "/text", "sample.txt")
        return file.delete()
    }
}
