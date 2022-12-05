package DomainObjects

import org.apache.kafka.connect.data.Timestamp
import org.joda.time.DateTime


//Define domain object for message.
object MessageObject {



  case class Messages(
           message_id: String,
           mds_version: String,
           timestamp: String,
           place: Place,
           sensor: Sensor,
           analytics_module: AnalyticsModule,
           detected_object: DetectedObject,
           event: Event
  )

  case class Place(
            id : String,
            name: String,
            _type: String,
            location: Location,
            )

  case class Location(
           lat: Float,
           lon: Float,
           alt: Float
           )


  case class Coordinate(
           x : Double,
           y : Double,
           z : Double
           )

  case class Sensor(
           id : String,
           _type: String,
           description: String,
           location: Location,
           coordinate: Coordinate
           )

  case class AnalyticsModule(
            id : String,
            description: String,
            source: String,
            version: String
            )

  case class DetectedObject(
           id : String,
           speed : Double,
           direction : Double,
           orientation : Double,
           vehicle: Vehicle,
           bbox: Bbox,
           location: Location,
           coordinate: Coordinate
                           )

  case class Vehicle(
          _type: String,
          make: String,
          model: String,
          color: String,
          licenseState: String,
          license: String,
          confidence: Double
          )

  case class Bbox(
                   topleftx : Int,
                   toplefty: Int,
                   bottomrightx: Int,
                   bottomrighty: Int
                 )

  case class Event(id : String, _type : String)
}



