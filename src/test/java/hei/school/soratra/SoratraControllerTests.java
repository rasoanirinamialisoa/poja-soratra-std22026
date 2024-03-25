package hei.school.soratra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SoratraControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadPhrase() throws Exception {
        String id = "123"; // ID de test
        String phrase = "une phrase poétique en minuscules"; // Phrase de test

        mockMvc.perform(MockMvcRequestBuilders.put("/soratra/{id}", id)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(phrase))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}

