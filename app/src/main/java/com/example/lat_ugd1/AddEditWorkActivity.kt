package com.example.lat_ugd1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lat_ugd1.api.WorkApi
import com.example.lat_ugd1.models.Work
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class AddEditWorkActivity : AppCompatActivity() {
    companion object{
        private val KATEGORI_LIST = arrayOf(
            "Magang",
            "Studi Independen",
            "Internship",
            "Praktek Kerja Lapangan"
        )
    }

    private var edkategori: AutoCompleteTextView? = null
    private var etnamaPerusahaan: EditText? = null
    private var etposisi: EditText? = null
    private var etlamaKerja: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_work)

        //Pendeklarasian request queue
        queue = Volley.newRequestQueue(this)
        edkategori = findViewById(R.id.ed_kategori)
        etnamaPerusahaan = findViewById(R.id.et_namaPerusahaan)
        etposisi = findViewById(R.id.et_posisi)
        etlamaKerja = findViewById(R.id.et_lamaKerja)
        layoutLoading = findViewById(R.id.layout_loading)

        setExposedDropDownMenu()

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
        val btnSave = findViewById<Button>(R.id.btn_save)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val id = intent.getLongExtra("id", -1)
        if (id == -1L) {
            tvTitle.setText("Tambah Pengalaman Kerja")
            btnSave.setOnClickListener { createWork() }
        } else {
            tvTitle.setText("Edit Pengalaman Kerja")
            getWorkById(id)

            btnSave.setOnClickListener { updateWork(id) }
        }
    }

    fun setExposedDropDownMenu() {
        val adapterKategori: ArrayAdapter<String> = ArrayAdapter<String>( this,
            R.layout.item_list, KATEGORI_LIST)
        edkategori!!.setAdapter(adapterKategori)
    }

    private fun getWorkById(id: Long) {
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, WorkApi.GET_BY_ID_URL + id, Response.Listener { response ->
                val gson = Gson()
                val work = gson.fromJson(response, Work::class.java)

                edkategori!!.setText(work.kategori)
                etnamaPerusahaan!!.setText(work.namaPerusahaan)
                etposisi!!.setText(work.posisi)
                etlamaKerja!!.setText(work.lamaKerja)
                setExposedDropDownMenu()

                FancyToast.makeText(this@AddEditWorkActivity, "Data berhasil diambil!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                setLoading(false)
            }, Response.ErrorListener { error ->
                setLoading(false)

                try {
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    FancyToast.makeText(
                        this@AddEditWorkActivity,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.ERROR,false
                    ).show()
                } catch (e: Exception) {
                    FancyToast.makeText(this@AddEditWorkActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
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

    private fun createWork() {
        setLoading(true)

        if(edkategori!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditWorkActivity, "[!] Kategori Tidak boleh Kosong", Toast.LENGTH_SHORT).show()
        }
        else if(etnamaPerusahaan!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditWorkActivity, "[!] Nama Perusahaan Tidak boleh Kosong", Toast.LENGTH_SHORT).show()
        }
        else if(etposisi!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditWorkActivity, "[!] Posisi Tidak boleh Kosong", Toast.LENGTH_SHORT).show()
        }
        else if(etlamaKerja!!.text.toString().isEmpty()){
            Toast.makeText(this@AddEditWorkActivity, "[!] Lama Kerja Tidak boleh Kosong", Toast.LENGTH_SHORT).show()
        }
        else{
            val work = Work(
                edkategori!!.text.toString(),
                etnamaPerusahaan!!.text.toString(),
                etposisi!!.text.toString(),
                etlamaKerja!!.text.toString(),
            )

            val stringRequest: StringRequest =
                object : StringRequest(Method.POST, WorkApi.ADD_URL, Response.Listener { response ->
                    val gson = Gson()
                    val work = gson.fromJson(response, Work::class.java)

                    if (work != null)
                        FancyToast.makeText(
                            this@AddEditWorkActivity,
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
                        FancyToast.makeText( this@AddEditWorkActivity,
                            errors.getString("message"),
                            FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR,false
                        ).show()
                    } catch (e: Exception) {
                        FancyToast.makeText(this@AddEditWorkActivity, e.message, FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR, false).show()
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
                        val requestBody = gson.toJson(work)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }

            // Menambahkan request ke request queue
            queue!!.add(stringRequest)
        }
        setLoading(false)

    }

    private fun updateWork(id: Long) {
        setLoading(true)

        val work = Work(
            edkategori!!.text.toString(),
            etnamaPerusahaan!!.text.toString(),
            etposisi!!.text.toString(),
            etlamaKerja!!.text.toString(),
        )

        val stringRequest: StringRequest = object :
            StringRequest(Method.PUT, WorkApi.UPDATE_URL + id, Response.Listener { response ->
                val gson = Gson()

                var work = gson.fromJson(response, Work::class.java)

                if (work != null)
                    FancyToast.makeText(
                        this@AddEditWorkActivity,
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
                        this@AddEditWorkActivity,
                        errors.getString("message"),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS, false
                    ).show()
                } catch (e: Exception) {
                    FancyToast.makeText(this@AddEditWorkActivity, e.message, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
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
                val requestBody = gson.toJson(work)
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