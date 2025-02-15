/*
 * This file is generated by jOOQ.
 */
package com.sljricardo.jooq.tables.pojos


import java.io.Serializable


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class Task(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val assigneeId: String? = null
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: Task = other as Task
        if (this.id == null) {
            if (o.id != null)
                return false
        }
        else if (this.id != o.id)
            return false
        if (this.name == null) {
            if (o.name != null)
                return false
        }
        else if (this.name != o.name)
            return false
        if (this.description == null) {
            if (o.description != null)
                return false
        }
        else if (this.description != o.description)
            return false
        if (this.assigneeId == null) {
            if (o.assigneeId != null)
                return false
        }
        else if (this.assigneeId != o.assigneeId)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.id == null) 0 else this.id.hashCode())
        result = prime * result + (if (this.name == null) 0 else this.name.hashCode())
        result = prime * result + (if (this.description == null) 0 else this.description.hashCode())
        result = prime * result + (if (this.assigneeId == null) 0 else this.assigneeId.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("Task (")

        sb.append(id)
        sb.append(", ").append(name)
        sb.append(", ").append(description)
        sb.append(", ").append(assigneeId)

        sb.append(")")
        return sb.toString()
    }
}
