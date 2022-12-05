package Interfaces

import Utils.DomainObject._

trait IParkingLot {

  def ConstructParkingLot () : Unit

  // report the parking
  def Report(): Unit

  def AddSlot() : Unit
}
