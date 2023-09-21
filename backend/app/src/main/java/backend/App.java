package backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CorsHandler;

public class App extends AbstractVerticle {

  @Override
  public void start() {
    HttpServer server = vertx.createHttpServer();
    Router router = Router.router(vertx);

    // CORS settings
    router.route().handler(CorsHandler.create("*")
    .allowedMethod(io.vertx.core.http.HttpMethod.GET)
    .allowedHeader("Access-Control-Allow-Method")
    .allowedHeader("Access-Control-Allow-Origin")
    .allowedHeader("Access-Control-Allow-Credentials")
    .allowedHeader("Content-Type"));

    router.get("/test").handler(this::handleTestEndpoint);

    server.requestHandler(router).listen(8080);
  }

  private void handleTestEndpoint(RoutingContext context) {
    context.response()
      .putHeader("content-type", "text/plain")
      .end("Hello World!");
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new App());
  }
}
