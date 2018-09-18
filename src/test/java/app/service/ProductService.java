package app.service;

import app.domain.Product;
import app.payload.ProductPayload;
import lombok.NonNull;

public interface ProductService {

  Product createProduct(@NonNull ProductPayload payload);

}
