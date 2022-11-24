package com.example.lat_ugd1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.adapters.InterviewAdapter
import com.example.lat_ugd1.api.InterviewApi
import com.example.lat_ugd1.models.Interview
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class InterviewActivity : AppCompatActivity() {
    private var srInterview: SwipeRefreshLayout? = null
    private var adapter: InterviewAdapter? = null
    private var svInterview: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srInterview = findViewById(R.id.sr_interview)
        svInterview = findViewById(R.id.sv_interview)

        srInterview?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allInterview() })
        svInterview?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener {
            val i = Intent(this@InterviewActivity, AddEditInterviewActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_interview)
        adapter = InterviewAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager( this)
        rvProduk.adapter = adapter
        allInterview()
    }

    private fun allInterview(){
        srInterview!!.isRefreshing = true
        val stringRequest: StringRequest = object : StringRequest(Method.GET, InterviewApi.GET_ALL_URL, Response.Listener { response ->
            val gson = Gson()
            var interview : Array<Interview> = gson.fromJson(response, Array<Interview>::class.java)

            adapter!!.setInterviewList(interview)
            adapter!!.filter.filter(svInterview!!.query)
            srInterview!!.isRefreshing = false

            if (!interview.isEmpty())
                FancyToast.makeText(this@InterviewActivity, "Data Berhasil Diambil", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show()
            else
                FancyToast.makeText(this@InterviewActivity, "Data Kosong", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()


        }, Response.ErrorListener { error ->
            srInterview!!.isRefreshing = false
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val error = JSONObject(responseBody)
                FancyToast.makeText(
                    this@InterviewActivity,
                    error.getString("message"),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,false
                ).show()
            } catch (e: Exception){
                FancyToast.makeText(this@InterviewActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
            }
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    fun deleteInterview(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, InterviewApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var interview = gson.fromJson(response, Interview::class.java)
                if (interview!=null)
                    FancyToast.makeText(this@InterviewActivity, "Data Berhasil Dihapus", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                allInterview()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val error = JSONObject(responseBody)
                    FancyToast.makeText(
                        this@InterviewActivity,
                        error.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } catch (e: java.lang.Exception){
                    FancyToast.makeText(this@InterviewActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allInterview()
    }

    // Fungsi ini digunakan menampilkan layout loading
    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            layoutLoading!!.visibility = View.VISIBLE
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.visibility = View.INVISIBLE
        }
    }
}