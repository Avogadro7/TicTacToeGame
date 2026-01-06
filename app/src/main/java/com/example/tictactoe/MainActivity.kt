package com.example.tictactoe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        }

        binding.reportBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.data = Uri.parse("Email")
            val emails = arrayOf("avazovaziz39161@gmail.com")
            intent.putExtra(Intent.EXTRA_EMAIL, emails)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Tic tac toe game")
            intent.putExtra(Intent.EXTRA_TEXT, "Maslahatingiz:")
            intent.type = "message/rfc822"
            val emailIntent = Intent.createChooser(intent, "Launch email")
            startActivity(emailIntent)
        }
    }
}