package com.example.demo_kot.util.tablegenerator.type

import java.math.BigInteger
import java.time.Instant
import kotlin.reflect.KClass

enum class DataType(val databaseName: String, val javaClass: KClass<*>) {
    TINYINT("TINYINT", Int::class),
    INT_INTEGER("INT", Int::class),
    INT_LONG("INT", Long::class),
    BIGINT("BIGINT", BigInteger::class),
    CHAR_20("CHAR(25)", String::class),
    CHAR_50("CHAR(55)", String::class),
    CHAR_100("CHAR(105)", String::class),
    CHAR_250("CHAR(255)", String::class),
    VARCHAR_500("VARCHAR(550)", String::class),
    VARCHAR_1000("VARCHAR(1050)", String::class),
    VARCHAR_5000("VARCHAR(5050)", String::class),
    VARCHAR_10000("VARCHAR(10050)", String::class),
    DATETIME("DATETIME", Instant::class);

}