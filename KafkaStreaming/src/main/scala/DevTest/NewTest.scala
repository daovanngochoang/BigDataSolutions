package DevTest
import com.github.nscala_time.time.Imports

import java.time.Duration
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, LocalDate}

import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object NewTest extends App {

  var time = DateTime.now().toString.replace("T", " ").replace("z", "")
  time = time.substring(0, time.length - 10)

  var time2 = DateTime.now().toString.replace("T", " ").replace("z", "")
  time2 = time2.substring(0, time2.length - 10)

  val dateString = "2022-11-30T06:59:45z".replace("T", " ").replace("z", "")

  val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  val d1 = sdf.parse("2022-12-04 22:25:38")
  val d2 = sdf.parse(time2)

  println(time)
  println(dateString)
  var result = (d2.getTime() - d1.getTime())
  result = TimeUnit.MILLISECONDS.toSeconds(result)
  println(result)




//  val diff  = Duration.between(d1, time_before)
//  println(time_before.compareTo(time) )
}
