package com.cliente.smartcupon.interfaces

import com.cliente.smartcupon.poko.Promocion
import com.cliente.smartcupon.poko.Sucursal

interface NotificacionListaSucursal {
    fun clicItemLista(posicion: Int, sucursal: Sucursal)

}