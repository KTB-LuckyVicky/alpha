package site.todayfin.alphaapiserver.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import site.todayfin.alphaapiserver.storage.DateStorage;


@Configuration
public class DateConfig {

    @Autowired
    private DateStorage dateStorage;

    // 애플리케이션 시작 시 데이터 초기화
    @PostConstruct
    public void init() {
        dateStorage.setDate();
    }
}
