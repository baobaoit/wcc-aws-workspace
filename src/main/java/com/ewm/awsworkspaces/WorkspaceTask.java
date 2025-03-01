package com.ewm.awsworkspaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceTask {
    private static final Logger log = LoggerFactory.getLogger(WorkspaceTask.class);
    private static final String START_WORKSPACE = "start-workspace";
    private static final String STOP_WORKSPACE = "stop-workspace";

    @Value("${ewm.workspace.id}")
    private String ewmWorkspaceId;

    @Autowired
    private EwmWorkspacesService ewmWorkspacesService;

    public Runnable getTask(String taskName) {
        Runnable ret;
        switch (taskName.toLowerCase()) {
            case START_WORKSPACE:
                ret = () -> ewmWorkspacesService.startWorkspace(ewmWorkspaceId);
                break;
            case STOP_WORKSPACE:
                ret = () -> ewmWorkspacesService.stopWorkspace(ewmWorkspaceId);
                break;
            default:
                log.warn("The task [{}] is not defined!", taskName);
                ret = null;
                break;
        }
        return ret;
    }
}
