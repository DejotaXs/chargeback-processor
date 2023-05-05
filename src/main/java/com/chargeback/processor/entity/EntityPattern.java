package com.chargeback.processor.entity;

import java.io.Serializable;

public interface EntityPattern<T> extends Serializable {
    T toResponseDto();
}
