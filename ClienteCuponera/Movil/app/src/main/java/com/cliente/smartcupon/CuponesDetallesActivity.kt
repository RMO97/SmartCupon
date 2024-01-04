package com.cliente.smartcupon

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cliente.smartcupon.databinding.ActivityCuponesDetallesBinding
import com.cliente.smartcupon.interfaces.NotificacionLista
import com.cliente.smartcupon.interfaces.NotificacionListaSucursal
import com.cliente.smartcupon.poko.Promocion
import com.cliente.smartcupon.utils.Constantes
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import com.cliente.smartcupon.poko.Sucursal as Sucursal

class CuponesDetallesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuponesDetallesBinding
    private lateinit var promocion: Promocion
    var categoria: String = ""
    var idPromocion = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val categoria = intent.getStringExtra("categoria")
        binding = ActivityCuponesDetallesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        idPromocion = intent.getIntExtra("idPromocion", -1)
        descargarInformacionCupon(idPromocion)

        val copyButton = findViewById<Button>(R.id.copyButton)
        copyButton.setOnClickListener {
            val codigoPromocion = binding.codeText.text.toString()
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Código de Cupón", codigoPromocion)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this@CuponesDetallesActivity, "Código copiado al portapapeles", Toast.LENGTH_SHORT).show()

        }
        binding.btnRegresar.setOnClickListener {
            // Devolverse a ListaPromocionesActivity y pasar la categoría
            val intent = Intent(this@CuponesDetallesActivity, ListaPromocionesActivity::class.java)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
            finish()
        }
        binding.btnVerSucursal.setOnClickListener {
            val intent = Intent(this@CuponesDetallesActivity, SucursalActivity::class.java)
            intent.putExtra("idPromocion", idPromocion)
            intent.putExtra("categoria", categoria)
            startActivity(intent)
            finish()
        }
    }

    fun descargarInformacionCupon(idPromocion: Int) {
        Ion.with(this@CuponesDetallesActivity)
            .load("GET", "${Constantes.URL_WS}promocion/obtenerPromocionesPorId/$idPromocion")
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    try {
                        val promocion: Promocion = Gson().fromJson(result, Promocion::class.java)
                        cargarInformacionPromocion(promocion)
                    } catch (ex: JsonSyntaxException) {
                        Toast.makeText(this@CuponesDetallesActivity, "Error al parsear la respuesta JSON", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@CuponesDetallesActivity, "Error al obtener detalles de la promoción", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun cargarInformacionPromocion(promocion: Promocion) {
        binding.tvCupon.text = promocion.nombrePromocion
        binding.codeText.text = promocion.codigoPromocion
        binding.detailTextView.text = "Detalles: ${promocion.descripcion}"
        binding.valueTextView.text = "Valor: ${promocion.valor.toString()}"
        binding.typeTextView.text = "Tipo: ${obtenerTipoPromocion(promocion.tipoPromocion)}"
        binding.startDateTextView.text = "Fecha Inicio: ${promocion.fechaDeInicioPromocion}"
        binding.endDateTextView.text = "Fecha Final: ${promocion.fechaDeExpiracionPromocion}"
        binding.restrictionTextView.text = "Restriccion: ${promocion.restriccion}"
        val imageView = binding.imageView
        decodeBase64AndSetImage(promocion.fotografiaBase64, imageView)
    }

    private fun decodeBase64AndSetImage(base64: String, imageView: ImageView) {
        val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        imageView.setImageBitmap(bitmap)
    }

    private fun obtenerTipoPromocion(tipo: Boolean): String {
        return if (tipo) {
            "Descuento"
        } else {
            "Rebaja"
        }
    }
}
