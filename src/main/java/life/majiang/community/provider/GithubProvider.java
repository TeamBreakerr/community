package life.majiang.community.provider;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(50000, TimeUnit.MILLISECONDS)
                .readTimeout(50000, TimeUnit.MILLISECONDS)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody body = RequestBody.create(objectMapper.writeValueAsBytes(accessTokenDTO), JSON);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .addHeader("Accept", "application/json")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            JsonNode res = objectMapper.readTree(new StringReader(response.body().string()));
            return res.get("access_token").asText();
        }
    }

    public GithubUser GithubUser(String accessToken) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("Authorization", "token " + accessToken)
                .url("https://api.github.com/user")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return objectMapper.readValue(response.body().string(), GithubUser.class);
        }
    }
}
