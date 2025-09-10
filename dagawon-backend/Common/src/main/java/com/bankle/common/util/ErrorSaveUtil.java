package com.bankle.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorSaveUtil {
	
	
//	private final TbCommErrorRepository tbCommErrorRepository;
	
	public void errorSave1(HttpServletRequest request, Exception ex) {
		
		try {
			// Initialize class name string
			String className = "";
			
			// Extract stack trace details if available
			if (ex.getStackTrace().length > 0) {
				StackTraceElement firstElement = ex.getStackTrace()[0];
				className = firstElement.getClassName() + ".java, " + firstElement.getMethodName() + " , line : " + firstElement.getLineNumber();
			}
			
			// Retrieve request parameters
			Map<String, String[]> parameterMap = request.getParameterMap();
			String mapAsString = parameterMap.keySet().stream()
				.map(key -> key + "=" + Arrays.toString(parameterMap.get(key)))
				.collect(Collectors.joining(", ", "{'", "'}"));
			log.error("mapAsString : "  + mapAsString);
			
			// Read message body from the request
			StringBuilder stringBuilder = new StringBuilder();
			InputStream inputStream = request.getInputStream();
			String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
			log.error("messageBody : " + messageBody);
			
			//			Map m = request.getParameterMap();
//			Set s = m.entrySet();
//			Iterator it = s.iterator();
//			while(it.hasNext()){
//				Map.Entry<String,String[]> entry = (Map.Entry<String,String[]>)it.next();
//				String key             = entry.getKey();
//				String[] value         = entry.getValue();
//				System.out.println("Key is "+key+"<br>");
//				if(value.length>1){
//					for (int i = 0; i < value.length; i++) {
//						log.debug("<li>" + value[i].toString() + "</li><br>");
//					}
//				}else {
//					log.debug("Value is " + value[0].toString() + "<br>");
//				}
//				log.debug("-------------------<br>");
//			}
			
			// Get user information
			/*UserPrincipal user;
			try {
				user = UserAuthSvc.getStaticSession();
			} catch (Exception e) {
				user = null;
			}
			
			// Construct error DTO and save it
			var dto = TbCommErrorDto.builder()
				.message(ex.getMessage())
				.url(request.getRequestURI())
				.parameter(mapAsString.isEmpty() ? messageBody : mapAsString)
				.stackTrace(Arrays.toString(ex.getStackTrace()))
				.membNo(user != null ? UserAuthSvc.getStaticSession().getMembNo() : "")
				.crtDtm(LocalDateTime.now())
				.className(className)
				.build();
			tbCommErrorRepository.save(TbCommErrorMapper.INSTANCE.toEntity(dto));*/
			
		} catch (Exception e) {
			// Log error if any issue occurs during the process
			log.error("Error table loading error =>" +  e.getMessage());
			e.printStackTrace();
		}
		
		
	}
}
