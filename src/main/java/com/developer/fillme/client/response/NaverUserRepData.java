package com.developer.fillme.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverUserRepData {
    @JsonProperty("resultcode")
    private String resultCode;
    private String message;
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private String id;
        @JsonProperty("profile_image")
        private String profileImage;
        private String email;
        private String name;
    }
}
