import com.example.listadecomprasapp.models.Item

object DataManager {
    val usuarios = mutableListOf<Usuario>()
    var usuarioAtual: Usuario? = null
    val listasCompras = mutableListOf<ListaCompra>()
    private var contadorIdLista = 0
    private var contadorIdItem = 0

    fun adicionarLista(lista: ListaCompra) {
        listasCompras.add(lista.copy(id = contadorIdLista++))
    }

    fun editarLista(id: Int, titulo: String, uriImagem: String?) {
        val lista = listasCompras.find { it.id == id }
        lista?.titulo = titulo
        lista?.uriImagem = uriImagem
    }

    fun excluirLista(id: Int) {
        listasCompras.removeIf { it.id == id }
    }

    fun adicionarItem(idLista: Int, item: Item) {
        val lista = listasCompras.find { it.id == idLista }
        lista?.itens?.add(item.copy(id = contadorIdItem++))
    }

    fun editarItem(idLista: Int, idItem: Int, nome: String, quantidade: Int, unidade: String, categoria: String) {
        val item = listasCompras.find { it.id == idLista }?.itens?.find { it.id == idItem }
        item?.nome = nome
        item?.quantidade = quantidade
        item?.unidade = unidade
        item?.categoria = categoria
    }

    fun excluirItem(idLista: Int, idItem: Int) {
        listasCompras.find { it.id == idLista }?.itens?.removeIf { it.id == idItem }
    }

    fun alternarComprado(idLista: Int, idItem: Int) {
        val item = listasCompras.find { it.id == idLista }?.itens?.find { it.id == idItem }
        item?.comprado = !item.comprado
    }

    fun buscarListas(query: String): List<ListaCompra> {
        return listasCompras.filter { it.titulo.contains(query, ignoreCase = true) }
    }

    fun buscarItens(idLista: Int, query: String): List<Item> {
        return listasCompras.find { it.id == idLista }?.itens?.filter { it.nome.contains(query, ignoreCase = true) } ?: emptyList()
    }
}