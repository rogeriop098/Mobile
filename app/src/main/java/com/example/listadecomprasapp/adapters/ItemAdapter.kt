import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listadecomprasapp.R
import com.example.listadecomprasapp.databinding.ItemListaItensBinding
import com.example.listadecomprasapp.models.Item

class ItemAdapter(
    private val onCheck: (Int) -> Unit,
    private val onEdit: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private var itens: List<Item> = emptyList()

    fun atualizarItens(novosItens: List<Item>) {
        itens = novosItens
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListaItensBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itens[position]
        holder.binding.nomeItem.text = item.nome
        holder.binding.quantidadeItem.text = "${item.quantidade} ${item.unidade} (${item.categoria})"
        holder.binding.checkComprado.isChecked = item.comprado
        holder.binding.checkComprado.setOnCheckedChangeListener { _, _ -> onCheck(item.id) }
        holder.binding.botaoEditarItem.setOnClickListener { onEdit(item.id) }
        holder.binding.botaoExcluirItem.setOnClickListener { onDelete(item.id) }
        holder.binding.iconeCategoria.setImageResource(when (item.categoria.lowercase()) {
            "fruta" -> R.drawable.ic_fruta
            "carne" -> R.drawable.ic_carne
            else -> R.drawable.ic_default
        })
    }

    override fun getItemCount() = itens.size

    class ViewHolder(val binding: ItemListaItensBinding) : RecyclerView.ViewHolder(binding.root)
}