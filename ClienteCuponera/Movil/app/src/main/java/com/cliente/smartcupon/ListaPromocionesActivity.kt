package com.cliente.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliente.smartcupon.databinding.ActivityListaPromocionesBinding
import com.cliente.smartcupon.interfaces.NotificacionLista
import com.cliente.smartcupon.poko.Promocion
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class ListaPromocionesActivity : AppCompatActivity(), NotificacionLista{

    private lateinit var binding: ActivityListaPromocionesBinding
    private var categoria : String = ""
    private var promociones : ArrayList<Promocion> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        categoria = intent.getStringExtra("categoria").toString()
        title = "Promocion: ${categoria}"
        obtenerPromocionesEmpresa(categoria)

        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this@ListaPromocionesActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun obtenerPromocionesEmpresa(categoria: String){
        Ion.with(this@ListaPromocionesActivity)
            .load("GET", "${Constantes.URL_WS}promocion/buscarPorCategoria/${categoria}")
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    serializaInformacion(result)
                    mostrarInformacionLista()
                }else{
                    Toast.makeText(this@ListaPromocionesActivity, "Error al obtener las promociones de la categoria", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun serializaInformacion(json : String){
        val gson = Gson()
        val typePromociones = object : TypeToken<ArrayList<Promocion>>(){}.type

        promociones = gson.fromJson(json, typePromociones)
        Toast.makeText(this@ListaPromocionesActivity, "Promociones: "+promociones.size, Toast.LENGTH_LONG).show()
    }

    fun mostrarInformacionLista(){
        binding.recyclerPromociones.layoutManager = LinearLayoutManager(this@ListaPromocionesActivity)
        binding.recyclerPromociones.setHasFixedSize(true)
        if (promociones.size>0){
            binding.tvDefault.visibility = View.GONE
            binding.recyclerPromociones.adapter = PromocionAdapter(promociones, this)
        }
    }

    override fun clicItemLista(posicion: Int, promocion: Promocion) {
        TODO("Not yet implemented")
    }


}