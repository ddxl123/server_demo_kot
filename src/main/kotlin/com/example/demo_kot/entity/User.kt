package com.example.demo_kot.entity

import com.example.demo_kot.util.tablegenerator.annotation.OutColumn
import com.example.demo_kot.util.tablegenerator.annotation.OutTable
import com.example.demo_kot.util.tablegenerator.type.DataType

@OutTable
data class User(
    @OutColumn(dataType = DataType.CHAR_20)
    val username: String? = null,
    @OutColumn(dataType = DataType.CHAR_100)
    val password: String? = null,
    @OutColumn(dataType = DataType.CHAR_50)
    val email: String? = null,
    @OutColumn(dataType = DataType.TINYINT)
    val age: Int? = null,
    override val id: Long? = null,
    override val createdAt: Long? = null,
    override val updatedAt: Long? = null
) : BaseEntity(id, createdAt, updatedAt)
