package com.distlab.hqsms.edge;

import com.distlab.hqsms.strategy.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceHierarchy<Server, Pole, Device> {
  private static final Logger logger = LoggerFactory.getLogger(DeviceHierarchy.class);

  private Server server;
  private Pole pole;
  private Device device;
  private Rule.RuleDeviceType deviceType;

  public DeviceHierarchy(Server server, Pole pole, Device device, Rule.RuleDeviceType deviceType) {
    this.server = server;
    this.pole = pole;
    this.device = device;
    this.deviceType = deviceType;
  }

  public Server getServer() {
    return server;
  }

  public void setServer(Server server) {
    this.server = server;
  }

  public Pole getPole() {
    return pole;
  }

  public void setPole(Pole pole) {
    this.pole = pole;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  public Rule.RuleDeviceType getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(Rule.RuleDeviceType deviceType) {
    this.deviceType = deviceType;
  }
}
