package ru.ivan.keycloak.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ivan.keycloak.annotation.AllowedRoles;
import ru.ivan.keycloak.model.Product;
import ru.ivan.keycloak.model.Role;
import ru.ivan.keycloak.repository.ProductRepository;
import ru.ivan.keycloak.util.ValidationUtil;


@Slf4j
@RestController
@RequestMapping(value = ProductController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProductController {

  public static final String REST_URL = "/api/products";
  private final ProductRepository productRepository;

  @GetMapping
  @AllowedRoles({Role.MODERATOR, Role.USER})
  public List<Product> getAll() {
    log.info("getAll");
    return productRepository.findAll();
  }

  @GetMapping("/{id}")
  @AllowedRoles({Role.MODERATOR, Role.USER})
  public ResponseEntity<Product> get(@PathVariable int id) {
    log.info("get {}", id);
    return ResponseEntity.of(productRepository.findById(id));
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @AllowedRoles(Role.MODERATOR)
  public ResponseEntity<Product> createWithLocation(@Valid @RequestBody Product product) {
    log.info("create {}", product);
    ValidationUtil.checkNew(product);
    var created = productRepository.save(product);
    var uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path(REST_URL + "/{id}")
        .buildAndExpand(created.getId()).toUri();
    return ResponseEntity.created(uriOfNewResource).body(created);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @AllowedRoles(Role.MODERATOR)
  public void update(@Valid @RequestBody Product product, @PathVariable int id) {
    log.info("update {} with id={}", product, id);
    ValidationUtil.assureIdConsistent(product, id);
    productRepository.save(product);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @AllowedRoles(Role.MODERATOR)
  public void delete(@PathVariable int id) {
    log.info("delete {}", id);
    productRepository.deleteById(id);
  }


}
