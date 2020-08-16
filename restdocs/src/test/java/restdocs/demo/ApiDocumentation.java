package restdocs.demo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;

/**
 * @author Daeho Oh
 * @since 2020-08-16
 */
@WebMvcTest(CustomerRestController.class)
@AutoConfigureRestDocs
public class ApiDocumentation {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void errorExample() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/error")
                        .contentType(MediaType.APPLICATION_JSON)
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                        .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/customers")
                        .requestAttr(RequestDispatcher.ERROR_MESSAGE,
                                "The customer 'http://localhost:8443/v1/customers/123' does not exist")
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("error", Matchers.is("Bad Request")))
                .andExpect(MockMvcResultMatchers.jsonPath("timestamp", Matchers.is(Matchers.notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("status", Matchers.is(400)))
                .andExpect(MockMvcResultMatchers.jsonPath("path", Matchers.is(Matchers.notNullValue())))
                .andDo(MockMvcRestDocumentation.document("error-example"));
    }

    @Test
    public void indexExample() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/customers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("index-example"));
    }
}
