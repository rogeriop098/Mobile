import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.listadecomprasapp.databinding.ActivityAddListBinding

class AddListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddListBinding
    private var uriImagem: Uri? = null

    private val seletorImagem = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            uriImagem = it
            Glide.with(this).load(it).into(binding.imagemSelecionada)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoSelecionarImagem.setOnClickListener {
            seletorImagem.launch("image/*")
        }

        binding.botaoSalvar.setOnClickListener {
            val titulo = binding.titulo.text.toString()
            if (titulo.isEmpty()) {
                Toast.makeText(this, "Título não pode estar vazio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val novaLista = ListaCompra(
                id = 0,
                titulo = titulo,
                uriImagem = uriImagem?.toString()
            )

            DataManager.adicionarLista(novaLista)
            finish()
        }
    }
}