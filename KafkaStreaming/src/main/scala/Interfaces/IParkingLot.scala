package Interfaces

import DomainObjects.MessageObject._
import DomainObjects.ParkingLotObject.{ParkingLotVehicle, SLot}

trait IParkingLot {

  // report the parking
  def Report(mess: Messages): Unit

  // Add more slot to a parking lot object
  def AddSlot(newSlot : SLot) : Unit

  // handle Parked event
  def Parked (SLot: SLot) : Unit

  // handle Exit event
  def Exit (inputSLot: SLot): Unit

  // tracking vehicle stage, action to make sure they have the prosper id in the detected model
  def VehicleTracking (newVehicle: ParkingLotVehicle) : Unit


}
