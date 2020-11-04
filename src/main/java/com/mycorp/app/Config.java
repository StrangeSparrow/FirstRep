package com.mycorp.app;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
    private final Map<String, String> loadedParams = new HashMap<>();
    private static final Config Instance = new Config();
    private final static Logger logger = Logger.getLogger(Config.class);

    public enum Parameters {
        PAGE_SIZE("news.page_size"),
        ONLY_HEADERS("news.list.only_headers");

        private final String param;

        Parameters (String param) {
            this.param = param;
        }

        String getParam () { return param; }
    }

    private Config() {}

    public static Config getInstance() {
        return Instance;
    }

    public int getPageSize() {
        String result = getParam(Parameters.PAGE_SIZE.getParam());
        if (result == null || ! result.matches("^\\d+$"))
            return -1;

        return Integer.parseInt(result);
    }

    public boolean isOnlyHeaders() {
        String result = getParam(Parameters.ONLY_HEADERS.getParam());
        return result != null && result.equals("true");
    }

    private String getParam(String param) {
        if (loadedParams.containsKey(param))
            return loadedParams.get(param);

        String result = null;

        try (Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"))) {
            Pattern pattern = Pattern.compile("^" + param + ".\\S*");
            Matcher matcher;
            while (scanner.hasNext()) {
                matcher = pattern.matcher(scanner.nextLine());
                if (matcher.find()) {
                    result = matcher.group().replace(param + "=", "");
                    loadedParams.put(param, result);
                    return result;
                }
            }
        }
        return result;
    }
}
