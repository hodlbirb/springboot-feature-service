package com.moneylion.feature;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class FeatureControllerTests {

    @Autowired
    private MockMvc mvc;

    private final String jsonBodyEnable = "{\"email\":\"hello@world\",\"featureName\":\"canWithdraw\",\"enable\":true}";
    private final String jsonBodyDisable = "{\"email\":\"hello@world\",\"featureName\":\"canWithdraw\",\"enable\":false}";

    /*
     * Should return { "canAccess" : false } by default
     * when there's no matching feature record
     * */
    @Test
    @Order(1)
    public void defaultFeatureAccessStateIsFalse() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/feature")
                         .param("email", "hello@world")
                         .param("featureName", "canWithdraw")
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.canAccess").value(false));
    }

    /*
     * Should populate db with a new feature record
     * when no feature with the same email and featureName exist
     * */
    @Test
    @Order(2)
    public void enableNewFeature() throws Exception {
        /* Post feature */
        this.mvc.perform(MockMvcRequestBuilders.post("/feature")
                         .content(jsonBodyEnable)
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        /* Get feature */
        this.mvc.perform(MockMvcRequestBuilders.get("/feature")
                         .param("email", "hello@world")
                         .param("featureName", "canWithdraw")
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.canAccess").value(true));
    }

    /*
     * Should not update db feature state
     * when the state wasn't changed
     * Should return HTTP Status Not Modified 304
     * */
    @Test
    @Order(3)
    public void HttpNotModifiedWhenFeatureNotChanged() throws Exception {
        /* Post feature */
        this.mvc.perform(MockMvcRequestBuilders.post("/feature")
                         .content(jsonBodyEnable)
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotModified());

        /* Get feature */
        this.mvc.perform(MockMvcRequestBuilders.get("/feature")
                         .param("email", "hello@world")
                         .param("featureName", "canWithdraw")
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.canAccess").value(true));
    }

    /*
     * Should update db feature state (true|false)
     * when there's a matching record
     * */
    @Test
    @Order(5)
    public void HttpOKUpdateExistingFeature() throws Exception {
        /* Post feature */
        this.mvc.perform(MockMvcRequestBuilders.post("/feature")
                         .content(jsonBodyDisable)
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

        /* Get feature */
        this.mvc.perform(MockMvcRequestBuilders.get("/feature")
                         .param("email", "hello@world")
                         .param("featureName", "canWithdraw")
                         .contentType(MediaType.APPLICATION_JSON)
                         .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.canAccess").value(false));
    }

}
