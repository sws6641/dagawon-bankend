package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.FeeMaster;
import com.bankle.common.asis.domain.entity.QFeeMaster;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class FeeMasterRepositorySupport extends QuerydslRepositorySupport {

    public FeeMasterRepositorySupport() {
        super(FeeMaster.class);
    }

    public List<FeeMaster> getFeeCalculationInfo(Long amt, String dsc) {
        QFeeMaster fm = QFeeMaster.feeMaster;
        JPQLQuery<FeeMaster> query = from(fm);

        return query.select(fm)
                .where(fm.frAmt.lt(amt)
                        .and(fm.toAmt.gt(amt).or(fm.toAmt.eq(amt)))
                        .and(fm.feeMKey.stringValue().like(dsc + "%"))
                )
                .fetch();

    }
}
