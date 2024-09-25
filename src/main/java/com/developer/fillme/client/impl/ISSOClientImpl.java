package com.developer.fillme.client.impl;

import com.developer.fillme.client.IBaseRestClient;
import com.developer.fillme.client.ISSOClient;
import com.developer.fillme.client.header_config.IHeaderConfig;
import com.developer.fillme.client.response.FBUserRepData;
import com.developer.fillme.client.response.KakaoUserRepData;
import com.developer.fillme.client.response.NaverUserRepData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ISSOClientImpl implements ISSOClient {
    private final IBaseRestClient baseRestClient;
    private final IHeaderConfig headerConfig;

    @Override
    public KakaoUserRepData getKakaoUserInfo(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me?property_keys=[\"kakao_account.email\",\"kakao_account.gender\",\"kakao_account.birthday\",\"kakao_account.profile.nickname\"]";
        HttpEntity<Object> entity = headerConfig.headerConfig(accessToken);
        return baseRestClient.callAPI(url, HttpMethod.GET, entity, KakaoUserRepData.class);
    }

    @Override
    public FBUserRepData getFBUserInfo(String accessToken) {
        String url = "https://graph.facebook.com/v20.0/me?fields=id,name,email,gender,birthday";
        HttpEntity<Object> entity = headerConfig.headerConfig(accessToken);
        return baseRestClient.callAPI(url, HttpMethod.GET, entity, FBUserRepData.class);
    }

    @Override
    public NaverUserRepData getNaverUserInfo(String accessToken) {
        String url = "https://openapi.naver.com/v1/nid/me";
        HttpEntity<Object> entity = headerConfig.headerConfig(accessToken);
        return baseRestClient.callAPI(url, HttpMethod.GET, entity, NaverUserRepData.class);
    }
}
