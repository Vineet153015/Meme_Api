package com.example.apicalling

import android.app.DownloadManager
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.apicalling.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    val url:String = "https://meme-api.com/gimme"
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        getMemeData()

        binding.memeBtn.setOnClickListener{
            getMemeData()
        }
    }

    fun getMemeData(){

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait til new Meme is fetched")


        val queue = Volley.newRequestQueue(this)

// Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                    Log.e("Response", "getMemeData: " +response.toString())

                val resposeObject = JSONObject(response)


                resposeObject.get("postLink")


                binding.memeTitle.text = resposeObject.getString("title")
                binding.memeAuthor.text = resposeObject.getString("author")
                Glide.with(this@MainActivity).load(resposeObject.get("url")).into(binding.memeImage)
                progressDialog.dismiss()
            },
            { error ->
                Toast.makeText(this,"${error.localizedMessage}",Toast.LENGTH_SHORT).show()

            })

// Add the request to the RequestQueue.
        queue.add(stringRequest)

    }
}