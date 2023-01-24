package me.choicore.study.springbootjpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootJpaShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaShopApplication.class, args);
    }


    // Hibernate5Module을 빈으로 등록하면, Jackson이 Hibernate5Module을 사용하게 된다.
    // Hibernate5Module은 Hibernate가 지원하는 Lazy Loading을 지원한다.
    // Entity를 직접 노출하는 것은 좋지 않지만, Entity를 직접 노출해야 하는 경우에는 이 방법을 사용하면 된다.
    @Bean
    Hibernate5Module hibernate5Module() {
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        // hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        return hibernate5Module;
    }
}
