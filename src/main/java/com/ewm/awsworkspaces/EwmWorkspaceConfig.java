package com.ewm.awsworkspaces;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.workspaces.WorkSpacesClient;

@Configuration
public class EwmWorkspaceConfig {

    @Bean
    public WorkSpacesClient initWorkspaceClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "access-key",
                "Secret key");
        return WorkSpacesClient.builder().credentialsProvider(StaticCredentialsProvider.create(awsCreds)).region(Region.EU_WEST_1).build();
    }
}
