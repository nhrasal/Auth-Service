package com.app.auth.base.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaModel {
    private Integer offset;
    private Integer prevOffset;
    private Integer nextOffset;
    private Integer limit;
    private Long totalRecords;
    private Integer resultCount;
    private Integer totalPageCount;
    private List<SortModel> sort;
}
