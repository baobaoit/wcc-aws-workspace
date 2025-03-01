package com.ewm.awsworkspaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.workspaces.WorkSpacesClient;
import software.amazon.awssdk.services.workspaces.model.StartRequest;
import software.amazon.awssdk.services.workspaces.model.StartWorkspacesRequest;
import software.amazon.awssdk.services.workspaces.model.StartWorkspacesResponse;
import software.amazon.awssdk.services.workspaces.model.StopRequest;
import software.amazon.awssdk.services.workspaces.model.StopWorkspacesRequest;
import software.amazon.awssdk.services.workspaces.model.StopWorkspacesResponse;
import software.amazon.awssdk.services.workspaces.model.WorkspaceState;

import java.time.Instant;

@Service
public class EwmWorkspacesService {
    private static final Logger log = LoggerFactory.getLogger(EwmWorkspacesService.class);

    @Autowired
    private WorkSpacesClient workSpacesClient;

    public void startWorkspace(String workspaceId) {
        log.info("Start workspace [{}]...", workspaceId);
        workSpacesClient.describeWorkspaces().workspaces().forEach(workspace -> {
            if (workspaceId.equalsIgnoreCase(workspace.workspaceId()) && WorkspaceState.STOPPED.equals(workspace.state())) {
                StartRequest startRequest = StartRequest.builder().workspaceId(workspaceId).build();
                StartWorkspacesResponse startWorkspacesResponse = workSpacesClient.startWorkspaces(StartWorkspacesRequest.builder().startWorkspaceRequests(startRequest).build());
                if (startWorkspacesResponse.hasFailedRequests())
                    log.error("Start workspace failed: {}", startWorkspacesResponse); // StartWorkspacesResponse[FailedRequests=[])
                else {
                    log.info("Start workspace done.");
                }

            }
        });
    }

    public void stopWorkspace(String workspaceId) {
        log.info("Stop workspace [{}]...", workspaceId);
        workSpacesClient.describeWorkspaces().workspaces().forEach(workspace -> {
            if (workspaceId.equalsIgnoreCase(workspace.workspaceId()) && WorkspaceState.STARTING.equals(workspace.state())) {
                StopRequest stopRequest = StopRequest.builder().workspaceId(workspaceId).build();
                StopWorkspacesResponse stopWorkspacesResponse = workSpacesClient.stopWorkspaces(StopWorkspacesRequest.builder().stopWorkspaceRequests(stopRequest).build());

                if (stopWorkspacesResponse.hasFailedRequests())
                    log.error("Stop workspace failed: {}", stopWorkspacesResponse); // StopWorkspacesResponse(FailedRequests=[]) if 200 OK
                else {
                    log.info("Stop workspace done.");
                }

            }
        });
    }

}
