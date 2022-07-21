package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.dao.InMemoryUserStorage;



import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private final MockMvc mockMvc;
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserControllerTest(
            MockMvc mockMvc, InMemoryUserStorage userStorage) {
        this.mockMvc = mockMvc;
        this.userStorage = userStorage;
    }
    @Test
    void createUser() throws Exception {
        mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void createUserWithEmptyLogin() throws Exception {
        MvcResult response = mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = requireNonNull(response.getResolvedException(),
                "Не получено сообщение от контроллера").getMessage();
        assertTrue(message.contains("default message [login]"));
        assertTrue(message.contains("default message [must not be blank]"));
    }

    @Test
    void createUserWithEmptyName() throws Exception {
        mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    void createUserWithEmptyEmail() throws Exception {
        MvcResult response = mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = requireNonNull(response.getResolvedException(),
                "Не получено сообщение от контроллера").getMessage();
        assertTrue(message.contains("default message [email]"));
        assertTrue(message.contains("default message [must not be blank]"));
    }

    @Test
    void createUserWithIncorrectEmail() throws Exception {
        MvcResult response = mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"11111coco.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String message = requireNonNull(response.getResolvedException(),
                "Не получено сообщение от контроллера").getMessage();
        assertTrue(message.contains("default message [email]"));
        assertTrue(message.contains("default message [must be a well-formed email address]"));
    }

    @Test
    void createUserWithEmptyBirthday() throws Exception {
        mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \n" +
                                "}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void createUserWithIncorrectBirthday() throws Exception {
        mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"5555-55-55\"\n" +
                                "}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void updateUser() throws Exception {
        mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(status().isOk());

        mockMvc.perform((put("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"doloreUpdate\",\n" +
                                "  \"name\": \"est adipisicing\",\n" +
                                "  \"id\": 1,\n" +
                                "  \"email\": \"mail@yandex.ru\",\n" +
                                "  \"birthday\": \"1976-09-20\"\n" +
                                "}"))
                .andExpect(status().isOk());
    }


    @Test
    void getUsers() throws Exception {
        mockMvc.perform((post("/users"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"login\": \"dolore\",\n" +
                                "  \"name\": \"Nick Name\",\n" +
                                "  \"email\": \"mail@mail.ru\",\n" +
                                "  \"birthday\": \"1946-08-20\"\n" +
                                "}"))
                .andExpect(status().isOk());

        MvcResult mvcResult = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String user = "[{\"id\":1,\"email\":\"mail@yandex.ru\",\"login\":\"doloreUpdate\",\"name\":\"est adipisicing\",\"birthday\":\"1976-09-20\"}," +
                "{\"id\":2,\"email\":\"mail@mail.ru\",\"login\":\"dolore\",\"name\":\"dolore\",\"birthday\":\"1946-08-20\"}," +
                "{\"id\":4,\"email\":\"mail@mail.ru\",\"login\":\"dolore\",\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}," +
                "{\"id\":3,\"email\":\"mail@mail.ru\",\"login\":\"dolore\",\"name\":\"Nick Name\",\"birthday\":\"1946-08-20\"}]";
        assertEquals(user, result);
        assertNotNull(result);
    }
}