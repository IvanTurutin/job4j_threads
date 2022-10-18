package ru.job4j.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    
	ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );
	
	public void emailTo(User user) {
		pool.submit(new Runnable() {
			@Override
			public void run() {
				String subject = String.format(
						"Notification %s to email %s",
						user.username(), user.email()
				);
				String body = String.format(
						"Add a new event to %s", user.username()
				);
				send(subject, body, user.email());
			}
		});
		
	}
	
	public void close() {
		pool.shutdown();
	}
	
	public void send(String subject, String body, String email) {

	}
	
}
