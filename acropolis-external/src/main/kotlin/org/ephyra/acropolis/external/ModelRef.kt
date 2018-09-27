package org.ephyra.acropolis.external

/**
 * Types for supported references in the external model
 */
enum class RefType {
    APPLICATION,
    SYSTEM
}

/**
 * Extract a reference from its string format
 */
fun extractRef(ref: String): Pair<RefType, String> {
    val refParts = ref.split(".")

    if (refParts.size != 2) {
        throw IllegalArgumentException("Cannot resolve ref because the format is invalid.")
    }

     val refType = when (refParts[0]) {
         "application" -> RefType.APPLICATION
         "system" -> RefType.SYSTEM
         else -> throw java.lang.IllegalArgumentException("Provided ref [${refParts[0]}] is not a known ref type")
    }

    return Pair(refType, refParts[1])
}