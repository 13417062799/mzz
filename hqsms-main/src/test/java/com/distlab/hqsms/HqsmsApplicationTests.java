package com.distlab.hqsms;

 import org.junit.runner.RunWith;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.test.context.junit4.SpringRunner;

// import com.distlab.hqsms.common.net.SnmpUtil;
// import com.distlab.hqsms.light.LightService;
// import com.rabbitmq.tools.json.JSONUtil;
// import com.sun.jmx.snmp.SnmpDataTypeEnums;
// import nonapi.io.github.classgraph.json.JSONUtils;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.snmp4j.PDU;
// import org.snmp4j.Target;
// import org.snmp4j.event.ResponseEvent;
// import org.snmp4j.smi.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
//
// import java.io.IOException;
// import java.io.OutputStream;
// import java.math.BigInteger;
// import java.net.Socket;
// import java.text.ParseException;
// import java.util.Arrays;


@SpringBootTest
@RunWith(SpringRunner.class)
public class HqsmsApplicationTests {
  private static final Logger logger = LoggerFactory.getLogger(HqsmsApplicationTests.class);


//  private final MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
//
//  @Autowired
//  OkHttpService okHttpService;
//
//  @Test
//  public void test() {
//    JSONObject body = new JSONObject();
//    body.put("ZDBH", 452);
//    body.put("CJBH", "0000000000696285");
////    body.put("KSRQ", "2020-12-12");
////    body.put("JSRQ", "2021-03-10");
////    body.put("SBLX", 1);
//    RequestBody requestBody = RequestBody.create(mediaType, body.toJSONString());
//    String url = "http://119.29.85.28:74/api/2/000000000/query_devicezt_if";
//    String bodyInString = okHttpService.postRequestAndGetBody(url, requestBody);
//    JSONObject reBody = JSON.parseObject(bodyInString);
//    JSONArray details = reBody.getJSONArray("SbztDetails");
//    Iterator<Object> iterator = details.stream().iterator();
//    while (iterator.hasNext()) {
//      String sDetail = iterator.next().toString();
//      ChargerState test = JSON.parseObject(sDetail, ChargerState.class);
//      Float outVoltage = test.getOutVoltage();
//      Float outCurrent = test.getOutCurrent();
//      Float totalEnergy = test.getTotalEnergy();
//      Integer workStatus = test.getWorkStatus();
//      Integer chargingTime = test.getChargingTime();
//      Integer residualTime = test.getResidualTime();
//      Integer soc = test.getSoc();
//      Float maxBatteryTemp = test.getMaxBatteryTemp();
//      Date remoteTime = test.getRemoteTime();
//      System.out.println("yes");
//    }
//  }

//  @Test
//  public void test() {
//    //Ruijie_Ac_75795b
//    String oid = "1.3.6.1.4.1.4881.1.1.10.2.5.1.2.5.1.2.2";
//
//    try {
//      SnmpUtil snmpUtil = new SnmpUtil();
//      snmpUtil.setAddressGet("192.168.1.20/161");
//      snmpUtil.setCommunityGet("Hg123_123");
//      snmpUtil.initSnmpGet();
//
////      PDU pdu = snmpUtil.createPDU(PDU.GET, oid);
////      Target target = snmpUtil.createTarget(oid);
////      ResponseEvent event = snmpUtil.snmp.send(pdu, target);
////      PDU response = event.getResponse();
////      System.out.println(response);
//
//      Variable variable = OctetString.fromByteArray(new byte[]{(byte) 0xc0, (byte) 0xa8, 0x01, (byte) 0xc8, 0x00, (byte) 0xa2});
//      PDU pdu = new PDU();
//      pdu.add(new VariableBinding(new OID(oid), variable));
//      pdu.setType(PDU.SET);
//      Target target = snmpUtil.createTarget(oid);
//      ResponseEvent send = snmpUtil.snmp.send(pdu, target);
//      PDU response = send.getResponse();
//      System.out.println(response);
//    } catch (IOException e) {
//      logger.error(e.getMessage());
//    }
//  }

}
