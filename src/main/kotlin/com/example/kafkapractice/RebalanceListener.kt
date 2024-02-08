package com.example.kafkapractice

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener
import org.apache.kafka.common.TopicPartition

class RebalanceListener : ConsumerRebalanceListener {
    override fun onPartitionsAssigned(p0: MutableCollection<TopicPartition>?) {
        println("***Partitions are Assigned")
    }

    override fun onPartitionsRevoked(p0: MutableCollection<TopicPartition>?) {
        println("***Partitions are revoked ${p0.toString()}")
    }

}