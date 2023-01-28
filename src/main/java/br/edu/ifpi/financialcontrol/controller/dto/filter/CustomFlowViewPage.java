package br.edu.ifpi.financialcontrol.controller.dto.filter;

import br.edu.ifpi.financialcontrol.controller.dto.flow.FlowResponseBody;
import br.edu.ifpi.financialcontrol.controller.view.FlowView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomFlowViewPage extends PageImpl<FlowResponseBody> {

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
        super(content, pageable, total);
        this.content = super.getContent();
        this.totalElements = super.getTotalElements();
        this.totalPages = super.getTotalPages();
        this.numberOfElements = super.getNumberOfElements();
        this.page = super.getNumber();
    }
}
