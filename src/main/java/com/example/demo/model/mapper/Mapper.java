package com.example.demo.model.mapper;

public interface Mapper<E, P> {

    P mapToPayload(E entity);

    E mapToEntity(P payload);
}
