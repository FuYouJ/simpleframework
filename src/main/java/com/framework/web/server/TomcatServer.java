/*
package com.framework.web.server;

import com.framework.mvc.DispatcherServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.core.StandardWrapperFacade;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.jasper.servlet.JspServlet;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;





public class TomcatServer {
    private Tomcat tomcat;
    private String[] args;
    public TomcatServer(String[] args){
        this.args = args;
    }
    public void startServer(int port) throws LifecycleException, ServletException {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.start();
        //注册Servlet
        Context context = new StandardContext();
        context.setPath("/simpleframework");
        context.addLifecycleListener(new Tomcat.FixContextListener());
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        Tomcat.addServlet(context,"dispatcherServlet",dispatcherServlet)
                .setAsyncSupported(true);
        context.addServletMappingDecoded("/*","dispatcherServlet");
        tomcat.getHost().addChild(context);
        Thread thread = new Thread("awaitThread"){
            @Override
            public void run() {
                TomcatServer.this.tomcat.getServer().await();
            }
        };
        thread.setDaemon(false);
        thread.start();

        //注册一些必要的处理器
        DefaultServlet defaultServlet = new DefaultServlet();
        tomcat.addServlet(context,"default",defaultServlet);
//        ServletConfig servletConfig = new StandardWrapperFacade(new StandardWrapper());
        JspServlet jspServlet = new JspServlet();
//        jspServlet.init(servletConfig);
        tomcat.addServlet(context,"jsp",jspServlet);
    }
}
*/
