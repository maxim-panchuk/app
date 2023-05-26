package com.mpanchuk.app.controller;

import com.mpanchuk.app.domain.request.OrderRequest;
import com.mpanchuk.app.domain.response.OrderResponse;
import com.mpanchuk.app.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
@Valid
public class OrderController implements SecuredRestController{

    private final OrderService orderService ;

    public OrderController(OrderService orderService) {
        this.orderService = orderService ;
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponse> makeOrder(
            @Valid @RequestBody OrderRequest orderRequest) throws Exception {
        OrderResponse resp = orderService.makeOrder(getUsername(), orderRequest.getCity(), orderRequest.getCoupon());
        if (resp == null) {
            return new ResponseEntity<>(OrderResponse.builder().message("Сумма заказа должна быть больше 1000").build(), HttpStatus.OK) ;
        }
        return new ResponseEntity<>(orderService.makeOrder(getUsername(), orderRequest.getCity(), orderRequest.getCoupon()), HttpStatus.OK);
    }

    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return "" ;
    }
}
