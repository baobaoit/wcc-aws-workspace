package com.ewm.awsworkspaces;

import java.util.HashMap;
import java.util.Map;

public class SettingFile {
    private final Map<String, String> triggerTimes;

    public SettingFile() {
        triggerTimes = new HashMap<>();
    }

    public Map<String, String> getTriggerTimes() {
        return triggerTimes;
    }
}
