package com.example.IAM_SERVER.platformAdmin.adapters.in.Cli;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class SpringCommandFactory implements CommandLine.IFactory {

    private final ApplicationContext applicationContext;

    public SpringCommandFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <K> K create(Class<K> commandClass) {
        return applicationContext.getBean(commandClass);
    }
}