public class EmailNotification {
    
	ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    ); //используется в emailTo и close()
	
	public void emailTo(User user) {
		//должен через ExecutorService отправлять почту
		String subject = String.format(
			"subject = Notification %s to email %s", 
			user.getUsername(), user.getEmail()
		);
		String body = String.format(
			"body = Add a new event to %s", user.getUsername()
		);
		
	}
	
	public void close() {
		//должен закрыть pool
	}
	
	public void send(String subject, String body, String email) {
		//должен быть пустой
	}
	
}
