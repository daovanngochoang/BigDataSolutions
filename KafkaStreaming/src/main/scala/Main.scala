import Utils.{Config, DomainObject}
import Utils.DomainObject.{ParkingLot, ParkingSpot, SLot}


object Main extends App{

  val config = Config(boostrap_server = "192.168.10.235:9092", topic = "cs411_bigdata")
  val parkingLotObject = new ParkingLot ()
  parkingLotObject.id = "1"
  parkingLotObject.slots = new List[DomainObject.SLot] {new SLot(slot_id = "1", uptime = "0s", vehicle = null)}
  parkingLotObject

  val streaming = new StreamingProcesses(config)

  streaming.Start()

}
