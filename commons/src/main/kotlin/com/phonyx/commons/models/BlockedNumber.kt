package com.phonyx.commons.models


data class BlockedNumber(val id: Long, val number: String, val normalizedNumber: String, val numberToCompare: String, val contactName: String? = null)
