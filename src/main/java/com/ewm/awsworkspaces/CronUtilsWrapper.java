package com.ewm.awsworkspaces;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CronUtilsWrapper {
    private static final Logger log = LoggerFactory.getLogger(CronUtilsWrapper.class);
    private static final CronDescriptor descriptor = CronDescriptor.instance();
    private static final CronParser parser;

    static {
        // Get a predefined instance
        CronDefinition cronDefinition =
                CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);

        // Create a parser based on provided definition
        parser = new CronParser(cronDefinition);
    }

    public static String describeCronExpression(String cronExpression) {
        try {
            return descriptor.describe(parser.parse(cronExpression));
        } catch (Exception e) {
            log.error("Error occurred during parse [{}]: {}", cronExpression, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
