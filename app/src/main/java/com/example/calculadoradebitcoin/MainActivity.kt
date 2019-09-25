package com.example.calculadoradebitcoin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.bloco_cotacao.*
import kotlinx.android.synthetic.main.bloco_entrada.*
import kotlinx.android.synthetic.main.bloco_saida.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    val API_URL = "https://www.mercadobitcoin.net/api/BTC/ticker/"

    var cotacaoBitcoin:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscarCotacao()

        btn_calcular.setOnClickListener(){
            calcular()
        }

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

    fun calcular(){
        if (et_valor_reais.text.isEmpty()){
            et_valor_reais.error = "Preencha um valor!"
            return
        }

        var valor_digitadoSemVirgulas = et_valor_reais.text.toString().replace(",",".")
        //quando tiver mais de 1 ponto no valor após o replace
        if(valor_digitadoSemVirgulas.split(".").size > 2){
            Log.i("VALOR_CONVERTIDO",removePontosExcedentes(valor_digitadoSemVirgulas))
            valor_digitadoSemVirgulas = removePontosExcedentes(valor_digitadoSemVirgulas)
        }

        val valor_digitado = valor_digitadoSemVirgulas.toDouble()

        val resultado = if(cotacaoBitcoin > 0) valor_digitado / cotacaoBitcoin else 0.0

        //atualiza textview com o resultado formatado em 8 casas decimais
        tv_qtd_bitcoins.text = "%.8f".format(resultado)
    }

    fun removePontosExcedentes(valor:String):String{
        val arrayDoTexto = valor.split(".")
        var tamArray:Int = valor.split(".").size
        var textoDouble:String = ""

        var cont = 0
        while(cont < tamArray-1){
            textoDouble += arrayDoTexto[cont]
            cont++
        }



        return textoDouble+"."+arrayDoTexto[arrayDoTexto.size-1]
    }
}
