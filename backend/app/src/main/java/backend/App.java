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
import backend.DBConfig;

public class App extends AbstractVerticle {

    private JDBCClient jdbc;

    @Override
    public void start() {
        DBConfig dbConfig = new DBConfig("./.env");

        JsonObject config = new JsonObject()
                .put("url", dbConfig.getDBURL())
                .put("driver_class", "org.postgresql.Driver")
                .put("user", dbConfig.getDBUser())
                .put("password", dbConfig.getDBPassword());

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
        router.get("/hello").handler(this::handleHelloEndpoint);
        router.post("/register").handler(this::handleRegister);
        router.post("/tweet").handler(this::handleTweetSave);

        vertx.createHttpServer().requestHandler(router).listen(8080);
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
        String password = requestBody.getString("password"); // とりあえずハッシュ化はしてない
    
        if (username == null || password == null) {
            context.response().setStatusCode(400).end("Username or Password cannot be empty.");
            return;
        }
    
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?);";
    
        JsonArray params = new JsonArray().add(username).add(password);
    
        jdbc.updateWithParams(insertQuery, params, ar -> {
            if (ar.succeeded()) {
                context.response().setStatusCode(201).end("User registered successfully.");
            } else {
                System.err.println("Database insert error: " + ar.cause().getMessage());
                ar.cause().printStackTrace();
                context.response().setStatusCode(500).end("Error registering user.");
            }
        });
    }
    
    private void handleTweetSave(RoutingContext context) {
        JsonObject requestBody = context.getBodyAsJson();
        Integer userId = requestBody.getInteger("userId");
        String content = requestBody.getString("content");
    
        if (userId == null || content == null) {
            context.response().setStatusCode(400).end("UserId or text cannot be empty.");
            return;
        }
    
        String insertQuery = "INSERT INTO tweets (user_id, content) VALUES (?, ?);";
    
        JsonArray params = new JsonArray().add(userId).add(content);
    
        jdbc.updateWithParams(insertQuery, params, ar -> {
            if (ar.succeeded()) {
                context.response().setStatusCode(201).end("User registered successfully.");
            } else {
                System.err.println("Database insert error: " + ar.cause().getMessage());
                ar.cause().printStackTrace();
                context.response().setStatusCode(500).end("Error registering user.");
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new App());
    }
}
