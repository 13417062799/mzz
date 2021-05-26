package com.distlab.hqsms.common.exception;

import com.distlab.hqsms.broadcast.plan.BroadcastPlanInfo;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class BroadcastPlanInfoGroupsSequenceProvider implements DefaultGroupSequenceProvider<BroadcastPlanInfo> {
  @Override
  public List<Class<?>> getValidationGroups(BroadcastPlanInfo broadcastPlanInfo) {
    List<Class<?>> defaultGroupSequence = new ArrayList<>();
    defaultGroupSequence.add( BroadcastPlanInfo.class ); //添加默认组

    if ( broadcastPlanInfo != null && broadcastPlanInfo.getType() != null ) {
      if (broadcastPlanInfo.getType() == 1) {
        defaultGroupSequence.add( BroadcastPlanInfo.Type1.class );
      }
      if (broadcastPlanInfo.getType() == 2) {
        defaultGroupSequence.add( BroadcastPlanInfo.Type2.class );
      }
      if (broadcastPlanInfo.getType() == 3) {
        defaultGroupSequence.add( BroadcastPlanInfo.Type3.class );
      }
    }

    return defaultGroupSequence;
  }
}
