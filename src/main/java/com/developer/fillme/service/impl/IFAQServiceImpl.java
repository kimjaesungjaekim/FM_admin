//package com.developer.fillme.service.impl;
//
//import com.developer.fillme.entity.FAQEntity;
//import com.developer.fillme.entity.QFAQEntity;
//import com.developer.fillme.model.PageResponse;
//import com.developer.fillme.model.PaginationInfo;
//import com.developer.fillme.repository.IFAQRepo;
//import com.developer.fillme.request.faq.ListFQAReq;
//import com.developer.fillme.response.faq.DetailFQAResp;
//import com.developer.fillme.service.IFAQService;
//import com.querydsl.core.BooleanBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//import org.apache.commons.lang3.StringUtils;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class IFAQServiceImpl implements IFAQService {
//    private final IFAQRepo faqRepo;
//    private final ModelMapper modelMapper;
//    private final JPAQueryFactory jpaQueryFactory;
//
//    @Override
//    @Transactional
//    public PageResponse<List<DetailFQAResp>> listInfo(ListFQAReq req) {
//        QFAQEntity qfaq = QFAQEntity.fAQEntity;
//        BooleanBuilder builder = new BooleanBuilder();
//
//        if (StringUtils.isNoneBlank(req.getType())) {
//            builder.and(qfaq.faqType.eq(req.getType()));
//        }
//
//        long offset = (req.getPage() - 1) * req.getLimit();
//        long totalRecords = jpaQueryFactory.selectFrom(qfaq).where(builder).stream().count();
//        int totalPages = (int) Math.ceil((double) totalRecords / req.getLimit());
//
//        List<FAQEntity> results = jpaQueryFactory
//                .selectFrom(qfaq)
//                .where(builder)
//                .orderBy(qfaq.createdAt.desc())
//                .limit(req.getLimit())
//                .offset(offset)
//                .fetch();
//
//        List<DetailFQAResp> resp = results.stream()
//                .map(faqEntity -> modelMapper.map(faqEntity, DetailFQAResp.class))
//                .toList();
//        PaginationInfo pagination = PaginationInfo.builder()
//                .limit(req.getLimit())
//                .offset(req.getPage())
//                .totalPages(totalPages)
//                .totalRecords(totalRecords)
//                .build();
//        return new PageResponse<>(resp, pagination);
//    }
//
//    @Override
//    public DetailFQAResp detailInfo(Long id) {
//        return faqRepo.findById(id)
//                .map(faqEntity -> modelMapper.map(faqEntity, DetailFQAResp.class))
//                .orElse(null);
//    }
//}
