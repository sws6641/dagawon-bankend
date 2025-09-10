package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, String> {

    @Override
    List<Members> findAll();

    Optional<Members> findByMembNo(String membNo);

    Optional<Members> findByHpNoAndEntrStc(String HpNo, String entrStc);

    Optional<Members> findByUdidAndEntrStc(String udid, String entrStc);

    Boolean existsByMembNo(String membNo);

    Optional<Members> findByCi(String ci);



    /**
     * JPQL 예제
     *
     * @param member
     * @return
     */
    @Query("select m from Members m where m.membNm = :#{#member.membNm}")
    List<Members> findMembersByMembNm(@Param("member") Members member) ;

    @Query("select m.membNo from Members m where m.membNo like :dateWithMembDsc%")
    List<String> getMembIdsInThisDay(@Param("dateWithMembDsc") String dateWithMembDsc);


    /**
     * Native SQL 예제
     * @param member
     * @return
     */
    @Query(value = "select * from TEC_MEMB_M AS m where m.MEMB_NM = :#{#member.membNm}", nativeQuery = true)
    List<Members> findMembersByMembNm2(@Param("member") Members member);


    List<Members> findByMembNmLike(String membNm);

    List<Members> findByMembNmContaining(String membNm);
}
