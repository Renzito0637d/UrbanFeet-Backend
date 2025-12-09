package com.urbanfeet_backend.Model.CarritoDTOs;

import java.util.List;

public record CarritoResponse(
        Integer id,
        Double totalEstimado,
        List<CarritoItemDetailResponse> items) {
}