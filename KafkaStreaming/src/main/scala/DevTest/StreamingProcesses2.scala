package DevTest


import Interfaces.IStreaming
import Utils.Config
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization._

import java.time.Duration
import java.util.Properties
import scala.jdk.CollectionConverters._

class StreamingProcesses2(config: Config){








  def ConstructConfig(): Properties = {
    val consumerProps = new Properties()

    consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.boostrap_server)
    consumerProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[IntegerDeserializer].getName)
    consumerProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-id-1")
    consumerProps.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    consumerProps.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")

    return consumerProps
  }




  // start listen and consuming from object.
  def StartStreaming(): Unit = {

    // initialize a kafka consumer
    val consumerProps = ConstructConfig()
    val consumer = new KafkaConsumer[Int, String](consumerProps)


    consumer.subscribe(List(config.topic).asJava)
    println("         KEY        |         Message         |         Partition         |       Offset       | ")
    while (true) {
      val pollRecords = consumer.poll(Duration.ofSeconds(1))
      if (!pollRecords.isEmpty) {
        println(s"Polled ${pollRecords.count()} records")
        val recordIterator = pollRecords.iterator()

        while (recordIterator.hasNext) {
          val record = recordIterator.next()
          println(s"    ${record.key()}    |    ${record.value()}    |    ${record.partition()}    |    ${record.offset()}")
        }
      }
    }

  }



}
