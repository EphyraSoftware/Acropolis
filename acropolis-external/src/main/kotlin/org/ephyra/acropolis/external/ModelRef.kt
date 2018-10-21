package org.ephyra.acropolis.external

import java.lang.IllegalStateException

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

/**
 * Pack a reference into its string format from its type and name
 */
fun packRef(refType: RefType, name: String): String {
    val refTypeString = when(refType) {
        RefType.APPLICATION -> "application"
        RefType.SYSTEM -> "system"
    }

    return "$refTypeString.$name"
}

/**
 * Types of supported system software specializations
 */
enum class SystemSoftwareSpecialization {
    ReverseProxy,
    LoadBalancer,
    Queue,
    Datastore,
    UnknownSpecialization
}

/**
 * Extracts the enumerated type from the input name.
 * Unrecognised names are mapped to the UnknownSpecialization enumeration value.
 *
 * Note that the match against the name is NOT case-sensitive.
 */
fun extractSystemSpecialization(name: String): SystemSoftwareSpecialization {
    return when (name.toUpperCase()) {
        "REVERSEPROXY" -> SystemSoftwareSpecialization.ReverseProxy
        "LOADBALANCER" -> SystemSoftwareSpecialization.LoadBalancer
        "QUEUE" -> SystemSoftwareSpecialization.Queue
        "DATASTORE" -> SystemSoftwareSpecialization.Datastore
        else -> SystemSoftwareSpecialization.UnknownSpecialization
    }
}

/**
 * Extracts the enumerated type from the input name.
 * Unrecognised names are mapped to the UnknownSpecialization enumeration value.
 *
 * Note that the match against the name is NOT case-sensitive.
 */
fun packSystemSpecialization(systemSoftwareSpecialization: SystemSoftwareSpecialization): String {
    return when (systemSoftwareSpecialization) {
        SystemSoftwareSpecialization.ReverseProxy -> "ReverseProxy"
        SystemSoftwareSpecialization.LoadBalancer -> "LoadBalancer"
        SystemSoftwareSpecialization.Queue -> "Queue"
        SystemSoftwareSpecialization.Datastore -> "Datastore"
        else -> throw IllegalStateException("Missing conversion for specialization [$systemSoftwareSpecialization]")
    }
}
