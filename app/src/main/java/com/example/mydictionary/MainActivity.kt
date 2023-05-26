package com.example.mydictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var searchText: EditText
    private lateinit var textView: TextView
    private lateinit var meaningText: TextView
    private lateinit var exampleText: TextView
    private lateinit var originOfWordText: TextView
    private lateinit var textVoice: ImageView
    private lateinit var textAudio: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchText = findViewById(R.id.search_text)
        textView = findViewById(R.id.textView)
        meaningText = findViewById(R.id.meaning_text)
        exampleText = findViewById(R.id.example_text)
        originOfWordText = findViewById(R.id.origin_of_word_text)
        textVoice = findViewById(R.id.text_voice)
        val searchBtn = findViewById<Button>(R.id.search_btn)

        searchBtn.setOnClickListener {
            val searchWord = searchText.text.toString().trim()


            getMeaning(searchWord)

        }
    }

    private fun getMeaning(MainWord: String){

        val uRL = "https://api.dictionaryapi.dev/api/v1/entries/en/$MainWord"



        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, uRL, null,
            { response ->

                try {

                    val jsonObject: JSONObject = response.getJSONObject(0)

                    val word: String = jsonObject.getString("word")
//                    Toast.makeText(this, "yha tk chal rha hai", Toast.LENGTH_LONG).show()

//                    val origin: String = jsonObject.getString("origin")
//                    originOfWordText.text = origin

                    textView.text = word

                    val jsonArray = jsonObject.getJSONArray("phonetics")
                    val jsonObject1 = jsonArray.getJSONObject(0)
                    val url = jsonObject1.getString("audio")




                    val jsonArray1: JSONArray = jsonObject.getJSONArray("meanings")

                    val jsonObject2 = jsonArray1.getJSONObject(0)
                    val jsonArray2 = jsonObject2.getJSONArray("definitions")
                    val jsonObject3 = jsonArray2.getJSONObject(0)
                    val meaning = jsonObject3.getString("definition")


                    textAudio = url

                    meaningText.text = meaning


                }catch (e: Exception){

                }

            },
            {
                // TODO: Handle error
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)

    }

}