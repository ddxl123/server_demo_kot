package com.example.demo_kot.util

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * [data] 类的成员不能用该类进行委托。
 *
 * [P] 属性值类型。
 */
class WithFieldName<P : Any> {
    /// 值类型为 P 的字段值
    private var fieldValue: P by Delegates.notNull<P>();

    operator fun getValue(c: Any, property: KProperty<*>): P {
        return fieldValue
    }

    operator fun setValue(c: Any, property: KProperty<*>, value: P) {
        this.fieldValue = value
        this.fieldValue.fieldName = property.name
    }

}

class GetSetFieldName {
    /// 字段名的 value
    private var fieldNameValue: String by Delegates.notNull<String>();

    operator fun getValue(i: Any, property: KProperty<*>): String {
        return fieldNameValue
    }

    operator fun setValue(i: Any, property: KProperty<*>, s: String) {
        this.fieldNameValue = s
    }

}

var Any.fieldName: String by GetSetFieldName()
