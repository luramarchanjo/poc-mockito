package app.service;

import app.exception.RequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import lombok.NonNull;
import lombok.extern.java.Log;
import lombok.val;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Log
public class HttpClientImpl implements HttpClient {

  private final MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final OkHttpClient httpClient = new OkHttpClient();
  private final String url;

  public HttpClientImpl(@NonNull String url) {
    this.url = url;
  }

  public Response post(@NonNull String path, @NonNull Object payload) {
    Response response;

    log.info(String.format("Calling endpoint POST - %s ", url + path));
    try {
      val jsonPayload = objectMapper.writeValueAsString(payload);
      RequestBody requestBody = RequestBody.create(mediaType, jsonPayload);

      Request request = new Request.Builder()
        .url(url + path)
        .post(requestBody)
        .build();

      Call call = httpClient.newCall(request);
      response = call.execute();
    } catch (IOException e) {
      log.log(Level.SEVERE, "Error", e);
      throw new RequestException(String.format("Error to request POST=[%s]", payload), e);
    }

    log.info(String.format("Received %s", response));
    return response;
  }

}
