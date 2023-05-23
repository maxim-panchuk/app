package com.mpanchuk.app.controller;

import com.mpanchuk.app.domain.request.OrderRequest;
import com.mpanchuk.app.domain.response.OrderResponse;
import com.mpanchuk.app.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
@Valid
public class OrderController {

    private final OrderService orderService ;

    public OrderController(OrderService orderService) {
        this.orderService = orderService ;
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponse> makeOrder(
            @NonNull HttpServletRequest request,
            @Valid @RequestBody OrderRequest orderRequest) throws Exception {
        OrderResponse resp = orderService.makeOrder(getJwtString(request), orderRequest.getCity(), orderRequest.getCoupon());
        if (resp == null) {
            return new ResponseEntity<>(OrderResponse.builder().message("Сумма заказа должна быть больше 1000").build(), HttpStatus.OK) ;
        }
        return new ResponseEntity<>(orderService.makeOrder(getJwtString(request), orderRequest.getCity(), orderRequest.getCoupon()), HttpStatus.OK);
    }

    private String getJwtString(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        return authHeader.substring(7);
    }
}
