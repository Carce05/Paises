package com.paises.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.paises.databinding.PaisFilaBinding
import com.paises.model.Pais
import com.paises.ui.pais.PaisFragmentDirections

class PaisAdapter : RecyclerView.Adapter<PaisAdapter.PaisViewHolder>() {

    //Lista para almacenar info de paises
    private  var listaPaises = emptyList<Pais>()

    inner class PaisViewHolder(private val itemBinding: PaisFilaBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun dibuja(pais: Pais){
            itemBinding.tvNombre.text=pais.nombre
            itemBinding.tvCorreo.text=pais.correo
            itemBinding.tvTelefono.text=pais.telefono
            itemBinding.tvWeb.text=pais.web
            itemBinding.vistaFila.setOnClickListener{
                val accion = PaisFragmentDirections
                    .actionNavPaisToUpdatePaisFragment(pais)
                itemView.findNavController().navigate(accion)

            }

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisViewHolder {
        val itemBinding = PaisFilaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaisViewHolder(itemBinding)


    }

    override fun onBindViewHolder(holder: PaisViewHolder, position: Int) {
        val pais = listaPaises[position]
        holder.dibuja(pais)

    }

    override fun getItemCount(): Int {
        return listaPaises.size
    }

    fun setData(paises: List<Pais>) {
        this.listaPaises=paises

        //la siguiente instruccion redibuja la lista del reciclador
        notifyDataSetChanged()
    }
}