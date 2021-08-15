package com.example.demo_kot.entity

import com.example.demo_kot.util.tablegenerator.annotation.OutColumn
import com.example.demo_kot.util.tablegenerator.annotation.OutTable
import com.example.demo_kot.util.tablegenerator.type.DataType

@OutTable
data class EmailVerify(
    @OutColumn(dataType = DataType.CHAR_50) val email: String?,
    @OutColumn(dataType = DataType.INT) val code: Int?,
    override val id: Long?,
    override val createdAt: Long?,
    override val updatedAt: Long?,
) : BaseEntity(id, createdAt, updatedAt)
