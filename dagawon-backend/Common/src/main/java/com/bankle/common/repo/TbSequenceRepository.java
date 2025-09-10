package com.bankle.common.repo;

import com.bankle.common.entity.TbSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TbSequenceRepository extends JpaRepository<TbSequence, LocalDate> {

    /**
     *
     * @name        : TbSequenceRepository.getSeq
     * @author      :
     * @param       : SeqName 별
     * @return      : 20230921 + 000001 (6자리) ->  20230925000006 14자리
     **/
    @Procedure(value = "GetSequence")
    String getSeq(@Param("sequence_name") String seqName , @Param("sequence_length") int seqLength );
}