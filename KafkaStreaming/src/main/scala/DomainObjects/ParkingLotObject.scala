package DomainObjects

object ParkingLotObject {

  case class SLot(
                   var slot_id: Int,
                   var time: String,
                   var vehicle: ParkingLotVehicle
                 )


  case class ParkingLotVehicle(

                    var id: String,
                    _type: String,
                    make: String,
                    model: String,
                    color: String,
                    licenseState: String,
                    license: String,
                    confidence: Double,
                    var last_action : String
                    )


  object StreamingEvent {
    val parked = "parked"
    val exit = "exit"
    val entry = "entry"
    val moving = "moving"
    val stopped = "stoped"
  }

}
