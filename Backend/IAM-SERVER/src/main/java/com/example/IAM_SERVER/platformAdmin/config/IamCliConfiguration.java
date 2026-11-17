package com.example.IAM_SERVER.platformAdmin.config;

import com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.IamCliCommand;
import com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.SpringCommandFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

@Configuration
public class IamCliConfiguration {

    @Bean
    public CommandLine commandLine(
            IamCliCommand iamCliCommand,
            SpringCommandFactory factory) {

        return new CommandLine(iamCliCommand, factory);
    }
}