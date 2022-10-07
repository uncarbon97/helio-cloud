package cc.uncarbon.module.common;

import cc.uncarbon.framework.crud.annotation.EnableInitHikariPoolAtStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableInitHikariPoolAtStartup
@SpringBootApplication
public class CommonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApplication.class, args);
    }

}
