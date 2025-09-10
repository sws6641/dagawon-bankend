package com.bankle.common.asis.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Mapper
public interface CodeMapper {

    List<HashMap> selectCodes(String cdDvsnCd);

    List<HashMap> selectAllCodes();



}
