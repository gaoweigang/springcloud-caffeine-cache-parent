package com.gwg.springcloud.controller;

import com.gwg.springcloud.common.Result;
import com.gwg.springcloud.remote.IHelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * 在SpringCloud Eureka中，Restful接口 作为服务的提供者
 */
@RestController
public class HelloController implements IHelloRemote{

    @Autowired
    DiscoveryClient discoveryClient;

    @Value("${msg:unknown}")
    private String msg;

    public @ResponseBody Result<String> printServiceProvider(@PathVariable("name") String name, @PathVariable("age") int age){
        long startTime = System.currentTimeMillis();
        System.out.println("调用服务开始 start ..........，开始时间："+startTime);

        //1.熔断超时测试
        try{
            Thread.sleep(5000);//睡眠10秒
        }catch (Exception e){
            e.printStackTrace();
        }
        //2测试熔断异常处理
        /*if("gaoweigang".equals(name)){
            throw new IllegalArgumentException();
        }*/
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        System.out.println("调用服务开始 start ..........，执行时间："+(System.currentTimeMillis() - startTime) / 1000 );

        return Result.success(serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>Say " + msg);
    }
}