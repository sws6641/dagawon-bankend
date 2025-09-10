package com.bankle.common.util.httpapi.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlTransient;

@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseVo extends ConvertVo {

//	@ApiModelProperty(hidden = true)
	@XmlTransient
	private String body;

}
