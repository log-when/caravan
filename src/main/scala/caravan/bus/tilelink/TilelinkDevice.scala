package caravan.bus.tilelink
import caravan.bus.common.DeviceAdapter
import chisel3._
import chisel3.stage.ChiselStage
import chisel3.util.Decoupled

class TilelinkDevice(implicit val config: TilelinkConfig) extends DeviceAdapter with OpCodes {
    val io = IO(new Bundle {
        val tlSlaveTransmitter = Decoupled(new TilelinkSlave())
        val tlMasterReceiver = Flipped(Decoupled(new TilelinkMaster()))
        val reqOut = Decoupled(new TLRequest())
        val rspIn = Flipped(Decoupled(new TLResponse()))
    })

    io.tlMasterReceiver.ready := true.B
    io.rspIn.ready := true.B

    
    val stall = Module(new stallUnit)


    // Sending Response coming from Memory in the STALL to delay the response one cycle
    stall.io.bundle_in.d_opcode := Mux(io.tlMasterReceiver.bits.a_opcode === Get.U, AccessAckData.U, AccessAck.U)
    stall.io.bundle_in.d_data := io.rspIn.bits.dataResponse
    stall.io.bundle_in.d_param := 0.U
    stall.io.bundle_in.d_size := io.tlMasterReceiver.bits.a_size
    stall.io.bundle_in.d_source := io.tlMasterReceiver.bits.a_source
    stall.io.bundle_in.d_sink := 0.U
    stall.io.bundle_in.d_denied := false.B
    stall.io.bundle_in.d_corrupt := io.rspIn.bits.error
    stall.io.valid_in := true.B

    io.tlSlaveTransmitter.bits := stall.io.bundle_out
    io.tlSlaveTransmitter.valid := stall.io.valid_out

    io.reqOut.bits.addrRequest := io.tlMasterReceiver.bits.a_address
    io.reqOut.bits.dataRequest := io.tlMasterReceiver.bits.a_data
    io.reqOut.bits.activeByteLane := io.tlMasterReceiver.bits.a_mask
    io.reqOut.bits.isWrite := io.tlMasterReceiver.bits.a_opcode === PutFullData.U || io.tlMasterReceiver.bits.a_opcode === PutPartialData.U
    io.reqOut.valid := true.B

}