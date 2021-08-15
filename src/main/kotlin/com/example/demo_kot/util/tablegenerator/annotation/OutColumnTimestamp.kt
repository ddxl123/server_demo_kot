package com.example.demo_kot.util.tablegenerator.annotation

import com.example.demo_kot.util.tablegenerator.type.DataType
import com.example.demo_kot.util.tablegenerator.type.StorageType

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class OutColumnTimestamp(
    val dataType: DataType = DataType.TIMESTAMP,
    val storageTypes: Array<StorageType> = []
)
