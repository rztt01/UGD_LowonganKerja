package com.example.lat_ugd1.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.lat_ugd1.AddEditWorkActivity
import com.example.lat_ugd1.R
import com.example.lat_ugd1.WorkActivity
import com.example.lat_ugd1.models.Work
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class WorkAdapter (private var workList: List<Work>, context: Context) :
    RecyclerView.Adapter<WorkAdapter.ViewHolder>(), Filterable {

    private var filteredWorkList: MutableList<Work>
    private val context: Context

    init {
        filteredWorkList = ArrayList(workList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_work, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredWorkList.size
    }

    fun setWorkList(workList: Array<Work>) {
        this.workList = workList.toList()
        filteredWorkList = workList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val work = filteredWorkList[position]
        holder.tvKategori.text = work.kategori
        holder.tvNamaPerusahaan.text = work.namaPerusahaan
        holder.tvPosisi.text = work.posisi
        holder.tvLamaKerja.text = work.lamaKerja

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data mahasiswa ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is WorkActivity) work.id?.let { it1 ->
                        context.deleteWork(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvWork.setOnClickListener {
            val i = Intent(context, AddEditWorkActivity::class.java)
            i.putExtra("id", work.id)
            if (context is WorkActivity)
                context.startActivityForResult(i, WorkActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Work> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(workList)
                } else {
                    for (work in workList) {
                        if (work.kategori.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(work)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredWorkList.clear()
                filteredWorkList.addAll((filterResults.values as List<Work>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvKategori: TextView
        var tvNamaPerusahaan: TextView
        var tvPosisi: TextView
        var tvLamaKerja: TextView
        var btnDelete: ImageButton
        var cvWork: CardView

        init {
            tvKategori = itemView.findViewById(R.id.tv_kategori)
            tvNamaPerusahaan = itemView.findViewById(R.id.tv_namaPerusahaan)
            tvPosisi = itemView.findViewById(R.id.tv_posisi)
            tvLamaKerja = itemView.findViewById(R.id.tv_lamaKerja)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvWork = itemView.findViewById(R.id.cv_work)
        }
    }
}