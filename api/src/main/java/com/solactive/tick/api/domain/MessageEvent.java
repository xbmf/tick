package com.solactive.tick.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEvent<T> implements ResolvableTypeProvider {
    private T message;

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(this.message)
        );
    }
}

