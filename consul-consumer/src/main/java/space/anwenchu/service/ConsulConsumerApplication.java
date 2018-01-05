package space.anwenchu.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class ConsulConsumerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConsulConsumerApplication.class).web(true).run(args);
	}

}
