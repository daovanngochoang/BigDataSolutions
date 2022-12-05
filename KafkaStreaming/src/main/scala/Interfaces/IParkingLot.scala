package Interfaces

import DomainObjects.MessageObject._
import DomainObjects.ParkingLotObject.SLot

trait IParkingLot {

  def ConstructParkingLot () : Unit

  // report the parking
  def Report(mess: Messages): Unit

  def AddSlot(newSlot : SLot) : Unit

  def Parked (SLot: SLot) : Unit

  def Exit (inputSLot: SLot): Unit

}
