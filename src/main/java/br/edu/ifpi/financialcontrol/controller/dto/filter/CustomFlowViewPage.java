package br.edu.ifpi.financialcontrol.controller.dto.filter;

import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowResponseBody;
import br.edu.ifpi.financialcontrol.controller.view.FlowView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomFlowViewPage {

    @JsonView(FlowView.Simple.class)
    private List<FlowResponseBody> content;
    @JsonView(FlowView.Simple.class)
    private Long totalElements;
    @JsonView(FlowView.Simple.class)
    private Integer totalPages;
    @JsonView(FlowView.Simple.class)
    private Integer page;
    @JsonView(FlowView.Simple.class)
    private Integer numberOfElements;

    public CustomFlowViewPage(List<FlowResponseBody> content, Pageable pageable, long total) {
        PageImpl<FlowResponseBody> pageImpl = new PageImpl<>(content, pageable, total);

        this.content = pageImpl.getContent();
        this.totalElements = pageImpl.getTotalElements();
        this.totalPages = pageImpl.getTotalPages();
        this.numberOfElements = pageImpl.getNumberOfElements();
        this.page = pageImpl.getNumber();
    }
}
