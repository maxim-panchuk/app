package com.mpanchuk.app.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemToAddRequest(
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "price")
        Integer price,
        @JsonProperty(value = "city")
        String city
) {
}
