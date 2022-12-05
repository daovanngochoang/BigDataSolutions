package Utils

import Interfaces.IMessageProcessing
import Utils.DomainObject.Messages
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._


class MessageProcessing extends IMessageProcessing{
  override def RawMessagePreprocessing(rawMessage: String): String = {
    val result = rawMessage
      .replace("messageid", "message_id")
      .replace("mdsversion", "mds_version")
      .replace("@timestamp", "timestamp")
      .replace("analyticsModule", "analytics_module")
      .replace("object", "detected_object")
      .replace("type", "_type")

    return result
  }

  override def StringToObjectDeserializer(rawMessage: String): Messages = {

    // Preprocessing string message data
    val stringMessage = RawMessagePreprocessing(rawMessage)
    // decode data from text to object
    val decoded = decode[Messages](stringMessage)

    var messagesObject : Messages = null

    // get the object
    decoded.foreach((Object) => {
      messagesObject = Object
    })

    messagesObject
  }
}
