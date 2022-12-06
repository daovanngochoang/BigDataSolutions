
import Interfaces.{IMessageProcessing, IParkingLot, IStreaming}
import DomainObjects.Config
import DomainObjects.ParkingLotObject.{ParkingLotVehicle, SLot, StreamingEvent}
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization._

import java.time.Duration
import java.util.Properties
import scala.jdk.CollectionConverters._
import DomainObjects._
import DomainObjects.MessageObject._
import Utils.{MessageProcessing, ParkingLot}




class StreamingProcesses(
                          config: Config,
                          parkingLotObject: IParkingLot,
                          messageProcessing: IMessageProcessing
                        ) extends IStreaming{




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

        val recordIterator = pollRecords.iterator()

        while (recordIterator.hasNext) {
          // get record from streaming info
          val record = recordIterator.next()

          // preprocessing and deserialize
          var messOb = messageProcessing.StringToObjectDeserializer(record.value())
          println(messOb.detected_object)

          println("Event: " + messOb.event._type)

          // tracking object, event and slot
          ObjectAndSlotTracking(messOb)

          // report real-time parking lot info
          parkingLotObject.Report(messOb)

        }
      }
    }

  }

  override def ObjectAndSlotTracking(mess: Messages): Unit = {

    // get Objects from message
    val detectedObject = mess.detected_object
    var parked_slot = detectedObject.coordinate.z.asInstanceOf[Int]
    var vehicle = detectedObject.vehicle

    // create a vehicle object in the parked slot
    var parkedVehicle = new ParkingLotVehicle(
      id = detectedObject.id,
      _type = vehicle._type,
      make = vehicle.make,
      model = vehicle.model,
      color = vehicle.color,
      licenseState = vehicle.licenseState,
      license = vehicle.license,
      confidence = vehicle.confidence,
      last_action = mess.event._type
    )

    // create a slot for
    val newSlot = new SLot(
      slot_id = parked_slot,
      time = mess.timestamp,
      vehicle = parkedVehicle
    )

    //
    if (mess.event._type == StreamingEvent.parked){

      // update slot stage
      parkingLotObject.Parked(newSlot)

    }
    else if (mess.event._type == StreamingEvent.exit){

      // Update
      parkingLotObject.Exit(newSlot)

    }else {
    }

  }
}
