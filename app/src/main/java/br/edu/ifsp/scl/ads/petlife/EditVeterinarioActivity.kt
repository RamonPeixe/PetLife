package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditVeterinarioBinding

class EditVeterinarioActivity : AppCompatActivity() {

    private lateinit var aevb: ActivityEditVeterinarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aevb = ActivityEditVeterinarioBinding.inflate(layoutInflater)

        //Define o layout
        setContentView(aevb.root)

        //Pega a data da última visita ao veterinário
        val ultimaVisitaVeterinario = intent.getStringExtra("ultimaVisitaVeterinario")
        aevb.ultimaVisitaVeterinarioEt.setText(ultimaVisitaVeterinario)

        //Configura o botão de salvar para retornar os dados atualizados
        aevb.salvarBtn.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("ultimaVisitaVeterinario", aevb.ultimaVisitaVeterinarioEt.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish() //Finaliza a activity
        }
    }
}
