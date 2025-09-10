package com.bankle.common.asis.domain.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RgstrMapper {
    
    ArrayList<HashMap<String, Object>> selectRgstrIncident  ();                   // 등기사건조회 대상 조회
    ArrayList<HashMap<String, Object>> selectRgstrIcdntChgYn(String escr_m_key);  // 등기사건내역 테이블 삭제
    
    int deleteRgstrIcdntI(String escr_m_key               );  // 등기사건내역 테이블 삭제
    int insertRgstrIcdntI(HashMap<String, Object> paramMap);  // 등기사건내역 테이블 등록
    int updateRgstrIcdnt (HashMap<String, Object> paramMap);  // 등기사건내역 변경여부 수정
    int insertRgstrIcdntH(String escr_m_key               );  // 등기사건내역 이력 테이블 등록
    String getAddress(String escr_m_key);
}
