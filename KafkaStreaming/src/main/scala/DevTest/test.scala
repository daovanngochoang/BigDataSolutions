package DevTest

object test  extends  App {


  import io.circe.generic.auto._
  import io.circe.parser._
  import io.circe.syntax._


  case class Vehicle(
                      vehicle_type: String,
                      make: String,
                      model: String,
                      color: String,
                      licenseState: String,
                      license: String,
                      confidence: Double
                    )

  case class DetectedObject(
                         id: String,
                         speed: String,
                         direction: String,
                         orientation: String,
                         vehicle: Vehicle
                           )


  val detectedObject = new DetectedObject(
    id = "1",
    speed = "60km",
    direction = "0",
    orientation = "0",
    vehicle = new Vehicle(
      vehicle_type = "Mercedes",
      make = "China",
      model = "S450",
      color = "RED",
      licenseState = "NA",
      license = "NA",
      confidence = 0
    )
  )



  val json = detectedObject.asJson.noSpaces
  json.foreach((i) => {
    println(i)
  })

  val decodedObject = decode[DetectedObject](json)
  println(decodedObject)
  decodedObject.foreach((TargetObject ) => {
    TargetObject.vehicle.model
  })
}
