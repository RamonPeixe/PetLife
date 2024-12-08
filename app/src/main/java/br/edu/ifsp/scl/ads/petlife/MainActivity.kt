package br.edu.ifsp.scl.ads.petlife

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Launcher para adicionar um novo pet
    private lateinit var editPetLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        // Configura a toolbar
        setSupportActionBar(amb.toolbarTb)
        supportActionBar?.apply {
            title = getString(R.string.app_name)
        }

        // Configura o launcher para abrir a tela de cadastro de pet
        editPetLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let {

                }
            }
        }

        // Configura o clique no botão "+" para abrir a tela de cadastro de pet
        amb.addPetBtn.setOnClickListener {
            val intent = Intent(this, EditPetActivity::class.java)
            editPetLauncher.launch(intent)
        }
    }
}
