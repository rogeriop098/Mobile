import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadecomprasapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListaComprasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity", "onCreate chamado")

        setSupportActionBar(binding.toolbar)

        // Dados de teste temporários
        if (DataManager.listasCompras.isEmpty()) {
            DataManager.listaCompras = mutableListOf(ListaCompras(1, "Mercado", emptyList()))
        }

        adapter = ListaComprasAdapter(
            onClick = { lista ->
                startActivity(Intent(this, ListDetailsActivity::class.java).putExtra("idLista", lista.id))
            },
            onEdit = { idLista ->
                startActivity(Intent(this, EditListActivity::class.java).putExtra("idLista", idLista))
            },
            onDelete = { idLista ->
                DataManager.excluirLista(idLista)
                this.atualizarListas()
            }
        )

        binding.recyclerListas.layoutManager = LinearLayoutManager(this)
        binding.recyclerListas.adapter = adapter

        binding.botaoAdicionarLista.setOnClickListener {
            startActivity(Intent(this, AddListActivity::class.java))
        }

        binding.barraPesquisa.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val listasFiltradas = DataManager.buscarListas(newText.orEmpty()).sortedBy { it.titulo }
                adapter.atualizarListas(listasFiltradas)
                return true
            }
        })

        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
        binding.toolbar.setNavigationOnClickListener {
            DataManager.usuarioAtual = null
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun atualizarListas() {
        val listas = DataManager.listasCompras.sortedBy { it.titulo }
        adapter.atualizarListas(listas)
        Log.d("MainActivity", "Listas atualizadas: ${listas.size}")
    }

    override fun onResume() {
        super.onResume()
        atualizarListas()
    }
}