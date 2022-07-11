package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createFilm() throws Exception {
        mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void createFilmWithEmptyName() throws Exception {
        MvcResult response = mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = requireNonNull(response.getResolvedException(),
                "Не получено сообщение от контроллера").getMessage();
        assertTrue(message.contains("default message [name]"));
        assertTrue(message.contains("default message [must not be blank]"));
    }

    @Test
    void createFilmWithEmptyDescription() throws Exception {
        MvcResult response = mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"kuku\",\n" +
                                "  \"description\": \"\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = requireNonNull(response.getResolvedException(),
                "Не получено сообщение от контроллера").getMessage();
        assertTrue(message.contains("default message [description]"));
        assertTrue(message.contains("default message [must not be blank]"));
    }

    @Test
    void createFilmWithEmptyReleaseDate() throws Exception {
        MvcResult response = mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"kuku\",\n" +
                                "  \"description\": \"kto tam\",\n" +
                                "  \"releaseDate\": \"\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = requireNonNull(response.getResolvedException(),
                "Не получено сообщение от контроллера").getMessage();
        assertTrue(message.contains("default message [releaseDate]"));
        assertTrue(message.contains("default message [must not be null]"));
    }

    @Test
    void createFilmWithIncorrectReleaseDate() throws Exception {
        mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"kuku\",\n" +
                                "  \"description\": \"kto tam\",\n" +
                                "  \"releaseDate\": \"3033-07-04\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFilmWithEmptyDuration() throws Exception {
        mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"kuku\",\n" +
                                "  \"description\": \"kto tam\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": \n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFilmWithIncorrectDuration() throws Exception {
        mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"kuku\",\n" +
                                "  \"description\": \"kto tam\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": -1\n" +
                                "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateFilm() throws Exception {
        mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(status().isOk());

        mockMvc.perform((put("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"name\": \"Film Updated\",\n" +
                                "  \"releaseDate\": \"1989-04-17\",\n" +
                                "  \"description\": \"New film update decription\",\n" +
                                "  \"duration\": 190\n" +
                                "}"))
                .andExpect(status().isOk());
    }
    @Test
    void getFilms() throws Exception {
        mockMvc.perform((post("/films"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"nisi eiusmod\",\n" +
                                "  \"description\": \"adipisicing\",\n" +
                                "  \"releaseDate\": \"1967-03-25\",\n" +
                                "  \"duration\": 100\n" +
                                "}"))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get("/films"))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String film = "[{\"id\":2,\"name\":\"nisi eiusmod\",\"description\":\"adipisicing\",\"releaseDate\":\"1967-03-25\",\"duration\":100}," +
                "{\"id\":1,\"name\":\"Film Updated\",\"description\":\"New film update decription\",\"releaseDate\":\"1989-04-17\",\"duration\":190}," +
                "{\"id\":3,\"name\":\"nisi eiusmod\",\"description\":\"adipisicing\",\"releaseDate\":\"1967-03-25\",\"duration\":100}]";
        assertEquals(film, result);
        assertNotNull(result);
    }
}