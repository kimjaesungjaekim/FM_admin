package com.developer.fillme.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserRepData {
    private Long id;
    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @Setter
    public static class KakaoAccount {
        private KakaoProfile profile;
    }

    @Getter
    @Setter
    public static class KakaoProfile {
        private String nickname;
    }
}
