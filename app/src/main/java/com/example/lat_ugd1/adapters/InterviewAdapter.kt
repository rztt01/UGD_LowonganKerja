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
import com.example.lat_ugd1.AddEditInterviewActivity
import com.example.lat_ugd1.InterviewActivity
import com.example.lat_ugd1.R
import com.example.lat_ugd1.models.Interview
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*

class InterviewAdapter (private var interviewList: List<Interview>, context: Context) :
    RecyclerView.Adapter<InterviewAdapter.ViewHolder>(), Filterable {

    private var filteredInterviewList: MutableList<Interview>
    private val context: Context

    init {
        filteredInterviewList = ArrayList(interviewList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_interview, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredInterviewList.size
    }

    fun setInterviewList(interviewList: Array<Interview>) {
        this.interviewList = interviewList.toList()
        filteredInterviewList = interviewList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val interview = filteredInterviewList[position]
        holder.tvPerusahaan.text = interview.perusahaan
        holder.tvRole.text = interview.role
        holder.tvGaji.text = interview.gaji
        holder.tvDomisili.text = interview.domisili

        holder.btnDelete.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data mahasiswa ini?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus") { _, _ ->
                    if (context is InterviewActivity) interview.id?.let { it1 ->
                        context.deleteInterview(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvInterview.setOnClickListener {
            val i = Intent(context, AddEditInterviewActivity::class.java)
            i.putExtra("id", interview.id)
            if (context is InterviewActivity)
                context.startActivityForResult(i, InterviewActivity.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Interview> = java.util.ArrayList()
                if (charSequenceString.isEmpty()) {
                    filtered.addAll(interviewList)
                } else {
                    for (interview in interviewList) {
                        if (interview.perusahaan.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(interview)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredInterviewList.clear()
                filteredInterviewList.addAll((filterResults.values as List<Interview>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPerusahaan: TextView
        var tvRole: TextView
        var tvGaji: TextView
        var tvDomisili: TextView
        var btnDelete: ImageButton
        var cvInterview: CardView

        init {
            tvPerusahaan = itemView.findViewById(R.id.tv_perusahaan)
            tvRole = itemView.findViewById(R.id.tv_role)
            tvGaji = itemView.findViewById(R.id.tv_gaji)
            tvDomisili = itemView.findViewById(R.id.tv_domisili)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvInterview = itemView.findViewById(R.id.cv_interview)
        }
    }
}