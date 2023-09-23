package backend;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.core.json.JsonArray;


public class App extends AbstractVerticle {

    private JDBCClient jdbc;

    @Override
    public void start() {
        JsonObject config = new JsonObject()
                .put("url", "jdbc:mysql://db:3306/mydatabase")
                .put("driver_class", "com.mysql.cj.jdbc.Driver")
                .put("user", "user")
                .put("password", "password");

        jdbc = JDBCClient.createShared(vertx, config);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        // CORS settings
        router.route().handler(CorsHandler.create("*")
        .allowedMethod(io.vertx.core.http.HttpMethod.GET)
        .allowedHeader("Access-Control-Allow-Method")
        .allowedHeader("Access-Control-Allow-Origin")
        .allowedHeader("Access-Control-Allow-Credentials")
        .allowedHeader("Content-Type"));

        router.get("/test").handler(this::handleTestEndpoint);
        router.post("/register").handler(this::handleRegister);

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    private void handleTestEndpoint(RoutingContext context) {
        context.response()
                .putHeader("content-type", "text/plain")
                .end("Hello World!");
    }

    private void handleRegister(RoutingContext context) {
        JsonObject requestBody = context.getBodyAsJson();
        String username = requestBody.getString("username");
        String password = requestBody.getString("password");

        jdbc.updateWithParams(
                "INSERT INTO users (username, password) VALUES (?, ?)",
                new JsonArray().add(username).add(password),
                ar -> {
                    if (ar.succeeded()) {
                        context.response().setStatusCode(201).end("User registered successfully.");
                    } else {
                        context.response().setStatusCode(500).end("Error registering user.");
                    }
                });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new App());
    }
}
