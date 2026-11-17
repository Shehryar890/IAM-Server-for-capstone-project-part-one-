
package com.example.IAM_SERVER;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

import picocli.CommandLine;

import java.util.Arrays;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IamServerApplication {

	public static void main(String[] args) {
		System.out.println("Arguments: " + Arrays.toString(args));
		// Check whether the application was launched through CLI
		if (isCliCommand(args)) {
			System.out.println("CLI BRANCH ENTERED");
			System.setProperty("spring.devtools.restart.enabled", "false");
			// Remove the "iamcli" prefix before passing arguments to Picocli
			String[] cliArgs = Arrays.copyOfRange(args, 1, args.length);

			int exitCode;

			try (ConfigurableApplicationContext context =
						 new SpringApplicationBuilder(IamServerApplication.class)
								 .web(WebApplicationType.NONE)
								 .run()) {

				CommandLine commandLine =
						context.getBean(CommandLine.class);

				exitCode = commandLine.execute(cliArgs);
			}

			System.exit(exitCode);
			return;
		}
		System.out.println("NORMAL SERVER BRANCH ENTERED");
		// Otherwise, start the normal Spring Boot server
		SpringApplication.run(IamServerApplication.class, args);
	}

	private static boolean isCliCommand(String[] args) {
		return args.length > 0 && "iamcli".equals(args[0]);
	}
}
