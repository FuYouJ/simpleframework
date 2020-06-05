package com.framework.aop;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/5/31 21:55
 */

import com.framework.aop.aspect.DefaultAspect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class AspectInfo {
  private int orderIndex;
  private DefaultAspect aspectObject;
  //复用AspectJ
  private PointCutLocator pointCutLocator;
  public AspectInfo() {

  }

  public AspectInfo(int orderIndex, DefaultAspect aspectObject) {
    this.orderIndex = orderIndex;
    this.aspectObject = aspectObject;
  }

  public AspectInfo(int orderIndex, DefaultAspect aspectObject, PointCutLocator pointCutLocator) {
    this.orderIndex = orderIndex;
    this.aspectObject = aspectObject;
    this.pointCutLocator = pointCutLocator;
  }
}