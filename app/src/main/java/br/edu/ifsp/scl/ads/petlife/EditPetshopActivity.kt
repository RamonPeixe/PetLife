package br.edu.ifsp.scl.ads.petlife

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEditPetshopBinding

class EditPetshopActivity : AppCompatActivity() {

    private lateinit var aepb: ActivityEditPetshopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aepb = ActivityEditPetshopBinding.inflate(layoutInflater)

        //Define o layout
        setContentView(aepb.root)

        //Pega a data da última ida ao petshop
        val ultimaIdaPetshop = intent.getStringExtra("ultimaIdaPetshop")
        aepb.ultimaIdaPetshopEt.setText(ultimaIdaPetshop)

        //Configura o botão de salvar para retornar os dados atualizados
        aepb.salvarBtn.setOnClickListener {
            val resultIntent = Intent().apply {
                putExtra("ultimaIdaPetshop", aepb.ultimaIdaPetshopEt.text.toString())
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish() //Finaliza a activity
        }
    }
}
