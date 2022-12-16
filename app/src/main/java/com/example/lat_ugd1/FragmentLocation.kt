package com.example.lat_ugd1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lat_ugd1.databinding.FragmentLocationBinding

class FragmentLocation : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.maps.setOnClickListener{
            val move = Intent(activity, LocationActivity::class.java)
            startActivity(move)
            activity?.finish()
        }

        binding.interview.setOnClickListener{
            val move = Intent(activity, InterviewActivity::class.java)
            startActivity(move)
            activity?.finish()
        }

        binding.interviewPdf.setOnClickListener{
            val move = Intent(activity, PDFActivity::class.java)
            startActivity(move)
            activity?.finish()
        }

        binding.certificate.setOnClickListener{
            val move = Intent(activity, ShowPerusahaanActivity::class.java)
            startActivity(move)
            activity?.finish()
        }

        binding.workExperience.setOnClickListener{
            val move = Intent(activity, WorkActivity::class.java)
            startActivity(move)
            activity?.finish()
        }

    }


}