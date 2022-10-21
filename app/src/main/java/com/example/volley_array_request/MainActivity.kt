package com.example.volley_array_request

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var api_url = "https://api.squiggle.com.au/?q=games"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyRequest = Volley.newRequestQueue(this)

        fetchApi(api_url)

    }



    private fun fetchApi(apiUrl: String) {
        val objectReq = JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            { response: JSONObject ->
                try {

                    var array_data = response.getJSONArray("games")
                    //Log.d("ARRAY IS -->", array_data.toString())
                    for (x in 0 until array_data.length()-1) {

                        var dataObj = array_data.getJSONObject(x)

                        //get the following objects from our api
                        var obj_venue = dataObj.getString("venue")
                        var obj_hteam = dataObj.getString("hteam")
                        Log.d("OBJS-->", obj_venue.toString())

                    }





                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error: VolleyError? ->
                try {

                    Log.d("Error-->", error.toString())

                } catch (e: JSONException) {
                    e.printStackTrace()

                }

            })

            objectReq.retryPolicy =
            DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

        volleyRequest!!.add(objectReq)
    }
}