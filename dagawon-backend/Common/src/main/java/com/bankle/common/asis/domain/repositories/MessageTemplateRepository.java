package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.MessageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplate, Integer> {

}
