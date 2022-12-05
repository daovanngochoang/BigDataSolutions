package Interfaces

import Utils.DomainObject.Messages

trait IMessageProcessing {

  // preprocessing the raw string receive from kafka to match the field in the defined objects
  def RawMessagePreprocessing (rawMessage: String) : String

  // Convert the string object to the Object
  def StringToObjectDeserializer (stringMessage: String) : Messages

}
