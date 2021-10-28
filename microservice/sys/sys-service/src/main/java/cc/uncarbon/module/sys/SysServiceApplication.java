package cc.uncarbon.module.sys;

import cc.uncarbon.framework.crud.annotation.EnableInitHikariPoolAtStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableInitHikariPoolAtStartup
@SpringBootApplication
public class SysServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysServiceApplication.class, args);
    }

}
