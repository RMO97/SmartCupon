package com.cliente.smartcupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cliente.smartcupon.databinding.ActivityLoginBinding
import com.cliente.smartcupon.poko.Cliente
import com.cliente.smartcupon.poko.RespuestaLogin
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.koushikdutta.ion.Ion

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnIniciarSesion.setOnClickListener {
            val correoElectronico = binding.etCorreo.text.toString()
            val password = binding.etPassword.text.toString()

            if(validarCamposLogin(correoElectronico,password)){
                realizarPeticionLogin(correoElectronico,password)
            }
        }
        binding.btnRegistrarCliente.setOnClickListener {
            irPantallaRegistro()
        }
    }

    fun validarCamposLogin(correoElectronico: String, password:String):Boolean{
        var isValido = true
        if (correoElectronico.isEmpty()){
            isValido = false
            binding.etCorreo.error = "Correo electronico obligatorio"
        }
        if (password.isEmpty()){
            isValido = false
            binding.etPassword.error = "Contraseña obligatoria"
        }

        return isValido
    }

    fun realizarPeticionLogin(correoElectronico: String, password:String){
        Ion.getDefault(this@LoginActivity).conscryptMiddleware.enable(false)
        Ion.with(this@LoginActivity)
            .load("POST", Constantes.URL_WS+"autenticacion/loginMovil")
            .setHeader("Content-Type","application/x-www-form-urlencoded")
            .setBodyParameter("correoElectronico",correoElectronico)
            .setBodyParameter("password",password)
            .asString()
            .setCallback { e, result ->
                if (e == null && result !=null){
                    serializarRespuestaLogin(result)
                }else{
                    Toast.makeText(this@LoginActivity,"Error no se pudo ingresar al sistema", Toast.LENGTH_LONG).show()
                    println(e.toString())
                }
            }
    }

    fun serializarRespuestaLogin(json: String){
        val gson = Gson()
        val respuestaLogin = gson.fromJson(json, RespuestaLogin::class.java)
        Toast.makeText(this@LoginActivity, respuestaLogin.mensaje,Toast.LENGTH_SHORT).show()
        if (!respuestaLogin.error){
            irPantallaPrincipal(respuestaLogin.cliente)
        }
    }

    fun irPantallaPrincipal(cliente: Cliente){
        var intent = Intent(this@LoginActivity, MainActivity::class.java)
        val gson = Gson()
        val cadenaJson:String = gson.toJson(cliente)
        intent.putExtra("cliente",cadenaJson)
        startActivity(intent)
        finish()
    }

    fun irPantallaRegistro(){
        var intent = Intent(this@LoginActivity, RegistroClienteActivity::class.java )
        startActivity(intent)
        finish()
    }
}