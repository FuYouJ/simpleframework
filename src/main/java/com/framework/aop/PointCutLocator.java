package com.framework.aop;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/1 17:08
 */

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * 解析Aspect表达式 并且定位被织入的目标
 */
public class PointCutLocator {
    /**
     * point 解析器
     */
    //支持AspectJ的所有表达式
    private PointcutParser pointcutParser = PointcutParser
            .getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
                    PointcutParser.getAllSupportedPointcutPrimitives()
            );
    /**
     * 表达式解析器
     */
    private PointcutExpression pointcutExpression;

    /**
     * 解析表达式
     * @param expression
     */
    public PointCutLocator(String expression){
     pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**粗筛
     * @param targetClass
     * @return 是否匹配
     */
   public boolean roughMatches(Class<?> targetClass){
       //只能校验 withIn表达式 对于不能校验的 execution表达式，默认返回true  所以这里时粗筛
      return pointcutExpression.couldMatchJoinPointsInType(targetClass);
   }

    /**精确筛选
     *判断传入的 Method对象是否是Aspect的目标代理方法，即精确匹配PointCut
     * @param method
     * @return
     */
   public boolean accurateMatches(Method method){
       ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
       return shadowMatch.alwaysMatches();
   }
}