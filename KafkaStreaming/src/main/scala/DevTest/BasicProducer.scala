package DevTest


import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization._
import org.apache.kafka.common.utils.Utils.sleep

import java.util.Properties
import scala.io.Source


object BasicProducer extends App{

  val TOPIC = "test_topic"


  private val producerProps = new Properties()
  producerProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" )
  producerProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[IntegerSerializer].getName)
  producerProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getName)

  private val producer = new KafkaProducer[Int, String](producerProps)



  private val path = "src/main/resources/kafka_streaming_msg.txt"

  for (line <- Source.fromFile(path).getLines()) {
    if (line.nonEmpty) {
      sleep(2000)
      producer.send(new ProducerRecord[Int, String](TOPIC, s"${line}"))

    }
  }


  producer.flush()


}
