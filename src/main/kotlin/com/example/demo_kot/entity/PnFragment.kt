package com.example.demo_kot.entity

import com.example.demo_kot.util.tablegenerator.annotation.OutColumn
import com.example.demo_kot.util.tablegenerator.annotation.OutColumnAIID
import com.example.demo_kot.util.tablegenerator.annotation.OutTable
import com.example.demo_kot.util.tablegenerator.type.DataType
import java.math.BigInteger

@OutTable
data class PnFragment(
    @OutColumnAIID val userAiid: Long? = null,
    @OutColumnAIID val ruleAiid: Long? = null,
    @OutColumn(dataType = DataType.CHAR_20) val title: String? = null,
    @OutColumn(dataType = DataType.CHAR_50) val easyPosition: String? = null,
    override val id: Long? = null,
    override val createdAt: Long? = null,
    override val updatedAt: Long? = null
) : BaseEntity(id, createdAt, updatedAt)
