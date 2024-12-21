fun navigateArrows(from: Char, to: Char): List<String> = when (from) {
    '<' -> when (to) {
        '^' -> listOf(">^A")
        'v' -> listOf(">A")
        '>' -> listOf(">>A")
        'A' -> listOf(">>^A")
        else -> listOf("A")
    }

    'v' -> when (to) {
        '^' -> listOf("^A")
        '<' -> listOf("<A")
        '>' -> listOf(">A")
        'A' -> listOf(">^A", "^>A")
        else -> listOf("A")
    }

    '>' -> when (to) {
        'v' -> listOf("<A")
        '<' -> listOf("<<A")
        'A' -> listOf("^A")
        '^' -> listOf("^<A", "<^A")
        else -> listOf("A")
    }

    '^' -> when (to) {
        'v' -> listOf("vA")
        '<' -> listOf("v<A")
        'A' -> listOf(">A")
        '>' -> listOf(">vA", "v>A")
        else -> listOf("A")
    }

    'A' -> when (to) {
        '^' -> listOf("<A")
        '>' -> listOf("vA")
        'v' -> listOf("<vA", "v<A")
        '<' -> listOf("v<<A")
        else -> listOf("A")
    }

    else -> listOf()
}

fun navigateKeyboard(from: Char, to: Char): List<String> = when (from) {
    'A' -> when (to) {
        '0' -> listOf("<A")
        '1' -> listOf("^<<A")
        '2' -> listOf("^<A", "<^A")
        '3' -> listOf("^A")
        '4' -> listOf("^^<<A")
        '5' -> listOf("^^<A", "<^^A")
        '6' -> listOf("^^A")
        '7' -> listOf("^^^<<A")
        '8' -> listOf("^^^<A", "<^^^A")
        '9' -> listOf("^^^A")
        'A' -> listOf("A")
        else -> emptyList()
    }

    '0' -> when (to) {
        '0' -> listOf("A")
        '1' -> listOf("^<A")
        '2' -> listOf("^A")
        '3' -> listOf("^>A", ">^A")
        '4' -> listOf("^^<A")
        '5' -> listOf("^^A")
        '6' -> listOf("^^>A", ">^^A")
        '7' -> listOf("^^^<A")
        '8' -> listOf("^^^A")
        '9' -> listOf("^^^>A", ">^^^A")
        'A' -> listOf(">A")
        else -> emptyList()
    }

    '1' -> when (to) {
        '0' -> listOf(">vA")
        '1' -> listOf("A")
        '2' -> listOf(">A")
        '3' -> listOf(">>A")
        '4' -> listOf("^A")
        '5' -> listOf("^>A", ">^A")
        '6' -> listOf("^>>A", ">>^A")
        '7' -> listOf("^^A")
        '8' -> listOf("^^>A", ">^^A")
        '9' -> listOf("^^>>A", ">>^^A")
        'A' -> listOf(">>vA")
        else -> emptyList()
    }

    '2' -> when (to) {
        '0' -> listOf("vA")
        '1' -> listOf("<A")
        '2' -> listOf("A")
        '3' -> listOf(">A")
        '4' -> listOf("^<A", "<^A")
        '5' -> listOf("^A")
        '6' -> listOf("^>A", ">^A")
        '7' -> listOf("^^<A", "<^^A")
        '8' -> listOf("^^A")
        '9' -> listOf("^^>A", ">^^A")
        'A' -> listOf(">vA", "v>A")
        else -> emptyList()
    }

    '3' -> when (to) {
        '0' -> listOf("<vA", "v<A")
        '1' -> listOf("<<A")
        '2' -> listOf("<A")
        '3' -> listOf("A")
        '4' -> listOf("^<<A", "<<^A")
        '5' -> listOf("^<A", "<^A")
        '6' -> listOf("^A")
        '7' -> listOf("^^<<A", "<<^^A")
        '8' -> listOf("^^<A", "<^^A")
        '9' -> listOf("^^A")
        'A' -> listOf("vA")
        else -> emptyList()
    }

    '4' -> when (to) {
        '0' -> listOf(">vvA")
        '1' -> listOf("vA")
        '2' -> listOf("v>A", ">vA")
        '3' -> listOf(">>vA", "v>>A")
        '4' -> listOf("A")
        '5' -> listOf(">A")
        '6' -> listOf(">>A")
        '7' -> listOf("^A")
        '8' -> listOf("^>A", ">^A")
        '9' -> listOf(">>^A", "^>>A")
        'A' -> listOf(">>vvA")
        else -> emptyList()
    }

    '5' -> when (to) {
        '0' -> listOf("vvA")
        '1' -> listOf("v<A", "<vA")
        '2' -> listOf("vA")
        '3' -> listOf("v>A", ">vA")
        '4' -> listOf("<A")
        '5' -> listOf("A")
        '6' -> listOf(">>A")
        '7' -> listOf("^<A", "<^A")
        '8' -> listOf("^A")
        '9' -> listOf("^>A", ">^A")
        'A' -> listOf(">vvA", "vv>A")
        else -> emptyList()
    }

    '6' -> when (to) {
        '0' -> listOf("<vvA", "vv<A")
        '1' -> listOf("<<vA", "v<<A")
        '2' -> listOf("v<A", "<vA")
        '3' -> listOf("vA")
        '4' -> listOf("<<A")
        '5' -> listOf("<A")
        '6' -> listOf("A")
        '7' -> listOf("<<^A", "^<<A")
        '8' -> listOf("^<A", "<^A")
        '9' -> listOf("^A")
        'A' -> listOf("vvA")
        else -> emptyList()
    }

    '7' -> when (to) {
        '0' -> listOf(">vvvA")
        '1' -> listOf("vvA")
        '2' -> listOf("vv>A", ">vvA")
        '3' -> listOf("vv>>A", ">>vvA")
        '4' -> listOf("vA")
        '5' -> listOf("v>A", ">vA")
        '6' -> listOf("v>>A", ">>vA")
        '7' -> listOf("A")
        '8' -> listOf(">A")
        '9' -> listOf(">>A")
        'A' -> listOf(">>vvvA")
        else -> emptyList()
    }

    '8' -> when (to) {
        '0' -> listOf("vvvA")
        '1' -> listOf("vv<A", "<vvA")
        '2' -> listOf("vvA")
        '3' -> listOf("vv>A", ">vvA")
        '4' -> listOf("v<A", "<vA")
        '5' -> listOf("^A")
        '6' -> listOf("v>A", ">vA")
        '7' -> listOf("<A")
        '8' -> listOf("A")
        '9' -> listOf(">A")
        'A' -> listOf(">vvvA", "vvv>A")
        else -> emptyList()
    }

    '9' -> when (to) {
        '0' -> listOf("<vvvA", "vvv<A")
        '1' -> listOf("vv<<A", "<<vvA")
        '2' -> listOf("vv<A", "<vvA")
        '3' -> listOf("vvA")
        '4' -> listOf("<<vA", "v<<A")
        '5' -> listOf("<vA", "v<A")
        '6' -> listOf("vA")
        '7' -> listOf("<<A")
        '8' -> listOf("<A")
        '9' -> listOf("A")
        'A' -> listOf("vvvA")
        else -> emptyList()
    }

    else -> emptyList()
}

fun computeLength(str: String, depth: Int): Int {
    if (depth == 0) return str.length
    var curr = 'A'
    var totalLength = 0
    for (cursor in str) {
        val candidates = navigateArrows(curr, cursor)
        var minimalLength = 0
        for (candidate in candidates) {
            val subLength = computeLength(candidate, depth - 1)
            if (minimalLength == 0 || subLength < minimalLength) minimalLength = subLength
        }
        curr = cursor
        totalLength += minimalLength
    }
    return totalLength
}

fun getKeyboardPath(str: String, depth: Int): Int {
    var curr = 'A'
    var length = 0
    for (cursor in str) {
        val candidates = navigateKeyboard(curr, cursor)
        var minimalLength = 0
        for (candidate in candidates) {
            val subLength = computeLength(candidate, depth - 1)
            if (minimalLength == 0 || subLength < minimalLength) minimalLength = subLength
        }
        curr = cursor
        length += minimalLength
    }
    ("$str - $length").println()
    return length * str.substringBefore("A").toInt()
}

fun main() {
    val dayNumber = "Day0X"
    check(getKeyboardPath("029A", 3) == 68* 29)
    check(getKeyboardPath("980A", 3) == 60 * 980)
    check(getKeyboardPath("179A", 3) == 68 * 179)
    check(getKeyboardPath("379A", 3) == 64 * 379)
    check(getKeyboardPath("456A", 3) == 64 * 456)
}
