package com.distlab.hqsms.common.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.*;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * snmp工具类
 *  支持snmp版本：2c
 */
public class SnmpUtil {

  private static final Logger logger = LoggerFactory.getLogger(SnmpUtil.class);

  public Snmp snmp = null;
  private String communityGet;
  private String addressGet;
  private String addressTrap;

  public void setCommunityGet(String communityGet) {
    this.communityGet = communityGet;
  }

  public void setAddressGet(String addressGet) {
    this.addressGet = addressGet;
  }

  public void setAddressTrap(String addressTrap) {
    this.addressTrap = addressTrap;
  }


  /**
   * 初始化Snmp Get
   *
   * @throws IOException  IO异常
   */
  public void initSnmpGet() throws IOException {
    // 1.初始化多线程消息转发类
    MessageDispatcher messageDispatcher = new MessageDispatcherImpl();
    // 添加处理模型，添加版本2c
    messageDispatcher.addMessageProcessingModel(new MPv2c());
    // 2.创建transportMapping
    TransportMapping<?> transport = new DefaultUdpTransportMapping();
    // 3.正式创建snmp
    snmp = new Snmp(messageDispatcher, transport);
    // 开启监听
    snmp.listen();
  }

  /**
   * 创建目标
   *
   * @param oid 目标ID
   * @return    目标
   */
  public Target createTarget(String oid) {
    CommunityTarget target = new CommunityTarget();
    // 指定版本
    target.setVersion(SnmpConstants.version2c);
    // 指定地址
    target.setAddress(new UdpAddress(addressGet));
    // 指定团体名
    target.setCommunity(new OctetString(communityGet));
    target.setRetries(3);
    target.setTimeout(2000);
    return target;
  }

  /**
   * 创建协议数据单元
   *
   * @param type  类型
   * @param oid   目标ID
   * @return      PDU
   */
  public PDU createPDU(int type, String oid) {
    PDU pdu = new PDU();
    pdu.setType(type);
    pdu.add(new VariableBinding(new OID(oid)));
    return pdu;
  }

  /**
   * 获取目标节点下所有子节点的属性
   *
   * @param oid         目标节点
   * @return            子节点属性列表
   * @throws Exception  异常
   */
  public List<VariableBinding> snmpWalk(String oid) throws Exception {
    // 1.初始化
    List<VariableBinding> list = new ArrayList<>();
    initSnmpGet();
    // 2.创建目标
    Target target = createTarget(oid);
    // 3.创建报文
    PDU pdu = createPDU(PDU.GETNEXT, oid);
    // 4.发送报文，并获取结果
    boolean matched = true;
    while (matched) {
      ResponseEvent event = snmp.send(pdu, target);
      if (event == null || event.getResponse() == null) {
        logger.error("timeout...");
        break;
      }
      PDU response = event.getResponse();
      String nextOid = null;
      Vector<? extends VariableBinding> vbs = response.getVariableBindings();
      for (int i = 0; i < vbs.size(); i++) {
        VariableBinding vb = vbs.elementAt(i);
        nextOid = vb.getOid().toDottedString();
        // 是否为目标节点的子节点
        if (!nextOid.startsWith(oid)) {
          matched = false;
          break;
        }
        list.add(vb);
      }
      if (!matched) {
        break;
      }
      pdu.clear();
      pdu.add(new VariableBinding(new OID(nextOid)));
    }
    return list;
  }

  /**
   * 初始化Snmp Trap
   *
   */
  public void initSnmpTrap() throws IOException{
      // 创建接收SnmpTrap的线程池
    ThreadPool threadPool = ThreadPool.create("Trap", 2);
      // 创建多线程消息分发器，以同时处理传入的消息
    MultiThreadedMessageDispatcher dispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());
      // 监听端的IP及端口号
    Address listenAddress = GenericAddress.parse(addressTrap);
      // 在指定的地址创建UDP传输
      TransportMapping<?> transport;
      if (listenAddress instanceof UdpAddress) {
        transport = new DefaultUdpTransportMapping((UdpAddress) listenAddress);
      } else {
        transport = new DefaultTcpTransportMapping((TcpAddress) listenAddress);
      }
      // 初始化Snmp
      snmp = new Snmp(dispatcher, transport);
      // 添加处理模型
      snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
      snmp.listen();
  }
}
