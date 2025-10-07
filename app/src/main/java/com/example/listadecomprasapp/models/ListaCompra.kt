import com.example.listadecomprasapp.models.Item

data class ListaCompra(
    val id: Int,
    var titulo: String,
    var uriImagem: String? = null,
    val itens: MutableList<Item> = mutableListOf()
)