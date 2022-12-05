package DevTest

import Utils.DomainObject
import org.joda.time.DateTime

class Report extends IReport{





  override def ParkingLotReport(PObject: DomainObject.ParkingLot): Unit = {

    println(s"Report for the ParkingLot id = ${PObject.id}")
    println(s"Available: ${PObject.available}/${PObject.total}")
    println("busy slots: ")
    for (slot <- PObject.busy_slots){
      println(s"slot id : ${slot.slot_id}, uptime: ${slot.slot_id}, vehicle: ${slot.vehicle}")
    }
  }



  override def ObjectAndSlotTracking(PObject: DomainObject.ParkingLot, mess: DomainObject.Messages): Unit = {

    val event = mess.event._type
    val now = DateTime.now()
    val detectedObject = mess.detected_object

//    tracking event
//    only care about "parked", "entry"

//    tracking object vs new detected object
//    get id


//    tracking slot
//    slot id.


  }
}
