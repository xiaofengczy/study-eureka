package com.study.service;

import com.study.exception.HiError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FileName: FeignclientService Description:
 *
 * @author caozhongyu
 * @create 19-8-28
 */
@FeignClient(name = "eureka-client",fallback = HiError.class)
public interface EurekaFeignclientService {

  @GetMapping("/hi")
  String sayHello(@RequestParam("name") String name);
}