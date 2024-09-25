package com.developer.fillme.repository;

import com.developer.fillme.entity.FAQEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFAQRepo extends JpaRepository<FAQEntity, Long> {

    Page<FAQEntity> findAllByFaqType(String faqType, Pageable pageable);
}
