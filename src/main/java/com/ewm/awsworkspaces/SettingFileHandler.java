package com.ewm.awsworkspaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Component
public class SettingFileHandler {
    private static final Logger log = LoggerFactory.getLogger(SettingFileHandler.class);
    private static final String SETTING_FILE_NAME = "wcc-aws-workspaces-settings";
    private static final String SETTING_FILE_EXTENSION = "yaml";

    private final SettingFile settingFile;

    public SettingFileHandler() {
        settingFile = readSettingFile();
    }

    private SettingFile readSettingFile() {
        File file = new File(String.format("%s.%s", SETTING_FILE_NAME, SETTING_FILE_EXTENSION));
        ObjectMapper objectMapper = new YAMLMapper();
        try {
            return objectMapper.readValue(file, SettingFile.class);
        } catch (IOException e) {
            log.error("Error occurred during read setting file: {}", e.getMessage());
            e.printStackTrace();
        }
        return new SettingFile();
    }

    public Map<String, String> getConfigure() {
        return settingFile.getTriggerTimes();
    }
}
