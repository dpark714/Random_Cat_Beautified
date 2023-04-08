package com.driuft.random_cats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var petList: MutableList<Pet>
    private lateinit var rvPets: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPets = findViewById(R.id.pet_list)
        petList = mutableListOf()
        getCatData()
    }


    private fun getCatData() {
        val client = AsyncHttpClient()
        val url = "https://api.thecatapi.com/v1/images/search?limit=20&api_key=live_BrqdVy2YRcwwNeAWi9yLUE1FjFZ6kuWUREh1TGmYd4EmW1WeUm7wql1lmGUp75He"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Cat Success", "$json")

                val petArray = json.jsonArray
                for(i in 0 until petArray.length()){
                    val petObject = petArray.getJSONObject(i)
                    val breedArray = petObject.getJSONArray("breeds")
                    if (breedArray.length() > 0) { // check if there's at least one breed object
                        val breedObject = breedArray.getJSONObject(0) // we'll only use the first breed object
                        val name = breedObject.getString("name")
                        val origin = breedObject.getString("origin")
                        val imageUrl = petObject.getString("url")
                        petList.add(Pet(name, origin, imageUrl))
                    }
                }
                Log.d("petList","petList $petList")

                val adapter = PetAdapter(petList)
                rvPets.adapter = adapter
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Cat Error", errorResponse)
            }

})
}
}