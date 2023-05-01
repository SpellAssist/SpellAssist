package spellAssist.tagStream
import smile.graph.AdjacencyList
import spellAssist.common.Picture
import kotlin.math.max

class tagStream {
    private var m_tags = HashMap<String, Int>()
    private var m_edges = AdjacencyList(1, false)

    public fun numTags(): Int {
        return this.m_tags.count()
    }

    public fun numEdges(): Int {
        return this.m_edges.edges.count()
    }

    public fun reserve(cap: Int) {
        if (this.m_edges.numVertices >= cap) {
            return
        }
        resizeGraph(cap)
    }

    public fun capacity(): Int {
        return this.m_edges.numVertices
    }

    public fun shrinkToFit() {
        this.resizeGraph(this.m_tags.size)
    }

    public fun tags(): HashMap<String, Int> {
        return this.m_tags
    }

    public fun graph(): AdjacencyList {
        return this.m_edges
    }

    public fun clear() {
        this.m_tags.clear()
        this.m_edges.edges.clear()
    }

    public fun processPic(p: Picture) {
        for (i in 0 until p.tags.size) {
            for (j in (i + 1) until p.tags.size) {
                this.processTag(p.tags[i], p.tags[j])
            }
        }
    }

    private fun processTag(t1: String, t2: String) {
        val i1 = this.emplaceTag(t1)
        val i2 = this.emplaceTag(t2)
        if (i1 == i2) {
            return
        }

        if (max(i1, i2) >= this.capacity()) {
            this.reserve(this.capacity() * 2)
        }

        val edge = this.m_edges.getEdge(i1, i2)
        if (edge != null) {
            this.m_edges.setWeight(i1, i2, edge.weight + 1)
        } else {
            this.m_edges.addEdge(i1, i2, 1.0)
        }
    }

    private fun emplaceTag(t: String): Int {
        val temp = this.m_tags[t]
        if (temp != null) {
            return temp
        } else {
            val idx = this.m_tags.size
            this.m_tags[t] = idx
            return idx
        }
    }

    private fun resizeGraph(newSize: Int) {
        assert(newSize >= this.m_tags.count())

        var newGraph = AdjacencyList(newSize, false)
        for (edge in this.m_edges.edges) {
            newGraph.addEdge(edge.v1, edge.v2, edge.weight)
        }

        this.m_edges = newGraph
    }
}