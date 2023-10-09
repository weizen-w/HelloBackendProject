package spring.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 09/10/2023 HelloTest
 *
 * @author Wladimir Weizen
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Endpoint /city is works:")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
public class CityControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Nested
  @DisplayName("GET /city/all:")
  public class GetCourses {

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void return_empty_list_of_cities_for_empty_database() throws Exception {
      mockMvc.perform(get("/city/all"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()", is(0)));
    }

    @Test
    @Sql(scripts = "/sql/data.sql")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void return_list_of_cities_for_not_empty_database() throws Exception {
      mockMvc.perform(get("/city/all"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()", is(2)))
          .andExpect(jsonPath("$.[0].id", is(1)))
          .andExpect(jsonPath("$.[1].id", is(2)))
          .andExpect(jsonPath("$.[0].name", is("Paris")))
          .andExpect(jsonPath("$.[1].name", is("Berlin")));
    }
  }

  @Nested
  @DisplayName("POST /city/add:")
  public class PostCourse {

    @Test
    @Sql(scripts = "/sql/data.sql")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void return_created_city() throws Exception {
      mockMvc.perform(post("/city/add")
              .contentType(MediaType.APPLICATION_JSON)
              .content("{\n" +
                  "  \"name\": \"Amsterdam\" " +
                  "}"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(3)));
    }
  }

  @Nested
  @DisplayName("GET /city/{id}:")
  public class GetCourse {

    @Test
    @Sql(scripts = "/sql/data.sql")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void return_existed_city() throws Exception {
      mockMvc.perform(get("/city/1"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id", is(1)))
          .andExpect(jsonPath("$.name", is("Paris")));
    }
  }
}
