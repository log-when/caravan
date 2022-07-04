// updated dependency by log-when
package wishbone
import caravan.bus.wishbone.{DummyGpioController, WishboneConfig}
import org.scalatest.flatspec._
import org.scalatest.matchers.should._
import chiseltest._
import chiseltest.formal._
import chisel3._
import org.scalatest.freespec._

class GpioControllerTest extends AnyFreeSpec with ChiselScalatestTester {
  "write 40 in OUTPUT_EN_REG and read it back" in {
    implicit val config = WishboneConfig(10, 32)
    test(new DummyGpioController()).withAnnotations(Seq(VerilatorBackendAnnotation)) {c =>
      c.clock.step(5)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.addrRequest.poke("h40000000".U)
      c.io.req.bits.dataRequest.poke(40.U)
      c.io.req.bits.activeByteLane.poke("b1111".U)
      c.io.req.bits.isWrite.poke(true.B)
      c.clock.step(1)
      c.io.req.valid.poke(false.B)
      c.clock.step(1)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.isWrite.poke(false.B)
      c.clock.step(1)
      c.io.rsp.valid.expect(true.B)
      c.io.rsp.bits.dataResponse.expect(40.U)
    }
  }

  "write 40 in WDATA_REG and read it back" in {
    implicit val config = WishboneConfig(10, 32)
    test(new DummyGpioController()).withAnnotations(Seq(VerilatorBackendAnnotation)) {c =>
      c.clock.step(5)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.addrRequest.poke("h40000004".U)
      c.io.req.bits.dataRequest.poke(40.U)
      c.io.req.bits.activeByteLane.poke("b1111".U)
      c.io.req.bits.isWrite.poke(true.B)
      c.clock.step(1)
      c.io.req.valid.poke(false.B)
      c.clock.step(1)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.isWrite.poke(false.B)
      c.clock.step(1)
      c.io.rsp.valid.expect(true.B)
      c.io.rsp.bits.dataResponse.expect(40.U)
    }
  }

  "write 40 in RDATA_REG and read it back" in {
    implicit val config = WishboneConfig(10, 32)
    test(new DummyGpioController()).withAnnotations(Seq(VerilatorBackendAnnotation)) {c =>
      c.clock.step(5)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.addrRequest.poke("h40000008".U)
      c.io.req.bits.dataRequest.poke(40.U)
      c.io.req.bits.activeByteLane.poke("b1111".U)
      c.io.req.bits.isWrite.poke(true.B)
      c.clock.step(1)
      c.io.req.valid.poke(false.B)
      c.clock.step(1)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.isWrite.poke(false.B)
      c.clock.step(1)
      c.io.rsp.valid.expect(true.B)
      c.io.rsp.bits.dataResponse.expect(40.U)
    }
  }

  "write 40 in a register not available causing error" in {
    implicit val config = WishboneConfig(10, 32)
    test(new DummyGpioController()).withAnnotations(Seq(VerilatorBackendAnnotation)) {c =>
      c.clock.step(5)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.addrRequest.poke("h4000000c".U)
      c.io.req.bits.dataRequest.poke(40.U)
      c.io.req.bits.activeByteLane.poke("b1111".U)
      c.io.req.bits.isWrite.poke(true.B)
      c.clock.step(1)
      c.io.rsp.valid.expect(true.B)
      c.io.rsp.bits.error.expect(true.B)
      c.io.req.valid.poke(false.B)
      c.clock.step(1)
      c.io.req.valid.poke(true.B)
      c.io.req.bits.isWrite.poke(false.B)
      c.clock.step(1)
      c.io.rsp.valid.expect(true.B)
      c.io.rsp.bits.error.expect(true.B)
    }
  }
}