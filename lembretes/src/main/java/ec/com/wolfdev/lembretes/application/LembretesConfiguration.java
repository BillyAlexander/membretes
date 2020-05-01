package ec.com.wolfdev.lembretes.application;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import ec.com.wolfdev.lembretes.core.security.config.AppProperties;
import ec.com.wolfdev.lembretes.utils.Const;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class LembretesConfiguration extends SpringBootServletInitializer {
	@Value("${spring.jpa.generate-ddl}")
	private Boolean generateDll;

	@Value("${spring.jpa.show-sql}")
	private Boolean showSql;
	
	@Bean
	public JavaMailSender javaMailService() throws NamingException {

	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("example@gmail.com");
	    mailSender.setPassword("example");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    
	    return mailSender;
	}
	
	@Bean
	public DataSource dataBaseDataSource() throws NamingException {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/postgres");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("password");
        return dataSourceBuilder.build();
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() throws NamingException {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(this.generateDll);
		vendorAdapter.setShowSql(this.showSql);
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(Const.PACKAGE_NAMING);
		factory.setDataSource(this.dataBaseDataSource());
		Properties properties = new Properties();
		properties.setProperty("hibernate.jdbc.batch_size", "100");
		properties.setProperty("hibernate.order_inserts", "true");
		properties.setProperty("hibernate.order_updates", "true");
		properties.setProperty("hibernate.event.merge.entity_copy_observer", "allow");
		factory.setJpaProperties(properties);
		factory.afterPropertiesSet();
		return factory.getObject();
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
		
}
