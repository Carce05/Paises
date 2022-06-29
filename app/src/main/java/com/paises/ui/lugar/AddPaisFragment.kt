package com.paises.ui.pais

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.paises.R
import com.paises.databinding.FragmentAddPaisBinding
import com.paises.model.Pais
import com.paises.viewmodel.PaisViewModel

class AddPaisFragment : Fragment() {
    private lateinit var paisViewModel: PaisViewModel

    private var _binding: FragmentAddPaisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        paisViewModel = ViewModelProvider(this)[PaisViewModel::class.java]
        _binding = FragmentAddPaisBinding.inflate(inflater,container,false)

        //Se agrega la funcion para agregar un pais
        binding.btAdd.setOnClickListener { addPais() }

        return binding.root
    }

    private fun addPais() {
        val nombre=binding.etNombre.text.toString()
        val correo=binding.etCorreo.text.toString()
        val telefono=binding.etTelefono.text.toString()
        val web=binding.etWeb.text.toString()

        if (nombre.isNotEmpty()){
            val pais = Pais(0, nombre, correo, telefono, web, 0.0, 0.0, 0.0, "", "")
            paisViewModel.addPais(pais)
            Toast.makeText(requireContext(), getString(R.string.paisAdded), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addPaisFragment_to_nav_pais)
        }else{
            Toast.makeText(requireContext(), getString(R.string.noData), Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}