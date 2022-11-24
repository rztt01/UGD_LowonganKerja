package com.example.lat_ugd1

import android.content.Intent
import android.os.Bundle
import android.text.Spannable.Factory
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.api.InterviewApi
import com.example.lat_ugd1.models.Interview
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditInterviewActivity : AppCompatActivity() {
    companion object{
        private val GAJI_LIST = arrayOf(
            "Rp500.000,00 - Rp1.500.000,00",
            "Rp1.500.000,00 - Rp3.000.000,00",
            "Rp3.000.000,00 - Rp6.000.000,00",
            "Rp6.000.000,00 - Rp10.000.000,00",
            "Rp10.000.000,00 - Rp15.000.000,00",
            "Rp15.000.000,00 +......."
        )
    }

    private var etPerusahaan: EditText? = null
    private var etRole: EditText? = null
    private var edGaji: AutoCompleteTextView? = null
    private var etDomisili: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_interview)

        //Pendeklarasian request queue
        queue = Volley.newRequestQueue(this)
        etPerusahaan = findViewById(R.id.et_perusahaan)
        etRole = findViewById(R.id.et_role)
        edGaji = findViewById(R.id.ed_gaji)
        etDomisili = findViewById(R.id.et_domisili)
        layoutLoading = findViewById(R.id.layout_loading)

        setExposedDropDownMenu()

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Interview")
            btnSave.setOnClickListener { createInterview() }
        } else {
            tvTitle.setText("Edit Interview")
            getInterviewById(id)

            btnSave.setOnClickListener { updateInterview(id) }
        }
    }

    fun setExposedDropDownMenu() {
        val adapterGaji: ArrayAdapter<String> = ArrayAdapter<String>( this,
            R.layout.item_list, GAJI_LIST)
        edGaji!!.setAdapter(adapterGaji)
    }

    private fun getInterviewById(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, InterviewApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val interview = gson.fromJson(response, Interview::class.java)

                etPerusahaan!!.setText(interview.perusahaan)
                etRole!!.setText(interview.role)
                edGaji!!.setText(interview.gaji)
                etDomisili!!.setText(interview.domisili)
                setExposedDropDownMenu()

                FancyToast.makeText(this@AddEditInterviewActivity, "Data berhasil diambil!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this@AddEditInterviewActivity,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,false
                    ).show()
                } catch (e: Exception) {
                    FancyToast.makeText(this@AddEditInterviewActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
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

    private fun createInterview() {
        setLoading(true)

        val interview = Interview(
            etPerusahaan!!.text.toString(),
            etRole!!.text.toString(),
            edGaji!!.text.toString(),
            etDomisili!!.text.toString(),
        )

        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, InterviewApi.ADD_URL, Response.Listener { response ->
                val gson = Gson()
                val interview = gson.fromJson(response, Interview::class.java)

                if (interview != null)
                    FancyToast.makeText(
                        this@AddEditInterviewActivity,
                        "Data Berhasil Ditambahkan",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,false
                    ).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText( this@AddEditInterviewActivity,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,false
                    ).show()
                } catch (e: Exception) {
                    FancyToast.makeText(this@AddEditInterviewActivity, e.message, FancyToast.LENGTH_SHORT,FancyToast.ERROR, false).show()
                }
            }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(interview)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        // Menambahkan request ke request queue
        queue!!.add(stringRequest)
    }

    private fun updateInterview(id: Long) {
        setLoading(true)

        val interview = Interview(
            etPerusahaan!!.text.toString(),
            etRole!!.text.toString(),
            edGaji!!.text.toString(),
            etDomisili!!.text.toString(),
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, InterviewApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var interview = gson.fromJson(response, Interview::class.java)

                if (interview != null)
                    FancyToast.makeText(
                        this@AddEditInterviewActivity,
                        "Data berhasil diupdate",
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,false
                    ).show()

                val returnIntent = Intent()
                setResult(RESULT_OK, returnIntent)
                finish()

                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)
                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this@AddEditInterviewActivity,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } catch (e: Exception) {
                    FancyToast.makeText(this@AddEditInterviewActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val gson = Gson()
                val requestBody = gson.toJson(interview)
                return requestBody.toByteArray(StandardCharsets.UTF_8)
            }

            override fun getBodyContentType(): String {
                return "application/json"
            }
        }
        queue!!.add(stringRequest)
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