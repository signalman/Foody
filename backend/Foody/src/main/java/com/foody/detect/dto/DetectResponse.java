package com.foody.detect.dto;

public record DetectResponse(
    String confidence,
    String name,
    double x1,
    double x2,
    double y1,
    double y2
) {
}
