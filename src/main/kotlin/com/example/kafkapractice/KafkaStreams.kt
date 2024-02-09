package com.example.kafkapractice

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import java.util.*

//const val APPLICATION_NAME = "streams-filter-application"
//const val STREAM_LOG = "stream_log"
//const val STREAM_LOG_FILTER = "stream_log_filter"

const val APPLICATION_NAME = "order-join-application"
const val ADDRESS_TABLE = "address"
const val ORDER_STREAM = "order"
const val ORDER_JOIN_STREAM = "order_join"

fun main() {

    val props = Properties()
    props.putAll(
        setOf(
            StreamsConfig.APPLICATION_ID_CONFIG to APPLICATION_NAME,
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS,
            StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to Serdes.String().javaClass,
            StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to Serdes.String().javaClass
        )
    )

    val builder = StreamsBuilder()
//    builder.stream<String, String>(STREAM_LOG)
//        .filter { _, value -> value.length > 5 }
//        .to(STREAM_LOG_FILTER)
    val addressTable = builder.table<String, String>(ADDRESS_TABLE)
//    val addressGlobalTable = builder.globalTable<String, String>(ADDRESS_GLOBAL_TABLE)
    val orderStream = builder.stream<String, String>(ORDER_STREAM)
    orderStream.join(addressTable) { order, address -> "$order send to $address" }
        .to(ORDER_JOIN_STREAM)

    val streams = KafkaStreams(builder.build(), props)
    streams.start()
}