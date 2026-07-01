package com.likelion.pbl.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createLionReturnsCreatedWhenNameIsNew() throws Exception {
        mockMvc.perform(post("/members/lions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "이영희",
                                  "major": "컴퓨터공학",
                                  "generation": 13,
                                  "part": "Backend",
                                  "studentId": "20240001"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("이영희"))
                .andExpect(jsonPath("$.roleName").value("LION"))
                .andExpect(jsonPath("$.studentId").value("20240001"));
    }

    @Test
    void createStaffReturnsConflictWhenNameAlreadyExists() throws Exception {
        String body = """
                {
                  "name": "홍길동",
                  "major": "경영학",
                  "generation": 12,
                  "part": "Backend",
                  "position": "회장"
                }
                """;

        mockMvc.perform(post("/members/staffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/members/staffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict());
    }

    @Test
    void findMemberReturnsNotFoundWhenNameDoesNotExist() throws Exception {
        mockMvc.perform(get("/members/없는이름"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateLionReturnsOkWhenMemberExists() throws Exception {
        mockMvc.perform(post("/members/lions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "박민수",
                                  "major": "기계공학",
                                  "generation": 13,
                                  "part": "Backend",
                                  "studentId": "20240002"
                                }
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/members/lions/박민수")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "major": "소프트웨어학",
                                  "generation": 14,
                                  "part": "Frontend",
                                  "studentId": "20250002"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("박민수"))
                .andExpect(jsonPath("$.major").value("소프트웨어학"))
                .andExpect(jsonPath("$.generation").value(14))
                .andExpect(jsonPath("$.studentId").value("20250002"));
    }

    @Test
    void deleteMemberReturnsNoContentWhenMemberExists() throws Exception {
        mockMvc.perform(post("/members/staffs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "김운영",
                                  "major": "디자인",
                                  "generation": 11,
                                  "part": "Planning",
                                  "position": "운영진"
                                }
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/members/김운영"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/members/김운영"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findMembersReturnsAllOrExactNameMatches() throws Exception {
        mockMvc.perform(post("/members/lions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "검색테스트",
                                  "major": "컴퓨터공학",
                                  "generation": 13,
                                  "part": "Backend",
                                  "studentId": "20240003"
                                }
                                """))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        mockMvc.perform(get("/members").param("name", "검색테스트"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("검색테스트"));
    }

    @Test
    void openApiDocsAreAvailable() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/members/lions'].post.summary").value("Register a Lion"))
                .andExpect(jsonPath("$.paths['/members/lions'].post.responses['201'].description")
                        .value("Lion registered"))
                .andExpect(jsonPath("$.paths['/members/{name}'].delete.responses['204'].description")
                        .value("Member deleted"));
    }
}
