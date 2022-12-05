
import Interfaces.IStreaming
import Utils.Config
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.serialization._

import java.time.Duration
import java.util.Properties
import scala.jdk.CollectionConverters._
import Utils._
import _root_.Utils.DomainObject._





class StreamingProcesses(
                          config: Config,
                          parkingLotObject: ParkingLot,
                          messageProcessing: MessageProcessing
                        ) extends IStreaming{



  object StreamingEvent {
    val parked = "parked"
    val exit = "exit"
    val entry = "entry"
    val moving = "moving"
  }


  override def ConstructConfig(): Properties = {
    val consumerProps = new Properties()

    consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, config.boostrap_server)
    consumerProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    consumerProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer].getName)
    consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-id-1")
    consumerProps.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    consumerProps.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")

    return consumerProps
  }




  // start listen and consuming from object.
  override def Start(): Unit = {

    // initialize a kafka consumer
    val consumerProps = ConstructConfig()
    val consumer = new KafkaConsumer[Int, String](consumerProps)

    // subscribe to the topic
    consumer.subscribe(List(config.topic).asJava)

    // start waiting for incoming messages.
    while (true) {

      val pollRecords = consumer.poll(Duration.ofSeconds(0))

      if (!pollRecords.isEmpty) {

        println(s"Polled ${pollRecords.count()} records")

        val recordIterator = pollRecords.iterator()

        while (recordIterator.hasNext) {

          val record = recordIterator.next()
          var messOb = messageProcessing.StringToObjectDeserializer(record.value())
          ObjectAndSlotTracking(messOb)
          parkingLotObject.Report()

        }
      }
    }

  }

  override def ObjectAndSlotTracking(mess: Messages): Unit = {


    if (mess.event._type == StreamingEvent.parked){

    }

  }
}
