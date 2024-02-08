@file:Suppress("ktlint:standard:no-wildcard-imports")

package com.example.kafkapractice

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.Cluster
import org.apache.kafka.common.InvalidRecordException
import org.apache.kafka.common.serialization.StringSerializer
import org.apache.kafka.common.utils.Utils
import java.util.*

class CustomPartitioner : Partitioner {
    override fun configure(p0: MutableMap<String, *>?) {}

    override fun close() {}

    override fun partition(
        topic: String?,
        key: Any?,
        keyBytes: ByteArray?,
        value: Any?,
        valueBytes: ByteArray?,
        cluster: Cluster?,
    ): Int {
        keyBytes ?: throw InvalidRecordException("Need message key")
        if (key?.toString().equals("Pangyo")) {
            return 0
        }

        return Utils.murmur2(keyBytes) % (cluster?.partitionsForTopic(topic)?.size ?: 1)
    }
}

fun main(args: Array<String>) {
    val configs = Properties()
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
    configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
    configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
    configs.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner::class.java)
//    configs.put(ProducerConfig.ACKS_CONFIG, 0) // 설정해주면 @-1 이라는 오프셋으로 응답이 온다.

    val producer: KafkaProducer<String, String> = KafkaProducer(configs)
    val record: ProducerRecord<String, String> = ProducerRecord(TOPIC_NAME, 0, "Seoul", "Hi")

//    val send = producer.send(record) // 비동기로 동작 .get() -> 동기식
    val send = producer.send(record).get()
    println(record)
    println(send.toString())
    producer.flush()
    producer.close()
}
