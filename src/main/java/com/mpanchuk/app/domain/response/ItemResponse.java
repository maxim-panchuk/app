package com.mpanchuk.app.domain.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
    private Long id;
    private String name;
    private int price;
}