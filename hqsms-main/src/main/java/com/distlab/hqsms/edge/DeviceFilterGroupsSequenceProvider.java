package com.distlab.hqsms.edge;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class DeviceFilterGroupsSequenceProvider implements DefaultGroupSequenceProvider<DeviceAndLogFilter> {
  @Override
  public List<Class<?>> getValidationGroups(DeviceAndLogFilter filter) {
    List<Class<?>> defaultGroupSequence = new ArrayList<>();
    defaultGroupSequence.add(DeviceAndLogFilter.class);

    if (filter != null) {
      if (filter.getInEnd() != null && filter.getEnd() != null) {
        defaultGroupSequence.add(DeviceAndLogFilter.Time.class);
      }
      if (filter.getEnd() != null) {
        defaultGroupSequence.add(DeviceAndLogFilter.CreatedAt.class);
        return defaultGroupSequence;
      }
      if (filter.getInEnd() != null) {
        defaultGroupSequence.add(DeviceAndLogFilter.Login.class);
        return defaultGroupSequence;
      }
    }

    return defaultGroupSequence;
  }
}
