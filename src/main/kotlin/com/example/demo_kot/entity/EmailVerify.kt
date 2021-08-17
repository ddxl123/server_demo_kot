package com.example.demo_kot.entity

import com.example.demo_kot.util.tablegenerator.annotation.OutColumn
import com.example.demo_kot.util.tablegenerator.annotation.OutTable
import com.example.demo_kot.util.tablegenerator.type.DataType

@OutTable
data class EmailVerify(
    @OutColumn(dataType = DataType.CHAR_50) val email: String? = null,
    @OutColumn(dataType = DataType.INT) val code: Int? = null,
    override val id: Long? = null,
    override val createdAt: Long? = null,
    override val updatedAt: Long? = null,
) : BaseEntity(id, createdAt, updatedAt)
