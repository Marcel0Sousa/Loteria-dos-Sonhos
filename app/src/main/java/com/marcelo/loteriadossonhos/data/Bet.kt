package com.marcelo.loteriadossonhos.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "bet")
data class Bet(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("tipo") val type: String,
    @ColumnInfo("numeros") val numbers: String,
    @ColumnInfo("data") val date: Date = Date(),

    )
