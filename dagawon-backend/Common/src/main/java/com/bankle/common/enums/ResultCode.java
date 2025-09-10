package com.bankle.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API ResponseCode
 * @author SYS
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

	SUCCESS("S000", "요청이 정상처리되었습니다."),
	FAIL("E000", "오류가 발생했습니다."),
	BAD_REQUEST("E101", "요청 형식이 맞지 않습니다."),
	MEDIA_TYPE_NOT_SUPPORT("E102", "지원하지 않는 미디어 타입입니다."),
	UNAUTHORIZED("E103", "접근 권한이 없습니다."),
	USER_NOT_FOUND("E104", "사용자 정보를 찾을 수 없습니다."),
	NOT_FOUND("E105", "잘못된 접근입니다."),
	INITIAL_PASSWORD("E106", "임시 비밀번호로 로그인하였습니다."),
	SQL_FAIL("E107", "SQL 수행 중 오류가 발생했습니다."),
	WRONG_PWD("E108", "기존 비밀번호가 맞지 않습니다."),
	EXIST_PWD("E109", "기존 비밀번호와 동일한 비밀번호 입니다. 다시 변경해 주세요."),
	
	// JWT
	WRONG_TOKEN("E201", "잘못된 토큰입니다."),
	WRONG_TOKEN_TYPE("E202", "잘못된 타입의 토큰입니다."),
	TOKEN_EXPIRED("E203", "만료된 토큰입니다."),

	// 모바일 본인인증
	MOBILE_CERT_NOT_VERIFIED("E300", "인증이 완료되지 않았습니다."),
	MOBILE_CERT_AUTO_NO_INCONSISTENCY("E300", "인증번호가 일치하지 않습니다."),
	MOBILE_CERT_MODULE_ERROR("E301", "본인인증 모듈에서 알수없는 오류가 발생했습니다."),
	MOBILE_CERT_MODULE_DECRYPTION_ERROR("E302", "본인인증 중 복호화 시스템 오류가 발생했습니다."),
	MOBILE_CERT_MODULE_ARGUMENT_ERROR("E303", "입력 정보에 오류가 있습니다."),
	MOBILE_CERT_MODULE_SITE_CODE_INCONSISTENCY("E304", "본인 인증 모듈의 사이트코드 혹은 비밀번호가 맞지 않습니다."),
	MOBILE_CERT_MODULE_NETWORK_ERROR("E305", "서버 네트웍크 및 방확벽 관련하여 아래 IP와 Port를 오픈해 주셔야 이용 가능합니다.IP : 121.131.196.200 / Port : 3700 ~ 3715"),

	// 파일 업로드
	FILE_UPLOAD_FAIL("E400", "파일 업로드 중 오류가 발생했습니다."),
	FILE_DELETE_FAIL("E401", "파일 삭제 중 오류가 발생했습니다."),
	FILE_DOWNLOAD_FAIL("E402", "파일 다운로드 중 오류가 발생했습니다."),
	FILE_NOT_FOUND("E403", "시스템 저장소에 파일이 존재하지 않습니다."),

	// 엑셀 업/다운로드
	EXCEL_COLUMNS_NOT_DEFINED("E501", "엑셀 헤더가 정의되지 않았습니다."),
	EXCEL_DATA_ERROR("E502", "엑셀 데이터 생성 중 오류가 발생했습니다."),
	EXCEL_DOWNLOAD_ERROR("E503", "엑셀 다운로드 중 오류가 발생했습니다."),
	EXCEL_UPLOAD_ERROR("E503", "엑셀 문서 업로드 중 오류가 발생했습니다."),
	EXCEL_CELLS_NOT_MATCHED("E503", "업로드할 문서와 셀 개수가 일치하지 않습니다.")
	;

	private String code;
	private String msg;
}