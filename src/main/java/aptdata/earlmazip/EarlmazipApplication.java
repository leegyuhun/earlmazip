package aptdata.earlmazip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class EarlmazipApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarlmazipApplication.class, args);
	}

}
