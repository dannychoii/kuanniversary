package kr.ac.korea.anniversary.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    fun datasource():DataSource{
    return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Bean
    fun jdbcTemplate(): JdbcTemplate{
        return JdbcTemplate(datasource())
    }
}