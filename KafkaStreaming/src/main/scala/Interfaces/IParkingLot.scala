package Interfaces

import DomainObjects.MessageObject._
import DomainObjects.ParkingLotObject.SLot

trait IParkingLot {

  // Construct a parking lot object
  def ConstructParkingLot () : Unit

  // report the parking
  def Report(mess: Messages): Unit

  // Add more slot to a parking lot object
  def AddSlot(newSlot : SLot) : Unit

  // handle Parked event
  def Parked (SLot: SLot) : Unit

  // handle Exit event
  def Exit (inputSLot: SLot): Unit

}
