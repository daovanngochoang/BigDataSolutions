package Utils

import DomainObjects.MessageObject._
import DomainObjects.ParkingLotObject.SLot
import Interfaces.IParkingLot
import Utils.TimeHandler._
import org.joda.time.DateTime

import java.util.concurrent.TimeUnit


class ParkingLot (id : Int, n_slots : Int, input_location: Location) extends IParkingLot{


  // Props
  var location: Location = input_location
  private var  slots: List[SLot] = List()
  private var available: Int = 0
  private var total: Int = 0
  private var busy_slots: List[Int] = List()

  ConstructParkingLot()



  override def ConstructParkingLot(): Unit = {

    // auto add slot
    for (i <- 0 until(n_slots)){
      slots = slots :+ new SLot(slot_id = i+1,  time = "0s", vehicle = null)
    }

    // calculate available slots and total slot
    available = slots.count((slot) => {slot.vehicle == null})
    total = slots.length

  }


  override def Report(mess : Messages): Unit = {

    println(s"Report for the ParkingLot id = ${id}")
    println(s"Available: ${available}/${total}")
    println(s"   slot id   |   available   |                 vehicle                 |      Time      ")

    slots.foreach((slot) => {
        val now = DateTime.now().toString()
        var diff = "0 seconds"
        if (slot.vehicle != null){
          if (slot.time == "" || slot.time == "0s") {
            slot.time = DateTime.now().toString()
          }
          diff = TimeDistanceString(slot.time, mess.timestamp)
        }

        val vehicleName = if (slot.vehicle != null) slot.vehicle.model + slot.vehicle.model + slot.vehicle.color else null

        println(s"      ${slot.slot_id}           ${slot.vehicle == null}           ${vehicleName}         ${diff}   ")

    })
    println("\n\n")
  }


  override def AddSlot(newSlot : SLot): Unit = {
      slots = slots :+ newSlot
      total += 1
      available = slots.count((slot) => {slot.vehicle == null})
  }



  override def Parked(inputSLot: SLot): Unit = {
    slots.foreach((slot) => {
      if (slot.slot_id == inputSLot.slot_id){

        if (slot.vehicle == null){
          slot.vehicle = inputSLot.vehicle
          slot.time = inputSLot.time

          // update available
          available -= 1
        }else{
          slot.vehicle.id = inputSLot.vehicle.id
        }
        println(slot.vehicle)
      }
    })

  }

  override def Exit(inputSLot: SLot): Unit = {
    slots.foreach((slot) => {

      if (slot.vehicle!= null && slot.vehicle.id == inputSLot.vehicle.id) {

        // calculate if the time is valid
        val TimeDiff = TimeDistanceInMillis(old_time = slot.time, current_time = inputSLot.time )
        if (TimeUnit.MILLISECONDS.toSeconds(TimeDiff) > 5){
          slot.vehicle = null
          slot.time = ""
          // update available
          available -= 1

        }else {
          slot.vehicle.id = inputSLot.vehicle.id
        }

      }
    })
  }





}



