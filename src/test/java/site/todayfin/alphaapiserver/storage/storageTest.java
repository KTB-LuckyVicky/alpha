package site.todayfin.alphaapiserver.storage;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import site.todayfin.alphaapiserver.AlphaApiServerApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {AlphaApiServerApplication.class})
@TestPropertySource("classpath:application.properties")
public class storageTest {

    @Autowired
    DateStorage dateStorage;

    @Test
    void dateStorageTest() {
        assertNotNull(dateStorage.getDate());
        //assertEquals("2024-08-26", dateStorage.getDate());
    }
}
