package com.example.demo_kot.util.tablegenerator.annotation

import com.example.demo_kot.util.tablegenerator.type.DataType
import com.example.demo_kot.util.tablegenerator.type.StorageType

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class OutColumnAIID(
        val dataType: DataType = DataType.BIGINT,
        val storageTypes: Array<StorageType> = [StorageType.UNSIGNED]
)
