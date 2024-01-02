package com.cliente.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliente.smartcupon.databinding.ActivityMainBinding
import com.cliente.smartcupon.interfaces.NotificacionLista
import com.cliente.smartcupon.poko.Promocion
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class MainActivity : AppCompatActivity(), NotificacionLista{

    private lateinit var binding: ActivityMainBinding
    private var categorias : ArrayList<Promocion> = ArrayList()
    var categoria: String = "";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        obtenerCategoriaPromociones()

    }

    fun obtenerCategoriaPromociones(){
        Ion.with(this@MainActivity)
            .load("GET", "${Constantes.URL_WS}promocion/obtenerCategorias")
            .asString()
            .setCallback { e, result ->
                if (e==null && result != null){
                    serializaInformacion(result)
                    mostrarInformacionLista()
                }else{
                    Toast.makeText(this@MainActivity, "Error al obtener las promociones de la categoria", Toast.LENGTH_LONG).show()
                }
            }
    }
    fun serializaInformacion(json : String){
        val gson = Gson()
        val typeCategorias = object : TypeToken<ArrayList<Promocion>>(){}.type

        categorias = gson.fromJson(json, typeCategorias)
        Toast.makeText(this@MainActivity, "Categoria: "+categorias.size, Toast.LENGTH_LONG).show()
    }
    fun mostrarInformacionLista() {
        binding.recyclerCategorias.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerCategorias.setHasFixedSize(true)

        // Filtra categorías únicas
        val categoriasUnicas = categorias.distinctBy { it.categoria }

        if (categoriasUnicas.isNotEmpty()) {
            binding.tvDefault.visibility = View.GONE
            binding.recyclerCategorias.adapter = CategoriaAdapter(categoriasUnicas, this)
        }
    }

    override fun clicItemLista(posicion: Int, promocion: Promocion) {
        categoria = promocion.categoria
        Toast.makeText(this@MainActivity, "Ir ${categoria}", Toast.LENGTH_LONG).show()

        irPantalla(categoria)


    }

    fun irPantalla(categoria:String){
        val intent = Intent(this@MainActivity, ListaPromocionesActivity::class.java)

        intent.putExtra("categoria", categoria)
        startActivity(intent)
        finish()
    }
}