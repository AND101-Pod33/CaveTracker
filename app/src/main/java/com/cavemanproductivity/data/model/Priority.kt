package com.cavemanproductivity.data.model

enum class Priority(val displayName: String, val level: Int) {
    LOW("Small Rock", 1),
    MEDIUM("Medium Rock", 2),
    HIGH("Large Rock", 3),
    URGENT("Boulder", 4)
}