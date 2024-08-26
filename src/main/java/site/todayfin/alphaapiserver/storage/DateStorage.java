package site.todayfin.alphaapiserver.storage;

import org.springframework.stereotype.Component;

@Component
public class DateStorage {
    private String date;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
