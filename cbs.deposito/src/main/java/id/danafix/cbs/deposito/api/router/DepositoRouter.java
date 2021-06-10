package id.danafix.cbs.deposito.api.router;

import id.danafix.cbs.deposito.api.handler.DepositoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class DepositoRouter extends BaseRouter {
    private static final Logger logger = LoggerFactory.getLogger(DepositoRouter.class);

    @Override
    Logger getLogger() {
        return logger;
    }
    @Bean
    public RouterFunction<ServerResponse> accountRoute(DepositoHandler depositoHandler){
        logger.debug("accountRoute called");
        return RouterFunctions
                .route(GET("/deposito")
                        .and(accept(MediaType.APPLICATION_JSON)), depositoHandler::findAll)
                .andRoute(GET("/deposito/{id}")
                        .and(accept(MediaType.APPLICATION_JSON)), depositoHandler::findById)
                .andRoute(GET("/deposito/{accountNumber}/branch/{branchNumber}/balance")
                        .and(accept(MediaType.APPLICATION_JSON)), depositoHandler::getCurrentBalance)
                .andRoute(POST("/deposito").and(accept(MediaType.APPLICATION_JSON)), depositoHandler::save)
                .andRoute(PATCH("/deposito/{accountNumber}/branch/{branchNumber}/withdraw")
                        .and(accept(MediaType.APPLICATION_JSON)), depositoHandler::withdrawBalance);
    }
}
