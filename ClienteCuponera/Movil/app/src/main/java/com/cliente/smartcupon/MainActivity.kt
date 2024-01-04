package com.cliente.smartcupon

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cliente.smartcupon.databinding.ActivityMainBinding
import com.cliente.smartcupon.interfaces.NotificacionLista
import com.cliente.smartcupon.poko.Cliente
import com.cliente.smartcupon.poko.Promocion
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion

class MainActivity : AppCompatActivity(), NotificacionLista{

    private lateinit var binding: ActivityMainBinding
    private var categorias : ArrayList<Promocion> = ArrayList()
    var categoria: String = "";
    private lateinit var clienteEdit : Cliente
    private val editRequestCode = 1
    private lateinit var etSearch: EditText

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val jsonCliente = intent.getStringExtra("cliente")
        etSearch = findViewById(R.id.etSearch)
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarCategorias(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {
                filtrarCategorias(s.toString())
            }
        })
        obtenerCategoriaPromociones()

        binding.btnUserEdicion.setOnClickListener {
            if (jsonCliente!=null)
            serializarDatosCliente(jsonCliente)
        }
    }
    private fun filtrarCategorias(query: String) {
        val categoriasFiltradas = categorias.filter {
            it.categoria.contains(query, ignoreCase = true)
        }
        binding.recyclerCategorias.adapter = CategoriaAdapter(categoriasFiltradas, this)
    }

    fun serializarDatosCliente(json: String){
        val gson = Gson()
        clienteEdit = gson.fromJson(json,Cliente::class.java)
        if(clienteEdit!=null){
            irPantallaEdicionCliente(clienteEdit)
        }else{
            Toast.makeText(this@MainActivity,"Error al obtener informacion del cliente",Toast.LENGTH_LONG).show()
        }
    }

    fun irPantallaEdicionCliente(cliente: Cliente){
        var intent = Intent(this@MainActivity,EditarClienteActivity::class.java)
        val gson = Gson()
        val cadenaJson: String = gson.toJson(cliente)
        intent.putExtra("cliente",cadenaJson)
        startActivityForResult(intent,editRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == editRequestCode && resultCode == RESULT_OK) {
            val clienteActualizadoJson = data?.getStringExtra("cliente")
            if (clienteActualizadoJson != null) {
                val gson = Gson()
                val clienteActualizado = gson.fromJson(clienteActualizadoJson, Cliente::class.java)

                this.clienteEdit = clienteActualizado
                Toast.makeText(this@MainActivity, "Información del cliente actualizada", Toast.LENGTH_LONG).show()
            }
        }
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