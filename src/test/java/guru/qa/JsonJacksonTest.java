package guru.qa;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.qa.model.Family;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class JsonJacksonTest {
    private final ClassLoader cl = JsonJacksonTest.class.getClassLoader();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Проверка содержимого json файла")
    @Test
    public void checkJson() throws IOException {
        try (InputStream is = cl.getResourceAsStream("family.json")) {
            Family familyJson = objectMapper.readValue(is, Family.class);

            assertTrue(familyJson.isParent());
            assertEquals("Mike", familyJson.getFamilyChildren().get(0).getName());
            assertEquals(19, familyJson.getFamilyChildren().get(0).getAge());
            assertEquals("Maria", familyJson.getFamilyChildren().get(1).getName());
            assertEquals(18, familyJson.getFamilyChildren().get(1).getAge());
        }

    }
}
