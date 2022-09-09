package com.example.lat_ugd1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentHome : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVHomeAdapter = RVHomeAdapter(Home.listOfMahasiswa)

        val rvMahasiswa : RecyclerView = view.findViewById(R.id.rv_mahasiswa)

        rvMahasiswa.layoutManager = layoutManager

        rvMahasiswa.adapter = adapter
    }

}