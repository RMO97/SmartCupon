package com.cliente.smartcupon

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliente.smartcupon.databinding.ActivityListaPromocionesBinding
import com.cliente.smartcupon.interfaces.NotificacionLista
import com.cliente.smartcupon.poko.Cliente
import com.cliente.smartcupon.poko.Promocion
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import java.text.ParseException
import java.util.Locale

class ListaPromocionesActivity : AppCompatActivity(), NotificacionLista{

    private lateinit var binding: ActivityListaPromocionesBinding
    private var categoria : String = ""
    private var promociones : ArrayList<Promocion> = ArrayList()
    private var idPromocion : Int = 0
    private lateinit var etSearch: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaPromocionesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        categoria = intent.getStringExtra("categoria").toString()
        title = "Promocion: ${categoria}"
        obtenerPromocionesCategoria(categoria)


        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this@ListaPromocionesActivity, MainActivity::class.java)
            startActivity(intent)
        }
        etSearch = findViewById(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarPromociones(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
                filtrarPromociones(s.toString())
            }
        })
        obtenerPromocionesCategoria(categoria)
    }

    private fun filtrarPromociones(query: String) {
        val promocionesFiltradas = promociones.filter { promocion ->
            promocion.nombreEmpresa.contains(query, ignoreCase = true) ||
                    promocion.nombreComercial.contains(query, ignoreCase = true) ||
                    promocion.fechaDeExpiracionPromocion.contains(query, ignoreCase = true)
        }

        binding.recyclerPromociones.adapter = PromocionAdapter(promocionesFiltradas, this)
    }
    fun obtenerPromocionesCategoria(categoria: String){
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

    fun mostrarInformacionLista() {
        binding.recyclerPromociones.layoutManager = LinearLayoutManager(this@ListaPromocionesActivity)
        binding.recyclerPromociones.setHasFixedSize(true)

        // Filtra promociones con cupones mayores a 0 y fecha de expiración futura
        val promocionesFiltradas = promociones.filter {
            it.numeroCuponesMaximo > 0 && !esFechaExpirada(it.fechaDeExpiracionPromocion)
        }

        if (promocionesFiltradas.isNotEmpty()) {
            binding.tvDefault.visibility = View.GONE
            binding.recyclerPromociones.adapter = PromocionAdapter(promocionesFiltradas, this)
        } else {
        // Si no hay promociones que cumplen los criterios, puedes mostrar un mensaje o realizar alguna acción
            binding.tvDefault.visibility = View.VISIBLE
        }
    }

    fun esFechaExpirada(fechaDeExpiracionPromocion: String): Boolean {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        try {
            val fechaExpiracionDate = formato.parse(fechaDeExpiracionPromocion)
            val fechaActual = Calendar.getInstance().time
            return fechaExpiracionDate != null && fechaExpiracionDate.before(fechaActual)
        } catch (e: ParseException) {
            e.printStackTrace()
            return false // Tratamiento de error, puedes ajustarlo según tus necesidades
        }
    }
    /*
    fun mostrarInformacionLista(){
        binding.recyclerPromociones.layoutManager = LinearLayoutManager(this@ListaPromocionesActivity)
        binding.recyclerPromociones.setHasFixedSize(true)
        if (promociones.size>0){
            binding.tvDefault.visibility = View.GONE
            binding.recyclerPromociones.adapter = PromocionAdapter(promociones, this)
        }
    }*/

    override fun clicItemLista(posicion: Int, promocion: Promocion) {
        idPromocion = promocion.idPromocion
        val intent = Intent(this@ListaPromocionesActivity, CuponesDetallesActivity::class.java)
        intent.putExtra("idPromocion", idPromocion)
        intent.putExtra("categoria", categoria) // Pasar la categoría a CuponesDetallesActivity
        startActivity(intent)
        finish()
    }
}


