package com.cliente.smartcupon.interfaces

import com.cliente.smartcupon.poko.Promocion
import com.cliente.smartcupon.poko.Sucursal

interface NotificacionLista {
    fun clicItemLista(posicion: Int, promocion: Promocion)

}