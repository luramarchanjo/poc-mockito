package app.service;

import okhttp3.Response;

public interface HttpClient {

  Response post(String path, Object payload);

}
