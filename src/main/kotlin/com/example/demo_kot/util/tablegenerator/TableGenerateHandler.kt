import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.core.type.classreading.CachingMetadataReaderFactory
import org.springframework.core.type.classreading.MetadataReader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.util.ClassUtils
import javax.sql.DataSource

class GenerateTableHandler(args: Array<String?>, applicationClass: Class<Any>, val packageName: String, var isDropAllIfExist: Boolean) {

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
            val tableNames: List<Map<String, Any>> = jdbcTemplate.queryForList("select table_name from information_schema.tables where table_schema='$dbName'")
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
        val resourcePattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(packageName) + "/**/*.class"
        // 获取全部资源
        val resources = resourcePatternResolver.getResources(resourcePattern)
        // 可读取每个资源文件中的类信息
        val metadataReaderFactory = CachingMetadataReaderFactory(resourcePatternResolver)

        // 对每个资源进行遍历，并获取每个资源的类信息
        resources.forEach { resource ->
            // 用于读取类信息
            val reader: MetadataReader = metadataReaderFactory.getMetadataReader(resource)
            // TODO: 扫描到的是每个资源的n个类还是单个类？
        }

    }

    private fun generateSql(tableName: String, fieldContent: StringBuilder): String {
        val tableSql: String = "CREATE TABLE $tableName($fieldContent);"
        println(tableSql)
        return tableSql
    }
}