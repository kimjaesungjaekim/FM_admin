package com.developer.fillme.service;

import com.developer.fillme.model.PageResponse;
import com.developer.fillme.request.faq.ListFQAReq;
import com.developer.fillme.response.faq.DetailFQAResp;

import java.util.List;

public interface IFAQService {
    PageResponse<List<DetailFQAResp>> listInfo(ListFQAReq req);

    DetailFQAResp detailInfo(Long id);
}
