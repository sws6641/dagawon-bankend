package com.bankle.common.asis.domain.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bankle.common.asis.domain.entity.Members;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface MemberMapper {
    List<Map<String, Object>> getMembers();
    
    List<HashMap<String, Object>> selectEntrAsntCnts ();
    
    int updateMembInfo(HashMap<String, Object> paramMap);
    
    int insertEntrAsnt(HashMap<String, Object> paramMap);
    
    int deleteEntrAsnt(HashMap<String, Object> paramMap);
    
    int chkMembDump(HashMap<String, Object> paramMap);
    Members getMemberInfo(HashMap<String, Object> paramMap);
}
