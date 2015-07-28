package cake;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class CakeConfig {

    @Value("${time.format}")
    private String timeformat;

    @Bean
    public ObjectMapper jacksonObjectMapper() {

        DateFormat df = new SimpleDateFormat(timeformat);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);

        return objectMapper;

    }

}