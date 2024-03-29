package com.study.exception;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * FileName: MyZuulFallbackProvider Description:
 *
 * @author caozhongyu
 * @create 2019/8/31
 */
@Component
public class MyZuulFallbackProvider implements FallbackProvider {

  @Override
  public String getRoute() {
    return "eureka-client";
  }

  @Override
  public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
    if (cause instanceof HystrixTimeoutException) {
      return response(HttpStatus.GATEWAY_TIMEOUT);
    } else {
      return response(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private ClientHttpResponse response(final HttpStatus status) {

    return new ClientHttpResponse() {
      @Override
      public HttpStatus getStatusCode() throws IOException {
        return HttpStatus.OK;
      }

      @Override
      public int getRawStatusCode() throws IOException {
        return 0;
      }

      @Override
      public String getStatusText() throws IOException {
        return "OK";
      }

      @Override
      public void close() {

      }

      @Override
      public InputStream getBody() throws IOException {
        return new ByteArrayInputStream("sorroy,have error".getBytes());
      }

      @Override
      public HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
      }
    };

  }
}