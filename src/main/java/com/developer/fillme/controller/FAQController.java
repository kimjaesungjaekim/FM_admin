//package com.developer.fillme.controller;
//
//import com.developer.fillme.model.BaseResponse;
//import com.developer.fillme.model.PageResponse;
//import com.developer.fillme.request.faq.ListFQAReq;
//import com.developer.fillme.response.faq.DetailFQAResp;
//import com.developer.fillme.service.IFAQService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springdoc.core.annotations.ParameterObject;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@Tag(name = "FAQ", description = "API FAQ INFO")
//@RequiredArgsConstructor
//@RequestMapping("/api/faq")
//public class FAQController {
//    private final IFAQService faqService;
//
//    @Operation(summary = "LIST INFO FAQ")
//    @GetMapping()
//    public ResponseEntity<PageResponse<List<DetailFQAResp>>> listInfo(@ParameterObject @ModelAttribute ListFQAReq req) {
//        PageResponse<List<DetailFQAResp>> editUserResp = faqService.listInfo(req);
//        return ResponseEntity.ok().body(editUserResp);
//    }
//
//    @Operation(summary = "DETAIL FAQ")
//    @GetMapping("/{id}")
//    public ResponseEntity<BaseResponse<DetailFQAResp>> detailInfo(
//            @Parameter(description = "Id FAQ") @PathVariable("id") Long id) {
//        DetailFQAResp editUserResp = faqService.detailInfo(id);
//        return ResponseEntity.ok().body(new BaseResponse<>(editUserResp));
//    }
//}
