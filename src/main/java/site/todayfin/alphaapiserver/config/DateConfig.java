package site.todayfin.alphaapiserver.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import site.todayfin.alphaapiserver.storage.DateStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableScheduling
public class DateConfig {

    @Autowired
    private DateStorage dateStorage;

    // 애플리케이션 시작 시 데이터 초기화
    @PostConstruct
    public void init() {
        LocalDateTime nowUTC = LocalDateTime.now(ZoneId.of("UTC"));
        LocalDate dateToUse;

        // UTC 시간이 0시 30분 이전인지 확인
        if (nowUTC.getHour() == 0 && nowUTC.getMinute() < 30) {
            // 0시 30분 이전이면 전날 날짜 사용
            dateToUse = nowUTC.toLocalDate().minusDays(1);
        } else {
            // 그렇지 않으면 현재 날짜 사용
            dateToUse = nowUTC.toLocalDate();
        }

        String dateString = dateToUse.format(DateTimeFormatter.ISO_DATE);
        saveDate(dateString);
    }

    // 매일 UTC 12시 30분에 실행
    @Scheduled(cron = "0 30 12 * * ?", zone = "UTC")
    public void performTask() {
        LocalDate now = LocalDate.now();
        String dateString = now.format(DateTimeFormatter.ISO_DATE);
        saveDate(dateString);
    }

    private void saveDate(String date) {
        dateStorage.setDate(date);
    }
}
