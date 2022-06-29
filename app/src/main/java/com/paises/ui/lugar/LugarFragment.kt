package com.paises.ui.pais

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paises.R
import com.paises.adapter.PaisAdapter
import com.paises.databinding.FragmentPaisBinding
import com.paises.viewmodel.PaisViewModel

class PaisFragment : Fragment() {

    private lateinit var paisViewModel: PaisViewModel

    private var _binding: FragmentPaisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        paisViewModel = ViewModelProvider(this)[PaisViewModel::class.java]
        _binding = FragmentPaisBinding.inflate(inflater,container,false)

        //Se programa la accion para pasarse de pais
        binding.addPaisButton.setOnClickListener{
            findNavController().navigate(R.id.action_nav_pais_to_addPaisFragment)
        }

        //Activar reciclador -RecyclerView
        var paisAdapter = PaisAdapter()
        val reciclador = binding.reciclador

        reciclador.adapter = paisAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        paisViewModel = ViewModelProvider(this)[PaisViewModel::class.java]

        paisViewModel.getAllData.observe(viewLifecycleOwner){
                paises -> paisAdapter.setData(paises)
        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}