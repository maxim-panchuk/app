package com.mpanchuk.app.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemRequest(
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "price")
        Integer price
) {
}
