package com.example.calculadoradebitcoin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bloco_cotacao.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL
import java.text.NumberFormat
import java.util.*

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

            //acessando cotação da String em Json
            cotacaoBitcoin = JSONObject(resposta).getJSONObject("ticker").getDouble("last")

            Log.d("RESPOSTA",URL(API_URL).readText())

            //executa a thread principal
            uiThread {
//                toast(resposta).show()
                //Atualiza a tela com a cotação atual
                tv_cotacao.setText(granaToBr(cotacaoBitcoin))
            }
        }
    }
}
