package com.cliente.smartcupon
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliente.smartcupon.databinding.ActivitySucursalBinding
import com.cliente.smartcupon.interfaces.NotificacionListaSucursal
import com.cliente.smartcupon.poko.Sucursal
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class SucursalActivity : AppCompatActivity(), NotificacionListaSucursal {

    private lateinit var binding: ActivitySucursalBinding
    private var sucursales: ArrayList<Sucursal> = ArrayList()
    private var idPromocion : Int = 0
    var categoria: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySucursalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        categoria = intent.getStringExtra("categoria").toString()
        idPromocion = intent.getIntExtra("idPromocion", -1)
        obtenerSucursales(idPromocion)
        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this@SucursalActivity, CuponesDetallesActivity::class.java)
            intent.putExtra("idPromocion", idPromocion)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
            finish()
        }
    }

    private fun obtenerSucursales(idPromocion: Int) {
        Ion.with(this@SucursalActivity)
            .load("GET", "${Constantes.URL_WS}sucursal/obtenerPorPromocion/${idPromocion}")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    serializaInformacion(result)
                    mostrarInformacionLista()
                } else {
                    Toast.makeText(this@SucursalActivity, "Error al obtener las sucursales", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun serializaInformacion(json: String) {
        val gson = Gson()
        val typeSucursales = object : TypeToken<ArrayList<Sucursal>>() {}.type

        sucursales = gson.fromJson(json, typeSucursales)
        Toast.makeText(this@SucursalActivity, "Sucursales: ${sucursales.size}", Toast.LENGTH_LONG).show()
    }

    private fun mostrarInformacionLista() {
        binding.recyclerSucursales.layoutManager = LinearLayoutManager(this@SucursalActivity)
        binding.recyclerSucursales.setHasFixedSize(true)

        if (sucursales.isNotEmpty()) {
            binding.tvDefault.visibility = android.view.View.GONE
            binding.recyclerSucursales.adapter = SucursalAdapter(sucursales, this)
        } else {
            binding.tvDefault.visibility = android.view.View.VISIBLE
        }
    }
    override fun clicItemLista(posicion: Int, sucursal: Sucursal) {
    }
}

