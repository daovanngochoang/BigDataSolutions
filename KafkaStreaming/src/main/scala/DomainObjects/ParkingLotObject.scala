package DomainObjects

object ParkingLotObject {

  case class SLot(
                   var slot_id: Int,
                   var time: String,
                   var vehicle: ParkedVehicle
                 )


  case class ParkedVehicle(

                    var id: String,
                    _type: String,
                    make: String,
                    model: String,
                    color: String,
                    licenseState: String,
                    license: String,
                    confidence: Double
                    )

}
