package com.example.calculadoradebitcoin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"

    var cotacaoBitcoin:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscarCotacao()

    }

    fun buscarCotacao(){

        //para executar uma tarefa assíncrona (poderemos não ter uma resposta imediata)
        doAsync {
            //Acessar a API e buscar seu resultado
            val resposta = URL(API_URL).readText()

            Log.d("RESPOSTA",URL(API_URL).readText())

            uiThread {
                toast(resposta).show()
            }
        }
    }
}
