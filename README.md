## Motivation
  We use the implementation of Wishbone Interface in this [design](https://github.com/merledu/caravan) to test our tool [CHA](https://github.com/iscas-tis/CHA.git) 

## Usage
  write assertion for WishboneHost 
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

  ![image](https://user-images.githubusercontent.com/52351307/177136746-db4a8a9b-4ed1-4f3a-b0cf-c8386923c291.png)

  
