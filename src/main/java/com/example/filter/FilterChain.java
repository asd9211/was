package com.example.filter;

import com.example.http.HttpRequest;
import com.example.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FilterChain {

    private static Logger log = LoggerFactory.getLogger(FilterChain.class);

    private List<Filter> filters;
    private boolean block = false;

    public FilterChain(List<Filter> filters) {
        this.filters = filters;
    }

    public void doFilter(HttpRequest request, HttpResponse response) {
        if (!isFilterEmpty()) {
            for (Filter filter : filters) {
                filter.doFilter(request, response);

                if (isFiltered(response)) {
                    log.info("this filter blocking : {}", filter.getClass().getName());
                    this.block = true;
                    break;
                }
            }
        }
    }

    private boolean isFiltered(HttpResponse response) {
        return response.isCommited();
    }

    private boolean isFilterEmpty() {
        return this.filters == null || this.filters.isEmpty();
    }

    public boolean isBlock() {
        return this.block;
    }

}
