package com.temporal.poc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderStatusRequest {

    @JsonProperty("returnOrderNo")
    private String returnOrderNo;

    @JsonProperty("status")
    private String status;

    @JsonProperty("itemId")
    private String itemId;

}
