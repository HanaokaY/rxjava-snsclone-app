package backend;

import io.reactivex.rxjava3.core.Observable;
import io.vertx.core.AbstractVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8080);

    // RxJavaの使用例
    Observable<String> observable = Observable.create(emitter -> {
      emitter.onNext("Hello");
      emitter.onNext("RxJava");
      emitter.onComplete();
    });

    observable.subscribe(item -> System.out.println("Received: " + item));
  }
}
