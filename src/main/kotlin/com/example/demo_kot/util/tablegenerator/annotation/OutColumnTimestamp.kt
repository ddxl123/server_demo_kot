package com.example.demo_kot.util.tablegenerator.annotation

import com.example.demo_kot.util.tablegenerator.type.DataType
import com.example.demo_kot.util.tablegenerator.type.StorageType

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class OutColumnTimestamp(
        val dataType: DataType = DataType.DATETIME,
        val storageTypes: Array<StorageType> = []
)
