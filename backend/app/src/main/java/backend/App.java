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
import io.vertx.config.ConfigRetriever;

public class App extends AbstractVerticle {

    private JDBCClient jdbc;

    @Override
    public void start() {
        ConfigRetriever retriever = ConfigRetriever.create(vertx);

        retriever.getConfig(ar -> {
            if (ar.failed()) {
                System.err.println("Failed to retrieve the configuration.");
                return;
            }

            JsonObject config = ar.result();

            JsonObject dbConfig = new JsonObject()
                    .put("url", config.getString("DATABASE_URL"))
                    .put("driver_class", config.getString("DATABASE_DRIVER_CLASS"))
                    .put("user", config.getString("DATABASE_USER"))
                    .put("password", config.getString("DATABASE_PASSWORD"));

            jdbc = JDBCClient.createShared(vertx, dbConfig);

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
            router.get("/hello").handler(this::handleHelloEndpoint);
            router.post("/register").handler(this::handleRegister);

            vertx.createHttpServer().requestHandler(router).listen(8080);
        });
    }

    private void handleTestEndpoint(RoutingContext context) {
        context.response()
                .putHeader("content-type", "text/plain")
                .end("Hello Test");
    }

    private void handleHelloEndpoint(RoutingContext context) {
        context.response()
                .putHeader("content-type", "text/plain")
                .end("Hello World!");
    }

    private void handleRegister(RoutingContext context) {
        JsonObject requestBody = context.getBodyAsJson();
        String username = requestBody.getString("username");
        String password = requestBody.getString("password");
        System.out.println(username + "と" + password);

        jdbc.query(
                "select * from cities ;",
                ar -> {
                    if (ar.succeeded()) {
                        // Convert the results to JSON and send as a response
                        JsonArray resultsArray = new JsonArray();
                        ar.result().getRows().forEach(resultsArray::add);
                        context.response().setStatusCode(200).end(resultsArray.encodePrettily());
                    } else {
                        // Log the actual error for debugging
                        System.err.println("Database query error: " + ar.cause().getMessage());
                        ar.cause().printStackTrace();
                        context.response().setStatusCode(500).end("Error querying the database.");
                    }
                });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new App());
    }
}
