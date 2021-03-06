package com.angel.gatewayzuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResponseFilter extends ZuulFilter {
    private static final int FILTER_ORDER=1;
    private static final boolean SHOULD_FILTER=true;

    private final FilterUtils filterUtils;

    @Autowired
    public ResponseFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();

        context.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());

        return null;
    }
}
