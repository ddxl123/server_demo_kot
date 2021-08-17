package com.example.demo_kot.util.tablegenerator.annotation

import com.example.demo_kot.util.tablegenerator.type.DataType
import com.example.demo_kot.util.tablegenerator.type.StorageType

@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class OutColumnPYID(
        val dataType: DataType = DataType.BIGINT,
        val storageTypes: Array<StorageType> = [
//            StorageType.UNSIGNED,
            StorageType.PRIMARY_KEY,
//            StorageType.AUTO_INCREMENT,
            StorageType.NOT_NULL,
            StorageType.UNIQUE
        ])
