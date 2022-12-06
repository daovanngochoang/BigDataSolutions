package Utils

import DomainObjects.MessageObject._
import DomainObjects.ParkingLotObject.{ParkingLotVehicle, SLot, StreamingEvent}
import Interfaces.IParkingLot
import Utils.TimeHandler._

import java.util.concurrent.TimeUnit
import scala.collection.mutable


class ParkingLot (id : Int, n_slots : Int, input_location: Location) extends IParkingLot{


  // ------------------------- Props -----------------------------
  var location: Location = input_location
  private var  slots: mutable.Map[Int, SLot] = mutable.Map()
  private var available: Int = 0
  private var total: Int = 0
  private var vehicles : mutable.Map[String, ParkingLotVehicle] = mutable.Map()


  // -----------------Construct the ParkingLot Object----------------
  // auto add slot
  for (i <- 0 until(n_slots)){
    slots += ((i+1) -> new SLot(slot_id = i+1,  time = "0s", vehicle = null))
  }

  // calculate available slots and total slot
  available = slots.count({case (k, slot) => {slot.vehicle != null} })
  total = slots.size



  //-----------------------Methods------------------------------------
  override def Report(mess : Messages): Unit = {

    println(s"Report for the ParkingLot id = ${id}")
    println(s"Available: ${available}/${total}")
    println("Busy slots: ")
    println(s"   slot id   |   available   |           vehicle          |      Time      ")


    slots.foreach({case (key, slot) =>{
        var diff = "0 seconds"

        // calculate the last time and current time distance to print out the report
        if (slot.vehicle != null) {
          diff = TimeDistanceString(slot.time, mess.timestamp)
          //print out the report
          val vehicleName = if (slot.vehicle != null) s"${slot.vehicle._type} ${slot.vehicle.make} ${slot.vehicle.color} ${slot.vehicle.licenseState}" else null
          println(s"      ${slot.slot_id}           ${slot.vehicle == null}           ${vehicleName}         ${diff}   ")

        }

    }})
    println("\n\n")

  }


  override def AddSlot(newSlot : SLot): Unit = {
      // in case we want more slots =>  add slot and update related fields
      slots = slots += (newSlot.slot_id ->  newSlot )
      total += 1
      available = slots.count({case (k, slot) => {slot.vehicle != null} })
  }



  override def Parked(inputSLot: SLot): Unit = {

    val id = inputSLot.slot_id

    // looking for the slot that has the parked event which has the same slot id with the one in the message
    // it would be more efficient using (key, value) map instead of using list.
    if (slots(id).vehicle == null) {

      // if there is no vehicle => add new vehicle to that slot
      slots(id).vehicle = inputSLot.vehicle
      slots(id).time = inputSLot.time

      // update available
      available += 1
    }else {

      // if there is a new vehicle => update ID
      slots(id).vehicle.id = inputSLot.vehicle.id
    }
    println(slots(id).vehicle)

  }

  override def Exit(inputSLot: SLot): Unit = {

    val id = inputSLot.slot_id

    // looking for the slot that is effected by this event which has the same id with the slot in the message
    if (slots(id).vehicle != null && slots(id).vehicle.id == inputSLot.vehicle.id) {

      // calculate if the time is valid because of outside factors such as human, cars ..
      // it's required more than 5s to exit a parking slot
      val TimeDiff = TimeDistanceInMillis(old_time = slots(id).time, current_time = inputSLot.time )
      if (TimeUnit.MILLISECONDS.toSeconds(TimeDiff) > 7){
        slots(id).vehicle = null
        slots(id).time = ""
        // update available
        available -= 1

      }else {
        // else just update the object id.
        slots(id).vehicle.id = inputSLot.vehicle.id
      }
      println(slots(id).vehicle)
    }
  }


  override def VehicleTracking(newVehicle : ParkingLotVehicle): Unit = {}

}



