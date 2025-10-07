import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listadecomprasapp.R
import com.example.listadecomprasapp.databinding.ItemListaComprasBinding

class ListaComprasAdapter(private val onClick: (ListaCompra) -> Unit, private val onEdit: (Int) -> Unit, private val onDelete: (Int) -> Unit) : RecyclerView.Adapter<ListaComprasAdapter.ViewHolder>() {
    private var listas: List<ListaCompra> = emptyList()

    fun atualizarListas(novasListas: List<ListaCompra>) {
        listas = novasListas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListaComprasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = listas[position]
        holder.binding.tituloLista.text = lista.titulo
        Glide.with(holder.itemView.context).load(lista.uriImagem ?: R.drawable.placeholder).into(holder.binding.imagemLista)
        holder.itemView.setOnClickListener { onClick(lista) }
        holder.binding.botaoEditar.setOnClickListener { onEdit(lista.id) }
        holder.binding.botaoExcluir.setOnClickListener { onDelete(lista.id) }
    }

    override fun getItemCount() = listas.size

    class ViewHolder(val binding: ItemListaComprasBinding) : RecyclerView.ViewHolder(binding.root)
}