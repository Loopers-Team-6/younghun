package com.loopers.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "root")
@JsonSubTypes({
    @JsonSubTypes.Type(value = RootMeticsMessage.class, name = "ROOT")
})
public sealed interface RootMessage permits RootMeticsMessage {
}
