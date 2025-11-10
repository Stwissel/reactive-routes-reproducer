package com.notessensei.web;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.Route.HttpMethod;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.validation.Valid;

@ApplicationScoped
public class ReactiveRoutesReproducer {

  @PermitAll
  @Route(path = "/hello", methods = HttpMethod.GET)
  Uni<String> hello() {
    return Uni.createFrom().item("Hello World");
  }

  /**
   * Echo URL that allows for testing the request body and headers
   * 
   * @param body JsonObject
   * @param ctx RoutingContext
   * @return JsonObject
   */
  @PermitAll
  @Route(path = "/echo", methods = HttpMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  Uni<JsonObject> echo(@Valid @Body JsonObject body, RoutingContext ctx) {
    JsonObject result = new JsonObject();
    JsonObject headers = new JsonObject();
    ctx.request().headers().forEach(header -> headers.put(header.getKey(), header.getValue()));
    JsonObject principal = ctx.user() != null ? ctx.user().principal() : new JsonObject().put("name", "anonymous");
    result.put("user", principal);
    result.put("path", ctx.request().uri());
    result.put("headers", headers);
    result.put("body", body);
    result.put("method", ctx.request().method().name());
    return Uni.createFrom().item(result);
  }
}
