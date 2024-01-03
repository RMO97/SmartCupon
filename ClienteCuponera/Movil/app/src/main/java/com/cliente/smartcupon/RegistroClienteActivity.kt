package com.cliente.smartcupon

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.cliente.smartcupon.databinding.ActivityRegistroClienteBinding
import com.cliente.smartcupon.poko.Cliente
import com.cliente.smartcupon.poko.Mensaje
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import java.util.Calendar

class RegistroClienteActivity : AppCompatActivity() {
    //private lateinit var clienteE:Cliente
    private lateinit var binding: ActivityRegistroClienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val apellidoPaterno = binding.etApellidoP.text.toString()
            val apellidoMaterno = binding.etApellidoM.text.toString()
            val correoElectronico = binding.etCorreoElectronico.text.toString()
            val direccion = binding.etDireccion.text.toString()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString()
            val numeroTelefono = binding.etTelefono.text.toString()
            val password = binding.etPassword.text.toString()

            if (validarCamposRegistro(nombre, apellidoMaterno, apellidoPaterno, direccion, password, correoElectronico, numeroTelefono, fechaNacimiento)){
                realizarPeticionRegistro(nombre, apellidoPaterno, apellidoMaterno, direccion, correoElectronico, numeroTelefono, fechaNacimiento, password)
            }
        }
        binding.btnFechaNacimiento.setOnClickListener {
            val dialogFecha = DatePickerFragment{año, mes, dia ->  mostrarFecha(año, mes, dia)}
            dialogFecha.show(supportFragmentManager,"DatePicker")
        }
        binding.btnCancelar.setOnClickListener {
            irPantallaLogin()
        }

    }

    fun mostrarFecha(año: Int, mes: Int, dia:Int){
        binding.etFechaNacimiento.setText("$año-$mes-$dia")
    }
    fun validarCamposRegistro(nombre: String,apellidoMaterno: String,apellidoPaterno: String,direccion: String,password: String,correoElectronico: String,numeroTelefono: String,fechaNacimiento: String):Boolean{
        var isValido = true
        if (direccion.isEmpty()){
            isValido = false
            binding.etDireccion.error = "Direccion obligatoria"
        }
        if (correoElectronico.isEmpty()){
            isValido = false
            binding.etCorreoElectronico.error = "Correo electronico obligatorio"
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
    fun realizarPeticionRegistro(nombre: String, apellidoPaterno: String, apellidoMaterno: String, direccion: String, correoElectronico: String, numeroTelefono: String, fechaNacimiento: String, password:String){
        Ion.getDefault(this@RegistroClienteActivity).conscryptMiddleware.enable(false)
        Ion.with(this@RegistroClienteActivity)
            .load("POST",Constantes.URL_WS+"cliente/registro")
            .setHeader("Content-Type","application/x-www-form-urlencoded")
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
                    serializarRespuestaRegistro(result)
                }else{
                    Toast.makeText(this@RegistroClienteActivity,"Error no se pudo registrar la nueva cuenta",Toast.LENGTH_LONG).show()
                }
            }
    }

    fun serializarRespuestaRegistro(json: String){
        val gson = Gson()
        val respuestaRegistro = gson.fromJson(json, Mensaje::class.java)
        Toast.makeText(this@RegistroClienteActivity,respuestaRegistro.mensaje,Toast.LENGTH_LONG).show()
        if(!respuestaRegistro.error){
            irPantallaLogin()
        }
    }

    fun irPantallaLogin(){
        var intent = Intent(this@RegistroClienteActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    class DatePickerFragment(val listener:(año:Int, mes:Int,dia:Int)->Unit):DialogFragment(),DatePickerDialog.OnDateSetListener{

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