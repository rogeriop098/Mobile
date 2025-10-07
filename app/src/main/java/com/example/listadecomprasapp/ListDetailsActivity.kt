import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadecomprasapp.databinding.ActivityListDetailsBinding

class ListDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailsBinding
    private var idLista: Int = -1
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idLista = intent.getIntExtra("idLista", -1)
        val lista = DataManager.listasCompras.find { it.id == idLista }
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = lista?.titulo

        adapter = ItemAdapter(

            onCheck = { idItem ->
                DataManager.alternarComprado(idLista, idItem)
                atualizarItens()
            },

            onEdit = { idItem ->
                startActivity(Intent(this, EditItemActivity::class.java).putExtra("idLista", idLista).putExtra("idItem", idItem))
            },

            onDelete = { idItem ->
                DataManager.excluirItem(idLista, idItem)
                atualizarItens()
            }
        )

        binding.recyclerItens.layoutManager = LinearLayoutManager(this)
        binding.recyclerItens.adapter = adapter
        atualizarItens()

        binding.botaoAdicionarItem.setOnClickListener {
            startActivity(Intent(this, AddItemActivity::class.java).putExtra("idLista", idLista))
        }

        binding.barraPesquisaItens.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.atualizarItens(DataManager.buscarItens(idLista, newText.orEmpty()).sortedWith(compareBy({ it.comprado }, { it.categoria }, { it.nome })))
                return true
            }
        })
    }

    private fun atualizarItens() {
        val itens = DataManager.listasCompras.find { it.id == idLista }?.itens?.sortedWith(compareBy({ it.comprado }, { it.categoria }, { it.nome })) ?: emptyList()
        adapter.atualizarItens(itens)
    }

    override fun onResume() {
        super.onResume()
        atualizarItens()
    }
}