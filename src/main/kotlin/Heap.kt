class Heap<T>(private val comparator: (parent: T, child: T) -> Boolean) {
    private val heap = arrayListOf<T>()
    val size
        get() = heap.size

    private fun rightIndex(i: Int): Int {
        return 2 * i + 2
    }

    private fun leftIndex(i: Int): Int {
        return 2 * i + 1
    }

    private fun parentIndex(i: Int): Int {
        return (i - 1) / 2
    }

    private fun shiftUp(idx: Int) {
        var i = idx
        while (i > 0 && !comparator(heap[parentIndex(i)], heap[i])) {
            heap[parentIndex(i)] = heap[i].also { heap[i] = heap[parentIndex(i)] }
            i = parentIndex(i)
        }
    }

    private fun shiftDown(idx: Int) {
        var i = idx
        while (leftIndex(i) < size) {
            val left = heap[leftIndex(i)]
            val right = if (rightIndex(i) < size) {
                heap[rightIndex(i)]
            } else {
                null
            }
            val current = heap[i]
            if (right != null && !comparator(current, left) && !comparator(current, right)) {
                if (comparator(right, left)) {
                    heap[rightIndex(i)] = heap[i].also { heap[i] = heap[rightIndex(i)] }
                    i = rightIndex(i)
                } else {
                    heap[leftIndex(i)] = heap[i].also { heap[i] = heap[leftIndex(i)] }
                    i = leftIndex(i)
                }
            } else if (right != null && !comparator(current, right)) {
                heap[rightIndex(i)] = heap[i].also { heap[i] = heap[rightIndex(i)] }
                i = rightIndex(i)
            } else if (!comparator(current, left)) {
                heap[leftIndex(i)] = heap[i].also { heap[i] = heap[leftIndex(i)] }
                i = leftIndex(i)
            } else {
                return
            }
        }
    }

    fun insert(value: T) {
        heap.add(value)
        shiftUp(heap.lastIndex)
    }

    fun extractRoot(): T {
        if (isEmpty()) {
            throw IndexOutOfBoundsException("Empty heap")
        }
        val root = heap[0]
        heap[0] = heap.last()
        heap.removeAt(heap.lastIndex)
        shiftDown(0)
        return root
    }

    fun getSortArray() : List<T> {
        val heap = clone()
        val array = arrayListOf<T>()
        while (heap.size > 0) {
            array.add(heap.extractRoot())
        }
        return array
    }

    fun buildHeap(array: List<T>) {
        clear()
        array.forEach {
            insert(it)
        }
    }

    fun getRoot() = if (isNotEmpty()) {
        heap[0]
    } else {
        null
    }

    fun clear() {
        heap.clear()
    }

    fun isNotEmpty() = heap.isNotEmpty()

    fun isEmpty() = heap.isEmpty()

    fun clone(): Heap<T> {
        val heap = Heap(comparator)
        heap.buildHeap(this.heap)
        return heap
    }
}