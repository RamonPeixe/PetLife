package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditPetBinding

class EditPetActivity : AppCompatActivity() {
    private lateinit var aepb: ActivityEditPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aepb = ActivityEditPetBinding.inflate(layoutInflater)
        setContentView(aepb.root)

        val nomePet = intent.getStringExtra("nome") ?: "Nome não informado"
        val dataNascimento = intent.getStringExtra("dataNascimento") ?: "Data não informada"
        val tipo = intent.getStringExtra("tipo") ?: "Tipo não informado"
        val cor = intent.getStringExtra("cor") ?: "Cor não informada"
        val porte = intent.getStringExtra("porte") ?: "Porte não informado"

        aepb.nomePetEt.setText(nomePet)
        aepb.dataNascimentoPetEt.setText(dataNascimento)
        aepb.tipoPetEt.setText(tipo)
        aepb.corPetEt.setText(cor)
        aepb.portePetEt.setText(porte)

        aepb.salvarBtn.setOnClickListener {
            val resultadoIntent = Intent().apply {
                putExtra("nome", aepb.nomePetEt.text.toString())
                putExtra("dataNascimento", aepb.dataNascimentoPetEt.text.toString())
                putExtra("tipo", aepb.tipoPetEt.text.toString())
                putExtra("cor", aepb.corPetEt.text.toString())
                putExtra("porte", aepb.portePetEt.text.toString())
            }

            setResult(Activity.RESULT_OK, resultadoIntent)
            finish()
        }
    }
}
