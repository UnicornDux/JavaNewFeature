package com.wp.bluetooth.entity

data class InitValue(
    val deviceCode: String,
    val electricity: Int,
    val lightIntensity: Int
);

data class PairValue(
    val deviceCode: String,
    val electricity: Int,
    val lightIntensity: Int,
    val signal: String
)
