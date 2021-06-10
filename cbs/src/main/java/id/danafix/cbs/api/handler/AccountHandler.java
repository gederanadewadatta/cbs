package id.danafix.cbs.api.handler;

import com.sun.mail.imap.protocol.IMAPProtocol;
import id.danafix.cbs.entity.Account;
import id.danafix.cbs.exceptions.CoreException;
import id.danafix.cbs.service.impl.AccountService;
import id.danafix.cbs.util.HandlerResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class AccountHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccountHandler.class);

    private final AccountService accountService;

    @Autowired
    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        logger.debug("Endpoint called - findAll");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.findAll(), Account.class);
    }
    public Mono<ServerResponse> findByOpeningDate(ServerRequest request){
        logger.debug("Endpoint called - findById");
        String openingDate = request.pathVariable("openingDate");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.findByOpeningDate(openingDate), Account.class);
    }
    public Mono<ServerResponse> findByStatus(ServerRequest request){
        logger.debug("Endpoint called - findById");
        String accountStatus = request.pathVariable("accountStatus");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountService.findByStatus(accountStatus), Account.class);
    }
//    public Mono<ServerResponse> findById(ServerRequest request){
//        logger.debug("Endpoint called - findById");
//        String id = request.pathVariable("id");
//        return accountService.findById(id)
//                .flatMap(resp -> HandlerResponseUtils.ok(resp, request))
//                .switchIfEmpty(ServerResponse.notFound().build())
//                .onErrorResume(CoreException.class,
//                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
//    }

    public Mono<ServerResponse> save(ServerRequest request){
        logger.debug("Endpoint called - save");
        return request.bodyToMono(Account.class)
                .flatMap(ac -> accountService
                        .save(ac)
                        .flatMap(obj -> HandlerResponseUtils.ok(obj, request)));
    }

    public Mono<Account> findById(String id){
        logger.debug("Endpoint called - findById");
        return accountService.findById(id);
    }

    public Flux<Account> insert(Mono<Account> bodyToMono) {

        logger.debug("Endpoint called - insertCustomer");
        return accountService.insert(bodyToMono);
    }
}
