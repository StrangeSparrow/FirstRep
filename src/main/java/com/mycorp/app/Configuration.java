package com.mycorp.app;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configuration {
    private static final Configuration instance = new Configuration();
    private final static Logger logger = Logger.getLogger(Configuration.class);

    private Configuration() {
        findParametersFromList();
    }

    public static Configuration getInstance() {
        return instance;
    }

    private List<String> loadFileParameters(){
        List<String> listParameters = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/application.properties"))) {
            while (reader.ready()) {
                listParameters.add(reader.readLine());
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return listParameters;
    }

    private void findParametersFromList() {
        List<String> listParameters = loadFileParameters();
        Pattern pattern;
        Matcher matcher;
        for (int i = 0; i < listParameters.size(); i++) {
            for (Parameters parameter : Parameters.values()) {
                pattern = Pattern.compile("^" + parameter.getParameter() + ".\\S*");
                matcher = pattern.matcher(listParameters.get(i));

                if (matcher.find()) {
                    String value = matcher.group().replace(parameter.getParameter() + "=", "");
                    parameter.setValue(value);
                    break;
                }
            }
        }
    }

    public enum Parameters {
        NEWS_PATH("news.path"),
        PAGE_SIZE("news.page_size"),
        ONLY_HEADERS("news.list.only_headers");

        private final String parameter;
        private String value;

        static { Configuration.getInstance(); }

        Parameters (String parameter) {
            this.parameter = parameter;
        }

        String getParameter() { return parameter; }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
