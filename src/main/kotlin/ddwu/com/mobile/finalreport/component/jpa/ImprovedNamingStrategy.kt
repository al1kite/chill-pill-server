package ddwu.com.mobile.finalreport.component.jpa

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import org.springframework.stereotype.Component
import java.util.Locale
import org.hibernate.boot.model.naming.PhysicalNamingStrategy

@Component
class ImprovedNamingStrategy : PhysicalNamingStrategy {

    override fun toPhysicalCatalogName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return name?.let {
            Identifier.toIdentifier(it.text)
        }
    }

    override fun toPhysicalSchemaName(name: Identifier?, context: JdbcEnvironment?): Identifier? {
        return name?.let {
            Identifier.toIdentifier(it.text)
        }
    }

    override fun toPhysicalTableName(name: Identifier, jdbcEnvironment: JdbcEnvironment): Identifier {
        return if (jdbcEnvironment.currentCatalog?.toString() == "finalreport") {
            Identifier.toIdentifier(camelToSnake(name).text.uppercase(Locale.getDefault()))
        } else Identifier.toIdentifier(name.toString())
    }

    override fun toPhysicalSequenceName(name: Identifier, jdbcEnvironment: JdbcEnvironment): Identifier? {
        return null
    }

    override fun toPhysicalColumnName(name: Identifier, jdbcEnvironment: JdbcEnvironment): Identifier {
        return camelToSnake(name)
    }

    private fun camelToSnake(name: Identifier): Identifier {
        val regex = "([a-z])([A-Z|0-9]+)"
        val replacement = "$1_$2"
        val convertName = name.text.replace(regex.toRegex(), replacement).lowercase(Locale.getDefault())
        return Identifier.toIdentifier(convertName)
    }
}


