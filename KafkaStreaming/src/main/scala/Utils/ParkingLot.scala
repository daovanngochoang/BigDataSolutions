package Utils

import Interfaces.IParkingLot
import Utils.DomainObject._


class ParkingLot (n_slots : Int, input_location: Location) extends IParkingLot{



  // Props
  var id: String = null
  var location: Location = input_location
  var slots: List[SLot] = null
  var available: Int = null
  var total: Int = null
  var busy_slots: List[Int] = null



  override def ConstructParkingLot(): Unit = {

    // auto add slot
    for (i <- 0 until(n_slots)){
      slots.appended(new SLot(slot_id = i,  uptime = "0s", vehicle = null))
    }

    available = slots.count((slot) => {slot.vehicle == null})
    total = slots.length
    slots.foreach((slot) => {
      if (slot.vehicle != null){
        busy_slots.appended(slot.slot_id)
      }
    })


  }


  override def Report(): Unit = {

  }

  override def AddSlot(): Unit = {}
}

