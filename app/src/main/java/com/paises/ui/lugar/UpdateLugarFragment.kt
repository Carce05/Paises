package com.paises.ui.pais

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paises.R

import com.paises.databinding.FragmentUpdatePaisBinding
import com.paises.model.Pais
import com.paises.viewmodel.PaisViewModel
import android.Manifest

class UpdatePaisFragment : Fragment() {
    private val args by navArgs<UpdatePaisFragmentArgs>()

    private lateinit var paisViewModel: PaisViewModel

    private var _binding: FragmentUpdatePaisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        paisViewModel = ViewModelProvider(this)[PaisViewModel::class.java]
        _binding = FragmentUpdatePaisBinding.inflate(inflater,container,false)

        //Se coloca la info del objeto pais que pasaron
        binding.etNombre.setText(args.pais.nombre)
        binding.etTelefono.setText(args.pais.telefono)
        binding.etCorreo.setText(args.pais.correo)
        binding.etWeb.setText(args.pais.web)

        binding.tvAltura.text=args.pais.altura.toString()
        binding.tvAltura.text=args.pais.latitud.toString()
        binding.tvAltura.text=args.pais.logitud.toString()

        //Se agrega la funcion para actualizar un pais
        binding.btActualizar.setOnClickListener { updatePais() }

        binding.btEmail.setOnClickListener{escribirCorreo()}
        binding.btPhone.setOnClickListener({llamarPais()})
        //binding.btWhatsapp.setOnClickListener({enviarWhatsApp()})
        binding.btWeb.setOnClickListener({verWeb()})
        //binding.btLocation.setOnClickListener({verMapa()})

        //Se indica que en esta pantalla se agrega opcion de menu
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun llamarPais() {
        val recurso = binding.etTelefono.text.toString()
        if (recurso.isNotEmpty()) {
            //Se activa el correo
            val rutina = Intent(Intent.ACTION_CALL)
            rutina.data= Uri.parse("tel:$recurso")

            if(requireActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                //se solicitan perimisos
                requireActivity().requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 105)
            }else{
                requireActivity().startActivity(rutina)
            }
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun verWeb() {
        //Se recupera el url del pais...
        val recurso = binding.etWeb.text.toString()
        if (recurso.isNotEmpty()) {
            //Se abre el sitio web
            val rutina = Intent(Intent.ACTION_VIEW, Uri.parse("http://$recurso"))
            startActivity(rutina)  //Levanta el browser y se ve el sitio web
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun escribirCorreo() {
        //Se recupera el correo del pais...
        val recurso = binding.etCorreo.text.toString()
        if (recurso.isNotEmpty()) {
            //Se activa el correo
            val rutina = Intent(Intent.ACTION_SEND)
            rutina.type="message/rfc822"
            rutina.putExtra(Intent.EXTRA_EMAIL, arrayOf(recurso))
            rutina.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.msg_saludos)+ " "+binding.etNombre.text)
            rutina.putExtra(Intent.EXTRA_TEXT, getString(R.string.msg_mensaje_correo))
            startActivity(rutina)//Levanta correo y lo presnta para modificar y enviar
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Pregunto si se dio clien en el icono de borrado
        if(item.itemId==R.id.menu_delete){
            //Hace algo si se dio click
            deletePais()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletePais() {
        val consulta = AlertDialog.Builder(requireContext())

        consulta.setTitle(R.string.delete)
        consulta.setMessage(getString(R.string.seguroBorrar)+ " ${args.pais.nombre}?")

        //Acciones a ejecutar si respondo YESSS
        consulta.setPositiveButton(getString(R.string.si)){_,_ ->

            //Borramos el pais... si consultar...
            paisViewModel.deletePais(args.pais)
            findNavController().navigate(R.id.action_updatePaisFragment_to_nav_pais)
        }
        consulta.setNegativeButton(getString(R.string.no)){_,_ ->

            consulta.create().show()
        }

    }


    private fun updatePais() {
        val nombre=binding.etNombre.text.toString()
        val correo=binding.etCorreo.text.toString()
        val telefono=binding.etTelefono.text.toString()
        val web=binding.etWeb.text.toString()

        if (nombre.isNotEmpty()){
            val pais = Pais(args.pais.id, nombre, correo, telefono, web, 0.0, 0.0, 0.0, "", "")
            paisViewModel.updatePais(pais)
            Toast.makeText(requireContext(), getString(R.string.paisAdded), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updatePaisFragment_to_nav_pais)
        }else{
            Toast.makeText(requireContext(), getString(R.string.noData), Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}