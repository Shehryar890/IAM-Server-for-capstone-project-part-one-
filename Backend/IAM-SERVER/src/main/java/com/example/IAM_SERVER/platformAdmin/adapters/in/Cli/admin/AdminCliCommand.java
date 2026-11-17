package com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.admin;

import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

@Component
@Command(
        name = "admin",
        description = "Platform administrator commands",
        subcommands = {
                CreatePlatformAdminCliCommand.class
        }
)
public class AdminCliCommand {
}