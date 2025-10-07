import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listadecomprasapp.models.Item
import com.example.listadecomprasapp.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddItemBinding
    private var idLista: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idLista = intent.getIntExtra("idLista", -1)

        // Dropdown para categoria (baseado no protótipo)
        val categorias = arrayOf("Fruta", "Carne", "Outros")
        binding.categoria.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias))

        binding.botaoSalvarItem.setOnClickListener {
            val nome = binding.nome.text.toString()
            val quantidade = binding.quantidade.text.toString().toIntOrNull() ?: 0
            val unidade = binding.unidade.text.toString()
            val categoria = binding.categoria.text.toString()

            if (nome.isEmpty() || quantidade <= 0 || unidade.isEmpty() || categoria.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            DataManager.adicionarItem(idLista, Item(0, nome, quantidade, unidade, categoria))
            finish()
        }
    }
}