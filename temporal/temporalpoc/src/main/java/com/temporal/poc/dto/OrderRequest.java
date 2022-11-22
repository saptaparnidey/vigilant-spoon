package com.temporal.poc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderRequest {

    @JsonProperty("returnOrderNo")
    private String returnOrderNo;

    @JsonProperty("refundAmount")
    private String refundAmount;

    @JsonProperty("itemId")
    private String itemId;

    @JsonProperty("refundType")
    private String refundType;
}
