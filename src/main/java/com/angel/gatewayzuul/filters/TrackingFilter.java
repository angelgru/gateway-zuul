package com.angel.gatewayzuul.filters;

import com.netflix.zuul.ZuulFilter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackingFilter extends ZuulFilter{

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER=true;

    private final FilterUtils filterUtils;

    @Autowired
    public TrackingFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
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
        if(!isCorrelationIdPresent()) {
            LoggerFactory.getLogger(TrackingFilter.class).error("TrackingFilter called");
            filterUtils.setCorrelationId(generateCorrelationId());
        }

        return null;
    }

    private boolean isCorrelationIdPresent() {
        return filterUtils.getCorrelationId() != null;
    }

    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }
}
