package Interfaces

import DomainObjects.MessageObject.Messages
import Utils.ParkingLot

import java.util.Properties

trait IStreaming {

  // checking and tracking object changes
  def ObjectAndSlotTracking(mess: Messages): Unit

  // Construct the config for consumer
  def ConstructConfig () : Properties

  // Start the consuming process.
  def Start() : Unit


}
