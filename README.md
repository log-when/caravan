## Motivation
  We use the implementation of Wishbone Interface in this [design](https://github.com/merledu/caravan) to test our tool [CHA](https://github.com/iscas-tis/CHA.git) 

## Usage
  
  1. Add the assertion
  
   ```
    class WishboneHostProp(implicit config: WishboneConfig) extends WishboneHost()(config)
  {
    val cyc_o = io.wbMasterTransmitter.bits.cyc
    val stb_o = io.wbMasterTransmitter.bits.stb 
    val ack_i = io.wbSlaveReceiver.bits.ack
    val err_i = io.wbSlaveReceiver.bits.err
    //it is allowed to get ack in the same cycle with stb
    svaSeqAnno.makeSVAAnno(this.reset, ap(stb_o) |-> |- ap(stb_o) U ap(ack_i || err_i) -| || G ap(stb_o) )
  }
  ```

  2. Create a test class:
  ```
  class WishboneHostTest2 extends AnyFlatSpec with ChiselScalatestTester with Formal {

    implicit val config = WishboneConfig(10, 32)
    behavior of "WishboneHost"
    it should "pass" in {
      verify(new WishboneHostProp(), Seq(BoundedCheck(100), BtormcEngineAnnotation))
    }
  }
  ```
  
  3. Run a test

   ```
   >sbt
   testOnly wishbone.WishboneHostTest2
   ```
  4. Result
  ![image](https://user-images.githubusercontent.com/52351307/177136746-db4a8a9b-4ed1-4f3a-b0cf-c8386923c291.png)

  
