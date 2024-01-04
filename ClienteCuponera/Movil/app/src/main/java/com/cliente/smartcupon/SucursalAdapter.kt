package com.cliente.smartcupon
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cliente.smartcupon.interfaces.NotificacionListaSucursal
import com.cliente.smartcupon.poko.Sucursal

class SucursalAdapter(val sucursales: List<Sucursal>, val observador: NotificacionListaSucursal) :
    RecyclerView.Adapter<SucursalAdapter.ViewHolderSucursales>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSucursales {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sucursal, parent, false)
        return ViewHolderSucursales(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderSucursales, position: Int) {
        val sucursal = sucursales[position]
        holder.tvNombreSucursal.text = "Sucursal: ${sucursal.nombreSucursal}"
        holder.tvDireccion.text = "Calle: ${sucursal.direccion}"
        holder.tvCodigoPostal.text = "Código Postal: ${sucursal.codigoPostal}"
        holder.tvTelefono.text = "Teléfono: ${sucursal.telefono}"
        holder.cardItem.setOnClickListener {
            observador.clicItemLista(position, sucursal)
        }
    }

    override fun getItemCount(): Int {
        return sucursales.size
    }

    class ViewHolderSucursales(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreSucursal: TextView = itemView.findViewById(R.id.tv_nombreSucursal)
        val tvDireccion: TextView = itemView.findViewById(R.id.tv_direccion)
        val tvCodigoPostal: TextView = itemView.findViewById(R.id.tv_codigoPostal)
        val tvTelefono: TextView = itemView.findViewById(R.id.tv_telefono)
        val cardItem: CardView = itemView.findViewById(R.id.card_item)
    }

}