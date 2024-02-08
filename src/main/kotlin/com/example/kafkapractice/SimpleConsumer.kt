package com.example.kafkapractice

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

fun main() {

    val configs = Properties()
    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
    configs.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID)
    configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
    configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
    configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false)

    val consumer: KafkaConsumer<String, String> = KafkaConsumer(configs)
    consumer.subscribe(listOf((TOPIC_NAME)), RebalanceListener())

    /* 동기 커밋
    while (true) {
        val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
        for (record in records) {
            println(record.toString())
        }
    }

     */

    /* 레코드 단위 오프셋 Code
    while (true) {
        val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofSeconds(1))
        val currentOffset = HashMap<TopicPartition, OffsetAndMetadata>()
        for (record in records) {
            println(record)
            currentOffset.put(
                TopicPartition(record.topic(), record.partition()),
                OffsetAndMetadata(record.offset() + 1, null)
            )
            consumer.commitSync(currentOffset)
        }
    }
     */

    /* 비동기 커밋
    while (true) {
        val records = consumer.poll(Duration.ofSeconds(1))
        for (record in records) {
            println(record)
        }
        consumer.commitAsync()
    }
     */

    // assign 메서드를 이용해서 컨슈머, 파티션을 직접 할당할 수도 있음
//    consumer.assign(TopicPartition)


    while (true) {
        val records = consumer.poll(Duration.ofSeconds(1))
        for (record in records) {
            println(record)
        }

        consumer.commitAsync { offsets, e ->
            if (e != null) {
                println("error1")
                println(offsets.toString())
            }
        }
    }



}