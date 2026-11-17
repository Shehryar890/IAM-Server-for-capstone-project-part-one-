package com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.admin;

import com.example.IAM_SERVER.platformAdmin.adapters.in.Cli.PlatformAdminRegisterCommand;
import com.example.IAM_SERVER.platformAdmin.domain.exceptions.PlatformAdminAlreadyExistsException;


import com.example.IAM_SERVER.platformAdmin.domain.port.in.web.RegisterplatformUsecase;
import org.springframework.stereotype.Component;

import picocli.CommandLine.Command;

import java.io.Console;
import java.util.Arrays;
import java.util.concurrent.Callable;

@Component
@Command(
        name = "create",
        description = "Create the initial platform administrator"
)
public class CreatePlatformAdminCliCommand implements Callable<Integer> {

    private final RegisterplatformUsecase   registerPlatformAdminUseCase;

    public CreatePlatformAdminCliCommand(
            RegisterplatformUsecase  registerPlatformAdminUseCase
    ) {
        this.registerPlatformAdminUseCase= registerPlatformAdminUseCase;
    }

    @Override
    public Integer call() {

        Console console = System.console();

        if (console == null) {
            System.err.println(
                    "Console is unavailable. Run this command from PowerShell or another terminal."
            );
            return 1;
        }

        String username = console.readLine("Enter username: ");

        char[] password = null;
        char[] confirmPassword = null;

        try {

            // 1. Collect password without displaying it.
            password = console.readPassword("Enter password: ");

            confirmPassword = console.readPassword(
                    "Confirm password: "
            );

            // 2. Validate username.
            if (username == null || username.isBlank()) {
                System.err.println("Username cannot be empty.");
                return 1;
            }

            // 3. Validate password input.
            if (password == null || password.length == 0) {
                System.err.println("Password cannot be empty.");
                return 1;
            }

            if (confirmPassword == null) {
                System.err.println("Password confirmation is required.");
                return 1;
            }

            // 4. Check password confirmation.
            if (!Arrays.equals(password, confirmPassword)) {
                System.err.println("Passwords do not match.");
                return 1;
            }

            // 5. Create the application command.
          PlatformAdminRegisterCommand command =
                    new PlatformAdminRegisterCommand(
                            username.trim(),
                            new String(password)
                    );

            // 6. Execute the existing signup use case.
            registerPlatformAdminUseCase.register(command);

            console.printf(
                    "%nPlatform administrator created successfully.%n"
            );

            return 0;

        } catch (PlatformAdminAlreadyExistsException exception) {

            console.printf(
                    "%nPlatform administrator already exists. Creation rejected.%n"
            );

            return 1;

        } catch (RuntimeException exception) {

            console.printf(
                    "%nUnable to create platform administrator. Check the server configuration and logs.%n"
            );

            return 1;

        } finally {

            // Clear password character arrays from memory.
            if (password != null) {
                Arrays.fill(password, '\0');
            }

            if (confirmPassword != null) {
                Arrays.fill(confirmPassword, '\0');
            }
        }
    }
}