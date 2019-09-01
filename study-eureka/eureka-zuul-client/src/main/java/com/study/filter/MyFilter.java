package com.study.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * FileName: MyFilter Description:
 *
 * @author caozhongyu
 * @create 2019/8/31
 */
@Component
public class MyFilter extends ZuulFilter {

  private Logger logger = LoggerFactory.getLogger(MyFilter.class);

  @Override
  public String filterType() {
    return PRE_TYPE;
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return false;
  }

  @Override
  public Object run() throws ZuulException {
    RequestContext currentContext = RequestContext.getCurrentContext();
    HttpServletRequest request = currentContext.getRequest();
    String token = request.getParameter("token");
    if(Objects.isNull(token)){
      logger.warn("token不存在");
      currentContext.setSendZuulResponse(false);
      currentContext.setResponseStatusCode(401);
      try {
        currentContext.getResponse().getWriter().write("token is empty");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    logger.info("OK");
    return null;
  }
}