package id.danafix.cbs.api.router;

import id.danafix.cbs.api.handler.AccountHandler;
import id.danafix.cbs.entity.Account;
import id.danafix.cbs.entity.AccountDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
@Configuration
public class AccountRouter  {
    private static final Logger logger = LoggerFactory.getLogger(AccountRouter.class);

    @Bean
    public RouterFunction<?> accountRoute(AccountHandler accountHandler){
        logger.debug("accountRoute called");
//        return RouterFunctions
//                .route(GET("/account")
//                        .and(accept(MediaType.APPLICATION_JSON)), accountHandler::findAll)
//                .andRoute(GET("/account/{id}")
//                        .and(accept(MediaType.APPLICATION_JSON)), accountHandler::findById)
//                .andRoute(POST("/account").and(accept(MediaType.APPLICATION_JSON)), accountHandler::save);
        return nest(path("/account"),

                route(RequestPredicates.GET("/{id}"),
                        request -> ok().body(accountHandler.findById(request.pathVariable("id")), AccountHandler.class))

                        .andRoute(method(HttpMethod.POST),
                                request -> {
                                    accountHandler.insert(request.bodyToMono(Account.class)).subscribe();
                                    return ok().build();
                                }));
    }
 
}
