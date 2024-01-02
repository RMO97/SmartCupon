package com.cliente.smartcupon.interfaces

import com.cliente.smartcupon.poko.Promocion

interface NotificacionLista {
    fun clicItemLista(posicion : Int, promocion : Promocion)
}