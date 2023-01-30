package br.edu.ifpi.financialcontrol;

import br.edu.ifpi.financialcontrol.controller.dto.category.CategoryIdRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowRequestBody;
import br.edu.ifpi.financialcontrol.controller.dto.type.TypeIdRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FinancialControlApplicationIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnAFlowsCollection_whenSuccessful() throws Exception {
        var request = MockMvcRequestBuilders.get("/flow")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    public void shouldReturnAFlowSingletonResource_whenSuccessful() throws Exception {
        var request = MockMvcRequestBuilders.get("/flow/e696ba92-bb09-4bcb-bfc9-c9fd68723d95")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("e696ba92-bb09-4bcb-bfc9-c9fd68723d95"))
                .andExpect(jsonPath("$.description").value("Plano de saúde"))
                .andExpect(jsonPath("$.value").value(50))
                .andExpect(jsonPath("$.date").value("2023-01-27T10:50:25Z"))
                .andExpect(jsonPath("$.type.id").value(2))
                .andExpect(jsonPath("$.category.id").value(2));
    }

    @Test
    public void shouldReturnABadRequestStatusCode_whenFlowCodeIsntValid() throws Exception {
        var request = MockMvcRequestBuilders.get("/flow/prank")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldReturnAFlowSaved_whenSuccessful() throws Exception {
        FlowRequestBody flowRequestBody = FlowRequestBody.builder()
                .description("Bonificação de funcionário do mês")
                .value(new BigDecimal("500"))
                .category(new CategoryIdRequestBody(6L))
                .type(new TypeIdRequestBody(1L))
                .build();

        var request = MockMvcRequestBuilders.post("/flow")
                .content(mapper.writeValueAsString(flowRequestBody))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.type.id").value(1))
                .andExpect(jsonPath("$.category.id").value(6));;
    }

    @Test
    public void shouldReturnABadRequestStatusCode_whenFlowToBeSavedHasNullableCategoryId() throws Exception {
        FlowRequestBody flowRequestBody = FlowRequestBody.builder()
                .description("Bonificação de funcionário do mês")
                .value(new BigDecimal("500"))
                .category(null)
                .type(new TypeIdRequestBody(1L))
                .build();

        var request = MockMvcRequestBuilders.post("/flow")
                .content(mapper.writeValueAsString(flowRequestBody))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnABadRequestStatusCode_whenFlowToBeSavedHasNullableTypeId() throws Exception {
        FlowRequestBody flowRequestBody = FlowRequestBody.builder()
                .description("Bonificação de funcionário do mês")
                .value(new BigDecimal("500"))
                .category(new CategoryIdRequestBody(6L))
                .type(null)
                .build();

        var request = MockMvcRequestBuilders.post("/flow")
                .content(mapper.writeValueAsString(flowRequestBody))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnABadRequestStatusCode_whenFlowToBeSavedHasSomeNullableFields() throws Exception {
        FlowRequestBody flowRequestBody = FlowRequestBody.builder()
                .description(null)
                .value(null)
                .category(new CategoryIdRequestBody(6L))
                .type(new TypeIdRequestBody(1L))
                .build();

        var request = MockMvcRequestBuilders.post("/flow")
                .content(mapper.writeValueAsString(flowRequestBody))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldUpdateAndReturnANoContentStatusCode_whenSuccessful() throws Exception {
        FlowRequestBody flowRequestBody = FlowRequestBody.builder()
                .description("Pente de memória DDR4")
                .value(new BigDecimal("250"))
                .category(new CategoryIdRequestBody(4L))
                .type(new TypeIdRequestBody(2L))
                .build();

        var request = MockMvcRequestBuilders.put("/flow/c6537384-cf50-483e-be9e-35b7ed8647eb")
                .content(mapper.writeValueAsString(flowRequestBody))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestStatusCode_whenFlowToBeUpdatedHasSomeNullableFields() throws Exception {
        FlowRequestBody flowRequestBody = FlowRequestBody.builder()
                .description(null)
                .value(null)
                .category(new CategoryIdRequestBody(4L))
                .type(new TypeIdRequestBody(2L))
                .build();

        var request = MockMvcRequestBuilders.put("/flow/c6537384-cf50-483e-be9e-35b7ed8647eb")
                .content(mapper.writeValueAsString(flowRequestBody))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void shouldDeleteAndReturnNoContentStatusCode_whenSuccessful() throws Exception {
        var request = MockMvcRequestBuilders.delete("/flow/85edb5c7-2afd-4d29-a27b-837c9f58c087");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestStatusCode_whenFlowCodeParameterIsntValid() throws Exception {
        var request = MockMvcRequestBuilders.delete("/flow/prank");

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
