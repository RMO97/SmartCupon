package com.cliente.smartcupon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cliente.smartcupon.interfaces.NotificacionLista
import com.cliente.smartcupon.poko.Promocion

class PromocionAdapter(val promociones: List<Promocion>, val observador: NotificacionLista) : RecyclerView.Adapter<PromocionAdapter.ViewHolderPromociones>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromocionAdapter.ViewHolderPromociones {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_promociones, parent, false)
        return ViewHolderPromociones(itemView)
    }

    override fun onBindViewHolder(holder: PromocionAdapter.ViewHolderPromociones, position: Int) {
        val promocion = promociones[position]
        holder.tvNombrePromocion.text = "Promocion: ${promocion.nombrePromocion}"
        if (promocion.tipoPromocion==true){
            holder.tvTipoPromocion.text = "Tipo: Descuento"
        }else{
            holder.tvTipoPromocion.text = "Tipo: Costo rebajado"
        }

        holder.tvValor.text = "Valor: ${promocion.valor}"
        holder.tvNumeroCupones.text = "Cupones disponibles: ${promocion.numeroCuponesMaximo}"
        holder.tvVigencia.text = "Vigencia: ${promocion.fechaDeExpiracionPromocion}"
        holder.tvnombreEmpresa.text = "Empresa: ${promocion.nombreComercial}"
        holder.cardItem.setOnClickListener{
            observador.clicItemLista(position, promocion)
        }
    }

    override fun getItemCount(): Int {
        return promociones.size
    }

    class ViewHolderPromociones(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val tvNombrePromocion : TextView = itemView.findViewById(R.id.tv_nombrePromocion)
        val tvnombreEmpresa : TextView = itemView.findViewById(R.id.tv_nombreEmpresa)
        val tvTipoPromocion : TextView = itemView.findViewById(R.id.tv_tipoPromocion)
        val tvValor : TextView = itemView.findViewById(R.id.tv_valor)
        val tvNumeroCupones : TextView = itemView.findViewById(R.id.tv_numperoCupones)
        val tvVigencia : TextView = itemView.findViewById(R.id.tv_vigencia)
        val cardItem : CardView = itemView.findViewById(R.id.card_item)

    }

}