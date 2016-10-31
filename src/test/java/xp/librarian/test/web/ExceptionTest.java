package xp.librarian.test.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import xp.librarian.config.WebConfig;

/**
 * @author xp
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebConfig.class})
@ActiveProfiles("test")
public class ExceptionTest {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionTest.class);

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void businessException() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/200"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void unauthorizedException() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/401"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void accessForbiddenException() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/403"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void resourceNotFoundException() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/404"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void internalServerException() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/test/500"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

}
