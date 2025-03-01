package com.ewm.awsworkspaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import java.util.Map;

@Configuration
public class ScheduleConfig {
    private static final Logger log = LoggerFactory.getLogger(ScheduleConfig.class);

    @Autowired
    private WorkspaceTask workspaceTask;

    @Autowired
    private SettingFileHandler settingFileHandler;

    @Autowired
    private TaskScheduler taskScheduler;

    @PostConstruct
    public void activateScheduler() {
        Map<String, String> configure = settingFileHandler.getConfigure();
        if (!configure.isEmpty()) {
            configure.forEach((taskName, cronExpression) -> {
                String cronExpressionDescribed =
                        CronUtilsWrapper.describeCronExpression(cronExpression);
                Runnable task = workspaceTask.getTask(taskName);

                if (cronExpressionDescribed != null && task != null) {
                    log.info("Executing cron job [{}] for [{}]",
                            cronExpressionDescribed, taskName);
                    taskScheduler.schedule(task, new CronTrigger(cronExpression));
                }
            });
        }
    }
}
