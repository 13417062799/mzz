package com.distlab.hqsms.common.exception;

import com.distlab.hqsms.screen.device.ScreenSetterInfo;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class ScreenSetterInfoGroupsSequenceProvider implements DefaultGroupSequenceProvider<ScreenSetterInfo> {

  @Override
  public List<Class<?>> getValidationGroups(ScreenSetterInfo setterInfo) {
    List<Class<?>> defaultGroupSequence = new ArrayList<>();
    defaultGroupSequence.add(ScreenSetterInfo.class);

    if (setterInfo != null) {
      if (setterInfo.getCommand() != null) {
        defaultGroupSequence.add(ScreenSetterInfo.P.class);
        return defaultGroupSequence;
      }
      if (setterInfo.getVolume() != null) {
        defaultGroupSequence.add(ScreenSetterInfo.V.class);
        return defaultGroupSequence;
      }
      if (setterInfo.getBrightness() != null) {
        defaultGroupSequence.add(ScreenSetterInfo.B.class);
        return defaultGroupSequence;
      }
      if (setterInfo.getColorTemp() != null) {
        defaultGroupSequence.add(ScreenSetterInfo.C.class);
        return defaultGroupSequence;
      }
      if (setterInfo.getWidth() != null || setterInfo.getHeight() != null) {
        defaultGroupSequence.add(ScreenSetterInfo.R.class);
        return defaultGroupSequence;
      }
    }
    defaultGroupSequence.add(ScreenSetterInfo.D.class);
    return defaultGroupSequence;
  }
}
