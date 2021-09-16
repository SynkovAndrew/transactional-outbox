package com.cource.student.configuration

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.reactive.TransactionalOperator
import org.springframework.transaction.support.DefaultTransactionDefinition
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.ByteBuffer
import java.util.*

@Configuration
class R2dbcConfiguration(
    @Qualifier("connectionFactory")
    private val connectionFactory: ConnectionFactory
) : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory = connectionFactory

    override fun getCustomConverters(): MutableList<Any> =
        mutableListOf(ByteArrayToUuidConverter, UuidToByteArrayConverter)

    @Bean
    fun r2dbcEntityTemplate(databaseClient: DatabaseClient): R2dbcEntityTemplate {
        return R2dbcEntityTemplate(databaseClient)
    }
}


object ByteArrayToUuidConverter : Converter<ByteArray, UUID> {
    override fun convert(source: ByteArray): UUID = ByteBuffer.wrap(source).let { UUID(it.long, it.long) }
}

object UuidToByteArrayConverter : Converter<UUID, ByteArray> {
    private const val UUID_SIZE = 16

    override fun convert(source: UUID): ByteArray =
        ByteBuffer
            .wrap(ByteArray(UUID_SIZE))
            .let {
                it.putLong(source.mostSignificantBits)
                it.putLong(source.leastSignificantBits)
                it.array()
            }
}

