package com.mpanchuk.app.domain.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemToAddResponse {
    private Long id;
    private String name;
    private int price;
    private String city;
}