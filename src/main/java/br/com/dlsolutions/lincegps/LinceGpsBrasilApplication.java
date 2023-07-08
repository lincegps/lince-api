package br.com.dlsolutions.lincegps;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.context.request.RequestContextListener;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@SpringBootApplication
@EntityScan(basePackages = "br.com.dlsolutions.lincegps.domain")
@EnableConfigurationProperties
@Slf4j
public class LinceGpsBrasilApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(LinceGpsBrasilApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Instant now = Instant.now();
		log.info("Started on " + ZonedDateTime.ofInstant(now, ZoneId.systemDefault()).toOffsetDateTime());
		log.info("Default Timezone: " + ZoneId.systemDefault() + " OffsetDateTime:" + OffsetDateTime.now());
		log.info("UTC Time: " + ZonedDateTime.ofInstant(now, ZoneId.of("UTC")));
		log.info("São Paulo: " + ZonedDateTime.ofInstant(now, ZoneId.of("America/Sao_Paulo")));
		log.info("Java-Name:{}", System.getProperty("java.specification.name"));
		log.info("Java-Vendor:{}", System.getProperty("java.specification.vendor"));
		log.info("Java-Version:{}", System.getProperty("java.specification.version"));
		log.info("Java-Runtime-Version:{}", System.getProperty("java.runtime.version"));
		log.info("file.encoding:{}", System.getProperty("file.encoding"));

	}

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.failOnEmptyBeans(false);
		return builder;
	}

	@Bean
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}
