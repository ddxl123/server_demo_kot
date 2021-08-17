package com.example.demo_kot.entity

import com.example.demo_kot.util.tablegenerator.annotation.OutColumnPYID
import com.example.demo_kot.util.tablegenerator.annotation.OutColumnTimestamp

open class BaseEntity(
    @OutColumnPYID open val id: Long? = null,
    @OutColumnTimestamp open val createdAt: Long? = null,
    @OutColumnTimestamp open val updatedAt: Long? = null,
)