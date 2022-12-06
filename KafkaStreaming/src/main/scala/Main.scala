import DomainObjects.Config
import Utils.{MessageProcessing, ParkingLot}


object Main extends App{


  val config = Config(boostrap_server = "192.168.10.235:9092", topic = "cs411_bigdata")

  val parkingLotObject = new ParkingLot (id = 1 , n_slots = 4, input_location = null)

  val messageProcessing = new MessageProcessing()

  val streaming = new StreamingProcesses(config, parkingLotObject = parkingLotObject, messageProcessing = messageProcessing)

  streaming.Start()

}
