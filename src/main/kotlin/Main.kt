
fun main() {
    val heap = Heap<Int> { parent, child -> parent < child }

    heap.insert(8)
    heap.insert(6)
    heap.insert(-1)
    heap.insert(-3)
    heap.insert(5)
    heap.insert(-5)

    println(heap.getSortArray())
}
