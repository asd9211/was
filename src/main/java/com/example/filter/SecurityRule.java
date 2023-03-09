package com.example.filter;

import java.util.Arrays;
import java.util.List;

public class SecurityRule {

    private static final String[] FORBIDDEN_URL_LIST = {
            "..",
            ".exe"
    };

    private SecurityRule(){}

    public static List<String> getForbiddenUrlList(){
        return Arrays.asList(FORBIDDEN_URL_LIST);
    }

}
