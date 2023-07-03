package com.issue.tracker.common;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomModelMapper extends ModelMapper {

    @Override
    public <T> T map(Object source, Class<T> destination) {
        this.getConfiguration().setAmbiguityIgnored(true);
        Object temp = source;
        if (source == null) {
            temp = new Object();
        }

        return super.map(temp, destination);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> this.map(element, targetClass))
                .collect(Collectors.toList());
    }

    public <S, T> Set<T> mapSet(Set<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> this.map(element, targetClass))
                .collect(Collectors.toSet());
    }
}
