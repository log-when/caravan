// updated dependency by log-when
package common
import chisel3._
import org.scalatest.flatspec._
import org.scalatest.matchers.should._
import chiseltest._
import chiseltest.formal._
import chisel3._
import org.scalatest.freespec._
trait MemoryDumpFileHelper { self: AnyFreeSpec with ChiselScalatestTester =>
  def getFile: Option[String] = {
    if (scalaTestContext.value.get.configMap.contains("memFile")) {
      Some(scalaTestContext.value.get.configMap("memFile").toString)
    } else {
      None
    }
  }
}