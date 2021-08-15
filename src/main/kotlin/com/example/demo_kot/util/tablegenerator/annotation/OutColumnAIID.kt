package com.example.demo_kot.util.tablegenerator.annotation

import com.example.demo_kot.util.tablegenerator.type.DataType
import com.example.demo_kot.util.tablegenerator.type.StorageType
import java.lang.annotation.Inherited

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class OutColumnAIID(
        val dataType: DataType = DataType.BIGINT,
        val storageTypes: Array<StorageType> = [StorageType.UNSIGNED]
)
