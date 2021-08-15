package com.example.demo_kot.entity

import com.example.demo_kot.util.tablegenerator.annotation.OutColumn
import com.example.demo_kot.util.tablegenerator.annotation.OutColumnAIID
import com.example.demo_kot.util.tablegenerator.annotation.OutTable
import com.example.demo_kot.util.tablegenerator.type.DataType

@OutTable
data class FComplete(
    @OutColumnAIID val userAiid: Long?,
    @OutColumnAIID val nodeAiid: Long?,
    @OutColumnAIID val fragmentAiid: Long?,
    @OutColumnAIID val ruleAiid: Long?,
    @OutColumn(dataType = DataType.CHAR_20) val title: String?,
    override val id: Long?,
    override val createdAt: Long?,
    override val updatedAt: Long?
) : BaseEntity(id, createdAt, updatedAt)
