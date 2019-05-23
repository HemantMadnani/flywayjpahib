package co.in.spring.config;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@EnableJpaRepositories("co.in.spring.dao")
@ComponentScan("co.in.spring")
public class RootConfig {

	@Autowired
	private Environment environment;

	@Bean
	@PostConstruct
	public DataSource flywayportalSource() {

		final BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(environment.getProperty("db.driver"));
		basicDataSource.setUsername(environment.getProperty("db.username"));
		basicDataSource.setPassword(environment.getProperty("db.password"));
		basicDataSource.setUrl(environment.getProperty("db.url"));
		return basicDataSource;
	}

	public Properties hibernateProperties() {

		final Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
		properties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
		properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}

	@Bean
	@DependsOn("portalFly")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		final LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		bean.setJpaProperties(hibernateProperties());
		bean.setDataSource(flywayportalSource());
		bean.setPackagesToScan("co.in.spring.model");
		bean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return bean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {

		final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return jpaTransactionManager;
	}

	@SuppressWarnings("deprecation")
	@Bean(initMethod = "migrate")
	public Flyway portalFly() {

		final Flyway flyway = new Flyway();
		flyway.setDataSource(flywayportalSource());
		flyway.setLocations("classpath:/migrations");
		flyway.setIgnoreMissingMigrations(true);
		flyway.setIgnoreIgnoredMigrations(true);
		flyway.setBaselineOnMigrate(true);
		flyway.migrate();
		return flyway;

	}

}
