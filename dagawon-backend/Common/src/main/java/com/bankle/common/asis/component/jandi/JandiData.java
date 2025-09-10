package com.bankle.common.asis.component.jandi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class JandiData {

    @JsonProperty("body")
    private String body;

    @JsonProperty("connectColor")
    private String connectColor;

    @JsonProperty("connectInfo")
    private List<JandiSubData> connectInfo;  // title, description, imageUrl
}
