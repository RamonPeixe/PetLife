package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditVacinaBinding

class EditVacinaActivity : AppCompatActivity() {

    private lateinit var aevb: ActivityEditVacinaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aevb = ActivityEditVacinaBinding.inflate(layoutInflater)

        //Define o layout
        setContentView(aevb.root)

        //Pega a data da última vacinação
        val ultimaVacina = intent.getStringExtra("ultimaVacina")
        aevb.ultimaVacinaEt.setText(ultimaVacina)

        //Configura o botão de salvar para retornar os dados atualizados
        aevb.salvarBtn.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("ultimaVacina", aevb.ultimaVacinaEt.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish() //Finaliza a activity
        }
    }
}
