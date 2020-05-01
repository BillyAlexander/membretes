package ec.com.wolfdev.lembretes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ec.com.wolfdev.lembretes.application.LembretesConfiguration;
import ec.com.wolfdev.lembretes.application.context.LembretesBaseJpa;
import ec.com.wolfdev.lembretes.core.base.context.WolfDevBase;
import ec.com.wolfdev.lembretes.utils.Const;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({ Const.PACKAGE_NAMING })
@EnableJpaRepositories({ Const.PACKAGE_NAMING })
public class LembretesApplication extends LembretesConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(LembretesApplication.class, args);
	}

	@Bean
	public WolfDevBase wolfDevBase() {
		return new WolfDevBase(new LembretesBaseJpa());
	}
}
