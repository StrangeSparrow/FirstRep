package com.mycorp.app;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Config {
    private static final Config Instance = new Config();
    private final static Logger logger = Logger.getLogger(Config.class);
    private final Map<String, String> loadedParams = new HashMap<>();

    private Config() {
    }

    public static Config getInstance() {
        return Instance;
    }

    public String getDBName() {
        return getParam(Parameters.DB_NAME.getParam());
    }

    public String getDBMigration() {
        return getParam(Parameters.DB_MIGRATION.getParam());
    }

    public String getSource() {
        return getParam(Parameters.APP_SOURCE.getParam());
    }

    public String getDBPass() {
        return getParam(Parameters.DB_PASS.getParam());
    }

    public String getDBURL() {
        return getParam(Parameters.DB_URL.getParam());
    }

    public String getNewsFileName() {
        return getParam(Parameters.NEWS_FILENAME.getParam());
    }

    public String getLocalNewsFileName() {
        return getParam(Parameters.LOCAL_NEWS_FILENAME.getParam());
    }

    public int getPageSize() {
        String result = getParam(Parameters.PAGE_SIZE.getParam());
        if (result == null || !result.matches("^\\d+$"))
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
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }

    public enum Parameters {
        PAGE_SIZE("news.page_size"),
        ONLY_HEADERS("news.list.only_headers"),
        NEWS_FILENAME("news.file_name"),
        LOCAL_NEWS_FILENAME("news.local_file_name"),
        DB_NAME("db.name"),
        DB_PASS("db.password"),
        DB_URL("db.url"),
        DB_MIGRATION("db.migration_prop"),
        APP_SOURCE("app.data_source");

        private final String param;

        Parameters(String param) {
            this.param = param;
        }

        String getParam() {
            return param;
        }
    }
}
