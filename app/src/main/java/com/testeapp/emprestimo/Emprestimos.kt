package com.testeapp.emprestimo

import android.os.Parcel
import android.os.Parcelable

class Emprestimos() : Parcelable {
    var nomeCliente: String? = null
    var cpf: String? = null
    var valor: String? = null
    var vencimento: String? = null
    var juros: String? = null
    var timestamp: String? = null

    constructor(
        nomeCliente: String?,
        cpf: String?,
        valor: String?,
        vencimento: String?,
        juros: String?,
        timestamp: String?
    ) : this() {
        this.nomeCliente = nomeCliente
        this.cpf = cpf
        this.valor = valor
        this.vencimento = vencimento
        this.juros = juros
        this.timestamp = timestamp
    }

    constructor(parcel: Parcel) : this() {
        nomeCliente = parcel.readString()
        cpf = parcel.readString()
        valor = parcel.readString()
        vencimento = parcel.readString()
        juros = parcel.readString()
        timestamp = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nomeCliente)
        parcel.writeString(cpf)
        parcel.writeString(valor)
        parcel.writeString(vencimento)
        parcel.writeString(juros)
        parcel.writeString(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Emprestimos> {
        override fun createFromParcel(parcel: Parcel): Emprestimos {
            return Emprestimos(parcel)
        }

        override fun newArray(size: Int): Array<Emprestimos?> {
            return arrayOfNulls(size)
        }
    }
}



