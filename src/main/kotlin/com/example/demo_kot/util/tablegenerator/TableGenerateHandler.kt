package com.example.demo_kot.util.tablegenerator

import com.example.demo_kot.util.Helper
import com.example.demo_kot.util.tablegenerator.annotation.*
import com.example.demo_kot.util.tablegenerator.type.DataType
import com.example.demo_kot.util.tablegenerator.type.StorageType
import com.example.demo_kot.util.tablegenerator.type.TypeWrap
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.core.type.classreading.CachingMetadataReaderFactory
import org.springframework.core.type.classreading.MetadataReader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.util.ClassUtils
import java.lang.reflect.Field
import javax.sql.DataSource
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaField

/**
 * 注解类集合
 */
object TableGenerateHelper {
    val ANNOTATION_CLASSES: MutableList<KClass<out Annotation>> = mutableListOf(
        OutColumn::class,
        OutColumnPYID::class,
        OutColumnAIID::class,
        OutColumnTimestamp::class
    )

    fun getDataType(annotation: Annotation): DataType {
        try {
            return (annotation as OutColumn).dataType
        } catch (e: Exception) {
        }
        try {
            return (annotation as OutColumnPYID).dataType
        } catch (e: Exception) {
        }
        try {
            return (annotation as OutColumnAIID).dataType
        } catch (e: Exception) {
        }
        try {
            return (annotation as OutColumnTimestamp).dataType
        } catch (e: Exception) {
        }
        throw Exception("注解类型没有写在 TableGenerateHelper 中！")
    }

    fun getStorageType(annotation: Any): Array<StorageType> {
        try {
            return (annotation as OutColumn).storageTypes
        } catch (e: Exception) {
        }
        try {
            return (annotation as OutColumnPYID).storageTypes
        } catch (e: Exception) {
        }
        try {
            return (annotation as OutColumnAIID).storageTypes
        } catch (e: Exception) {
        }
        try {
            return (annotation as OutColumnTimestamp).storageTypes
        } catch (e: Exception) {
        }
        throw Exception("注解类型没有写在 TableGenerateHelper 中！")
    }
}

class TableGenerateHandler(
    args: Array<String>,
    applicationClass: Class<*>,
    val packageName: String,
    var isDropAllIfExist: Boolean
) {

    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var context: ConfigurableApplicationContext


    init {
        val springApplication = SpringApplication(applicationClass)
        springApplication.setBannerMode(Banner.Mode.OFF)
        context = springApplication.run(*args)
        jdbcTemplate = JdbcTemplate(context.getBean(DataSource::class.java))
    }


    fun run() {
        println("====================================================================================")
        println("====================================================================================")
        println("====================================================================================")

        if (isDropAllIfExist) {
            println("正在删除全部表...")
            val dbUrl = jdbcTemplate.dataSource!!.connection.metaData.url
            val dbName = dbUrl.split("/")[3]
            val tableNames: List<Map<String, Any>> =
                jdbcTemplate.queryForList("select table_name from information_schema.tables where table_schema='$dbName'")
            for (tableName in tableNames) {
                jdbcTemplate.execute("DROP TABLE IF EXISTS ${tableName["TABLE_NAME"]};")
                println("${tableName["TABLE_NAME"]} 已被删除 。")
            }
            println("删除完成。")
        }

        println("正在创建表...")

        // spring工具类，可以获取指定路径下的全部类
        val resourcePatternResolver = PathMatchingResourcePatternResolver()
        // 需要匹配的资源路径
        val resourcePattern =
            ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(
                packageName
            ) + "/**/*.class"
        // 获取全部类。单文件有多个类会全部被识别出来。所有类会按照首字母顺序排序。
        val resources = resourcePatternResolver.getResources(resourcePattern)
        // 可读取每个类的信息
        val metadataReaderFactory = CachingMetadataReaderFactory(resourcePatternResolver)

        // 对每个资源进行遍历，并获取每个类的信息
        resources.forEach { resource ->
            // 用于读取类信息
            val reader: MetadataReader = metadataReaderFactory.getMetadataReader(resource)
            // 扫描到的 class
            val classname = reader.classMetadata.className;
            // 将扫描出的类转化为实体类
            val kClass = Class.forName(classname).kotlin

            // 根据注解筛选出需要写入的类
            if (kClass.findAnnotation<OutTable>() != null) {
                // 类名 —— 表名
                val tableName: String = Helper.toUnderLineCase(kClass.simpleName!!)

                // 字段名及属性 —— column 属性
                var columnSql: String = ""

                // 获取全部可识别的属性，包含当前类、直接父类、间接父类
                val noSortAllProperties = mutableListOf<KProperty1<out Any, *>>()
                kClass.allSuperclasses.forEach {
                    noSortAllProperties.addAll(it.declaredMemberProperties)
                }
                noSortAllProperties.addAll(kClass.declaredMemberProperties)

                // 进行排序
                val allProperties = mutableListOf<KProperty1<out Any, *>>()
                val idPro = mutableListOf<KProperty1<out Any, *>>();
                val xxIdPro = mutableListOf<KProperty1<out Any, *>>();
                val timeAtPro = mutableListOf<KProperty1<out Any, *>>()
                val otherPro = mutableListOf<KProperty1<out Any, *>>()
                noSortAllProperties.forEach { p ->
                    if (p.name == "id") {
                        idPro.add(p)
                    } else if (p.name.endsWith("id")) {
                        xxIdPro.add(p)
                    } else if (p.name == "createdAt" || p.name == "updatedAt") {
                        timeAtPro.add(p)
                    } else {
                        otherPro.add(p)
                    }
                }
                allProperties.addAll(idPro)
                allProperties.addAll(xxIdPro)
                allProperties.addAll(otherPro)
                allProperties.addAll(timeAtPro)

                // 遍历每个属性
                allProperties.forEach { pro ->
                    // 遍历属性的每个注解
                    pro.annotations.forEach { proAn ->
                        // 获取有效注解
                        if (TableGenerateHelper.ANNOTATION_CLASSES.contains(proAn.annotationClass)) {
                            // 被注解的字段名
                            val fieldName: String = pro.javaField!!.name
                            columnSql += "${Helper.toUnderLineCase(fieldName)} "

                            // 提取 dataType 和 storageType 并进行包装
                            val typeWrap = TypeWrap()
                            typeWrap.dataType = TableGenerateHelper.getDataType(proAn)
                            typeWrap.storageTypes = TableGenerateHelper.getStorageType(proAn)

                            checkType(pro.javaField!!, typeWrap)

                            columnSql += "${typeWrap.dataType.dataTypeName} "
                            typeWrap.storageTypes.forEach { it ->
                                columnSql += "${it.storageTypeName} "
                            }
                            columnSql += ", \n"
                        }
                    }
                }
                columnSql = columnSql.removeSuffix(", \n")

                val singleTableSql = "CREATE TABLE $tableName (\n${columnSql}\n);"
                println(singleTableSql)
                jdbcTemplate.execute(singleTableSql)
                println(singleTableSql)
            }
        }

        // 关闭 context。
        context.close()
    }

    /**
     * 检查 [数据库字段类型] 是否与 [java 字段类型] 对应。
     */
    private fun checkType(field: Field, typeWrap: TypeWrap) {
        val enumName: String = typeWrap.dataType.name
        val dataTypeName: String = typeWrap.dataType.dataTypeName

        val javaType: String = typeWrap.dataType.kClass.simpleName!!
        var fieldType = field.type.simpleName
        fieldType = if (fieldType == "long") "Long" else fieldType
        fieldType = if (fieldType == "Integer") "Int" else fieldType

        if (fieldType != javaType) {
            throw Throwable(
                """${"\n"}
                > [数据库字段类型] 与 [java 字段类型] 不一致!
                    应该为: [kotlin 字段类型: $javaType], [数据库字段类型名: $dataTypeName], [枚举名: $enumName] 
                    而当前: [kotlin 字段类型: $fieldType]
                    实体类: ${field.declaringClass.typeName}
                    字段名: ${field.name}
                """.trimIndent()
            )
        }
    }
}