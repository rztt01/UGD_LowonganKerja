package com.example.lat_ugd1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.adapters.WorkAdapter
import com.example.lat_ugd1.api.WorkApi
import com.example.lat_ugd1.models.Work
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class WorkActivity : AppCompatActivity() {
    private var srWork: SwipeRefreshLayout? = null
    private var adapter: WorkAdapter? = null
    private var svWork: SearchView? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    companion object {
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)

        queue = Volley.newRequestQueue(this)
        layoutLoading = findViewById(R.id.layout_loading)
        srWork = findViewById(R.id.sr_work)
        svWork = findViewById(R.id.sv_work)

        srWork?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allWork() })
        svWork?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
            val i = Intent(this@WorkActivity, AddEditWorkActivity::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }

        val rvProduk = findViewById<RecyclerView>(R.id.rv_work)
        adapter = WorkAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager( this)
        rvProduk.adapter = adapter
        allWork()
    }

    private fun allWork(){
        srWork!!.isRefreshing = true
        val stringRequest: StringRequest = object : StringRequest(Method.GET, WorkApi.GET_ALL_URL, Response.Listener { response ->
            val gson = Gson()
            var work : Array<Work> = gson.fromJson(response, Array<Work>::class.java)

            adapter!!.setWorkList(work)
            adapter!!.filter.filter(svWork!!.query)
            srWork!!.isRefreshing = false

            if (!work.isEmpty())
                FancyToast.makeText(this@WorkActivity, "Data Berhasil Diambil", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS,false).show()
            else
                FancyToast.makeText(this@WorkActivity, "Data Kosong", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()


        }, Response.ErrorListener { error ->
            srWork!!.isRefreshing = false
            try {
                val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                val error = JSONObject(responseBody)
                FancyToast.makeText(
                    this@WorkActivity,
                    error.getString("message"),
                    FancyToast.LENGTH_SHORT,
                    FancyToast.SUCCESS,false
                ).show()
            } catch (e: Exception){
                FancyToast.makeText(this@WorkActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
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

    fun deleteWork(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, WorkApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var work = gson.fromJson(response, Work::class.java)
                if (work!=null)
                    FancyToast.makeText(this@WorkActivity, "Data Berhasil Dihapus", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                allWork()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val error = JSONObject(responseBody)
                    FancyToast.makeText(
                        this@WorkActivity,
                        error.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } catch (e: java.lang.Exception){
                    FancyToast.makeText(this@WorkActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
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
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allWork()
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