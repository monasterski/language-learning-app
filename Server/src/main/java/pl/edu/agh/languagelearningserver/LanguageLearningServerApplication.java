package pl.edu.agh.languagelearningserver;

import com.google.common.base.Predicate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;


@SpringBootApplication
@EnableSwagger2
public class LanguageLearningServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(LanguageLearningServerApplication.class, args);
	}


	@Bean
	public Docket get(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(getSwaggerPaths())
				.build();

	}

	private Predicate<String> getSwaggerPaths(){
		return or(
				regex("/word.*"),
				regex("/category.*"),
				regex("/user.*"),
				regex("/quiz.*"),
				regex("/test.*")
		);
	}

}
