package site.todayfin.alphaapiserver.storage;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class DateStorage {
    private String date;

    public String getDate() {
        setDate();
        return this.date;
    }

    public void setDate() {
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
        this.date = dateString;
    }
}
