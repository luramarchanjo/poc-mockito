package app.service;

import app.domain.Product;
import app.exception.RequestException;
import app.payload.ProductPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.NonNull;
import okhttp3.Response;

public class ProductServiceImpl implements ProductService {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final HttpClient httpClient;

  public ProductServiceImpl(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public Product createProduct(@NonNull ProductPayload payload) {
    Product product = null;

    Response response = httpClient.post("/api/v1/products", payload);
    if (response.isSuccessful() && response.body() != null) {
      String body = response.body().toString();
      product = convertJsonToProduct(body);
    } else {
      throw new RequestException(String.format("Error to create Product=[%s]", payload));
    }

    return product;
  }

  private Product convertJsonToProduct(@NonNull String json) {
    try {
      return objectMapper.readValue(json, Product.class);
    } catch (IOException e) {
      throw new RequestException(String.format("Error to create Product, invalid json=[%s]", json));
    }
  }

}
