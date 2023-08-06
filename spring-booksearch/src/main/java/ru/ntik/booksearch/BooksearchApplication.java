package ru.ntik.booksearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BooksearchApplication {

	static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(BooksearchApplication.class, args);
	}

	public static void exit() {
		/* setting some error exit code (1 - Wrong Function) for case when during shutdown
			app catches another exception (which is very unlikely but possible) */
		int exitCode = 1;
		try {
			exitCode = SpringApplication.exit(context, () -> 0);
		} catch (IllegalArgumentException e) {
			// this exception is thrown when spring finds out it's context is non-existent and stops
			// as this is desired behaviour, this exception should be ignored
			exitCode = 0;
		}
		System.exit(exitCode);
	}
}
