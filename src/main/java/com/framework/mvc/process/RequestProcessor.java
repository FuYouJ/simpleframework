package com.framework.mvc.process;

/**
 * @Desc
 * @Author FuYouJ
 * @date 2020/6/3 22:09
 */
//请求处理器的鼻祖
public interface RequestProcessor {
    /**
     *
     * @param requestProcessorChain 以责任链的模式 处理请求
     * @return false终止
     * @throws Exception
     */
    boolean process(RequestProcessorChain requestProcessorChain) throws Exception;
}
