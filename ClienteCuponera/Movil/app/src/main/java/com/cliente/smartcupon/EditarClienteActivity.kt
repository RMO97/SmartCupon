package com.cliente.smartcupon

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cliente.smartcupon.databinding.ActivityEditarClienteBinding
import com.cliente.smartcupon.poko.Cliente
import com.cliente.smartcupon.poko.Mensaje
import com.cliente.smartcupon.poko.RespuestaLogin
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
import java.util.Calendar

class EditarClienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarClienteBinding
    private lateinit var clienteEdicion :Cliente
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val jsonCliente = intent.getStringExtra("cliente")
        if (jsonCliente!=null){
            serializarInformaiconCliente(jsonCliente)
            cargaInformacionCliente()
        }

        binding.btnCancelar.setOnClickListener {
            irPantallaPrincipal(clienteEdicion)
        }

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val apellidoPaterno = binding.etApellidoP.text.toString()
            val apellidoMaterno = binding.etApellidoM.text.toString()
            val direccion = binding.etDireccion.text.toString()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString()
            val numeroTelefono = binding.etTelefono.text.toString()
            val password = binding.etPassword.text.toString()
            val correoElectronico = clienteEdicion.correoElectronico
            val idCliente = clienteEdicion.idCliente
            if (validarCamposEdicion(nombre, apellidoMaterno, apellidoPaterno, direccion, password, numeroTelefono, fechaNacimiento)){
                realizarPeticionEditar(idCliente, nombre, direccion, password, apellidoMaterno, apellidoPaterno, numeroTelefono, fechaNacimiento, correoElectronico)
            }
        }
        binding.btnFechaNacimiento.setOnClickListener {
            val dialogFecha = RegistroClienteActivity.DatePickerFragment { año, mes, dia ->
                mostrarFecha(
                    año,
                    mes,
                    dia
                )
            }
            dialogFecha.show(supportFragmentManager,"DatePicker")
        }
    }

    fun serializarInformaiconCliente(json:String){
        val gson = Gson()
        clienteEdicion = gson.fromJson(json,Cliente::class.java)
    }
    fun cargaInformacionCliente(){
        binding.etNombre.setText(clienteEdicion.nombre)
        binding.etApellidoM.setText(clienteEdicion.apellidoMaterno)
        binding.etApellidoP.setText(clienteEdicion.apellidoPaterno)
        binding.etPassword.setText(clienteEdicion.password)
        binding.etFechaNacimiento.setText(clienteEdicion.fechaNacimiento)
        binding.etDireccion.setText(clienteEdicion.direccion)
        binding.etTelefono.setText(clienteEdicion.numeroTelefono)
    }

    fun mostrarFecha(año: Int, mes: Int, dia:Int){
        binding.etFechaNacimiento.setText("$año-$mes-$dia")
    }

    fun validarCamposEdicion(nombre: String,apellidoMaterno: String,apellidoPaterno: String,direccion: String,password: String,numeroTelefono: String,fechaNacimiento: String):Boolean{
        var isValido = true
        if (direccion.isEmpty()){
            isValido = false
            binding.etDireccion.error = "Direccion obligatoria"
        }
        if (nombre.isEmpty()){
            isValido = false
            binding.etNombre.error = "Nombre obligatorio"
        }
        if (apellidoPaterno.isEmpty()){
            isValido = false
            binding.etApellidoP.error = "Apellido paterno obligatorio"
        }
        if (apellidoMaterno.isEmpty()){
            isValido = false
            binding.etApellidoM.error = "Apellido materno obligatorio"
        }
        if (numeroTelefono.isEmpty()){
            isValido = false
            binding.etTelefono.error = "Numero de telefono obligatorio"
        }
        if (password.isEmpty()){
            isValido = false
            binding.etPassword.error = "Contraseña obligatoria"
        }
        if (fechaNacimiento.isEmpty()){
            isValido = false
            binding.etFechaNacimiento.error = "Fecha de nacimiento obligatoria"
        }
        return isValido
    }

    fun realizarPeticionEditar(idCliente: Int,nombre: String,direccion: String,password: String,apellidoMaterno: String,apellidoPaterno: String,
                               numeroTelefono: String,fechaNacimiento: String,correoElectronico: String){

        Ion.getDefault(this@EditarClienteActivity).conscryptMiddleware.enable(false)
        Ion.with(this@EditarClienteActivity)
            .load("PUT", Constantes.URL_WS+"cliente/editarCliente")
            .setHeader("Content-Type","application/x-www-form-urlencoded")
            .setBodyParameter("idCliente",idCliente.toString())
            .setBodyParameter("nombre",nombre)
            .setBodyParameter("apellidoPaterno", apellidoPaterno)
            .setBodyParameter("apellidoMaterno", apellidoMaterno)
            .setBodyParameter("direccion",direccion)
            .setBodyParameter("correoElectronico", correoElectronico)
            .setBodyParameter("password", password)
            .setBodyParameter("numeroTelefono",numeroTelefono)
            .setBodyParameter("fechaNacimiento",fechaNacimiento)
            .asString()
            .setCallback { e, result ->
                if (e==null && result!=null){
                    actualizarCliente(idCliente)
                }else{
                    Toast.makeText(this@EditarClienteActivity,"Error no se pudo editar la cuenta",Toast.LENGTH_LONG).show()
                }
            }
    }

    fun actualizarCliente(idCliente: Int){
        Ion.with(this@EditarClienteActivity)
            .load("GET","${Constantes.URL_WS}cliente/obtenerClientePorId/${idCliente}")
            .asString()
            .setCallback { e, result ->
                if (e==null&& result!=null){
                    serializarRespuestaEdicion(result)
                }
            }
    }

    fun serializarRespuestaEdicion(json: String){
        val gson = Gson()
        val respuestaEdicion = gson.fromJson(json, Cliente::class.java)
        Toast.makeText(this@EditarClienteActivity,"Cliente actualizado con exito", Toast.LENGTH_LONG).show()
        if(respuestaEdicion!=null){
            irPantallaPrincipal(respuestaEdicion)
        }
    }

    fun irPantallaPrincipal(cliente: Cliente){
        var intent = Intent(this@EditarClienteActivity, MainActivity::class.java)
        val gson = Gson()
        val cadenaJson:String = gson.toJson(cliente)
        intent.putExtra("cliente",cadenaJson)
        Toast.makeText(this@EditarClienteActivity,cadenaJson,Toast.LENGTH_LONG).show()
        startActivity(intent)
        finish()
    }


    class DatePickerFragment(val listener:(año:Int, mes:Int,dia:Int)->Unit): DialogFragment(),
        DatePickerDialog.OnDateSetListener{

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendario = Calendar.getInstance()
            var año = calendario.get(Calendar.YEAR)
            var mes = calendario.get(Calendar.MONTH)
            var dia = calendario.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireActivity(),this,año,mes,dia)
        }
        override fun onDateSet(view: DatePicker?, año: Int, mes: Int, dia: Int) {
            listener(año,mes,dia)
        }
    }

}