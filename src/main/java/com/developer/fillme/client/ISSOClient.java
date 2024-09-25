package com.developer.fillme.client;

import com.developer.fillme.client.response.FBUserRepData;
import com.developer.fillme.client.response.KakaoUserRepData;
import com.developer.fillme.client.response.NaverUserRepData;

public interface ISSOClient {
    KakaoUserRepData getKakaoUserInfo(String accessToken);

    FBUserRepData getFBUserInfo(String accessToken);

    NaverUserRepData getNaverUserInfo(String accessToken);

}
