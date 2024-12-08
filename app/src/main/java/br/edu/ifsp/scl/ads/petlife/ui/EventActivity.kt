package br.edu.ifsp.scl.ads.petlife.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.petlife.R
import br.edu.ifsp.scl.ads.petlife.databinding.ActivityEventBinding

class EventActivity : AppCompatActivity() {
    private val aeb: ActivityEventBinding by lazy {
        ActivityEventBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(aeb.root)

        // Configura a toolbar
        setSupportActionBar(aeb.toolbarTb)
        supportActionBar?.title = getString(R.string.event_list)

        // Configura o botão de adicionar eventos
        aeb.addEventBtn.setOnClickListener {
        }
    }
}
