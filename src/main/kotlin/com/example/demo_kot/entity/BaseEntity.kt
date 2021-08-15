package com.example.demo_kot.entity

import com.example.demo_kot.util.tablegenerator.annotation.OutColumnPYID
import com.example.demo_kot.util.tablegenerator.annotation.OutColumnTimestamp

open class BaseEntity(
    @property:OutColumnPYID open val id: Long?,
    @property:OutColumnTimestamp open val createdAt: Long?,
    @property:OutColumnTimestamp open val updatedAt: Long?,
)