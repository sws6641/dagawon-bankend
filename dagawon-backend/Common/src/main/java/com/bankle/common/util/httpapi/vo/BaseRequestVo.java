package com.bankle.common.util.httpapi.vo;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

@NoArgsConstructor
@Getter
@Setter
public class BaseRequestVo extends ConvertVo {
//	@ApiModelProperty(hidden = true)
	@Expose(serialize = false)
	private transient HttpHeaders headers = new HttpHeaders();
}
