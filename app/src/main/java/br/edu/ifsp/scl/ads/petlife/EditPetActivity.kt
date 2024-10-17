package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditPetBinding

class EditPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nomePet = intent.getStringExtra("nome") ?: "Nome não informado"
        val dataNascimento = intent.getStringExtra("dataNascimento") ?: "Data não informada"
        val tipo = intent.getStringExtra("tipo") ?: "Tipo não informado"
        val cor = intent.getStringExtra("cor") ?: "Cor não informada"
        val porte = intent.getStringExtra("porte") ?: "Porte não informado"

        binding.nomePetEt.setText(nomePet)
        binding.dataNascimentoPetEt.setText(dataNascimento)
        binding.tipoPetEt.setText(tipo)
        binding.corPetEt.setText(cor)
        binding.portePetEt.setText(porte)

        binding.salvarBtn.setOnClickListener {
            val resultadoIntent = Intent().apply {
                putExtra("nome", binding.nomePetEt.text.toString())
                putExtra("dataNascimento", binding.dataNascimentoPetEt.text.toString())
                putExtra("tipo", binding.tipoPetEt.text.toString())
                putExtra("cor", binding.corPetEt.text.toString())
                putExtra("porte", binding.portePetEt.text.toString())
            }

            setResult(Activity.RESULT_OK, resultadoIntent)
            finish()
        }
    }
}
