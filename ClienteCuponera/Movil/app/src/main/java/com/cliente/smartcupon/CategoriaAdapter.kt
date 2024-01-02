package com.cliente.smartcupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cliente.smartcupon.interfaces.NotificacionLista
import com.cliente.smartcupon.poko.Promocion

class CategoriaAdapter(val categorias: List<Promocion>, val observador: NotificacionLista) : RecyclerView.Adapter<CategoriaAdapter.ViewHolderCategorias>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaAdapter.ViewHolderCategorias {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_lista_categorias, parent, false)
        return ViewHolderCategorias(itemView)
    }

    override fun onBindViewHolder(holder: CategoriaAdapter.ViewHolderCategorias, position: Int) {
        val categoria = categorias[position]
        holder.tvCategoria.text = "categoria: ${categoria.categoria}"
        holder.cardItem.setOnClickListener{
            observador.clicItemLista(position, categoria)
        }
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    class ViewHolderCategorias(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val tvCategoria : TextView = itemView.findViewById(R.id.tv_categoria)
        val cardItem : CardView = itemView.findViewById(R.id.card_item)

    }
}