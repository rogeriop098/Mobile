import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.listadecomprasapp.databinding.ActivityAddListBinding

class EditListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddListBinding
    private var idLista: Int = -1
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

        idLista = intent.getIntExtra("idLista", -1)
        val lista = DataManager.listasCompras.find { it.id == idLista }
        if (lista != null) {
            binding.titulo.setText(lista.titulo)
            if (lista.uriImagem != null) {
                Glide.with(this).load(Uri.parse(lista.uriImagem)).into(binding.imagemSelecionada)
            }
        }

        binding.botaoSelecionarImagem.setOnClickListener {
            seletorImagem.launch("image/*")
        }

        binding.botaoSalvar.setOnClickListener {
            val titulo = binding.titulo.text.toString()
            if (titulo.isEmpty()) {
                Toast.makeText(this, "Título não pode estar vazio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            DataManager.editarLista(idLista, titulo, uriImagem?.toString() ?: lista?.uriImagem)
            finish()
        }
    }
}