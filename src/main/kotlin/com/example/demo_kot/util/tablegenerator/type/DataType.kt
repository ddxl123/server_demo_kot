package com.example.demo_kot.util.tablegenerator.type

import kotlin.reflect.KClass

enum class DataType(val dataTypeName: String, val kClass: KClass<*>) {
    TINYINT("TINYINT", Int::class),// (-128，127)
    SMALLINT("SMALLINT", Int::class),// (-32 768，32 767)
    INT("INT", Int::class),// (-2 147 483 648，2 147 483 647)
    BIGINT("BIGINT", Long::class),// (-9 223 372 036 854 775 808，9 223 372 036 854 775 807)
    CHAR_20("CHAR(30)", String::class),
    CHAR_50("CHAR(60)", String::class),
    CHAR_100("CHAR(110)", String::class),
    CHAR_250("CHAR(260)", String::class),
    VARCHAR_500("VARCHAR(510)", String::class),
    VARCHAR_1000("VARCHAR(1010)", String::class),
    VARCHAR_5000("VARCHAR(5010)", String::class),
    VARCHAR_10000("VARCHAR(10010)", String::class),
    TIMESTAMP("BIGINT", Long::class);// 精确至毫秒

}