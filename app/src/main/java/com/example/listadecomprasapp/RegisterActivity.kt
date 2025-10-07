import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listadecomprasapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoCriar.setOnClickListener {
            val nome = binding.nome.text.toString()
            val email = binding.email.text.toString()
            val senha = binding.senha.text.toString()
            val confirmarSenha = binding.confirmarSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(this, "Campos vazios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmarSenha) {
                Toast.makeText(this, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            DataManager.usuarios.add(Usuario(nome, email, senha))
            Toast.makeText(this, "Usuário cadastrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}