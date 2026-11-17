package com.example.IAM_SERVER.platformAdmin.adapters.in.Cli;

import com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.admin.AdminCliCommand;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

@Component
@Command(
        name = "iam",
        description = "IAM Server command-line interface",
        subcommands = {
                AdminCliCommand.class
        }
)
public class IamCliCommand {
}