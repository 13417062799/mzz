package com.distlab.hqsms.common.modbus;

public class ModbusRTUCMD {
  private byte[] incompleteCMD;

  public byte[] getCMD() {
    CRC16Modbus crc = new CRC16Modbus();
    crc.update(incompleteCMD, 0 , incompleteCMD.length);
    byte[] crcBytes = crc.getCrcBytes();
    byte[] cmd = new byte[incompleteCMD.length + 2];
    System.arraycopy(incompleteCMD, 0, cmd, 0, incompleteCMD.length);
    cmd[incompleteCMD.length] = crcBytes[0];
    cmd[incompleteCMD.length + 1] = crcBytes[1];
    return cmd;
  }

  public byte[] getIncompleteCMD() {
    return incompleteCMD;
  }

  public void setIncompleteCMD(byte[] incompleteCMD) {
    this.incompleteCMD = incompleteCMD;
  }
}
