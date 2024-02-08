@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.kafkapractice

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun main(args: Array<String>) {
    val configs: Properties = Properties()
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)

    val producer: KafkaProducer<String, String> = KafkaProducer(configs)
    val message = "testMessage"
//    val record: ProducerRecord<String, String> = ProducerRecord(TOPIC_NAME, message)
//    val record: ProducerRecord<String, String> = ProducerRecord(TOPIC_NAME, "hi", message)
    val record: ProducerRecord<String, String> = ProducerRecord(TOPIC_NAME, 0, "hi", message)

    val send = producer.send(record)

    println(record)
    println(send)
    producer.flush()
    producer.close()
}
