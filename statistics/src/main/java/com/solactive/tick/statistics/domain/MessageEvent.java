package com.solactive.tick.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEvent<T> implements ResolvableTypeProvider {
    private T data;

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(this.data)
        );
    }
}
