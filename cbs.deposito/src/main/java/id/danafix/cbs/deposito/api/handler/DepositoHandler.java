package id.danafix.cbs.deposito.api.handler;

import id.danafix.cbs.deposito.entity.Deposito;
import id.danafix.cbs.deposito.exceptions.CoreException;
import id.danafix.cbs.deposito.service.impl.DepositoService;
import id.danafix.cbs.deposito.util.HandlerResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class DepositoHandler {
    private static final Logger logger = LoggerFactory.getLogger(DepositoHandler.class);

    private DepositoService depositoService;

    @Autowired
    public DepositoHandler(DepositoService depositoService) {
        this.depositoService = depositoService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        logger.debug("Endpoint called - findAll");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(depositoService.findAll(), Deposito.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        logger.debug("Endpoint called - findById");
        String id = request.pathVariable("id");
        return depositoService.findById(id)
                .flatMap(resp -> {
                    return HandlerResponseUtils.ok(resp, request);
                })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(CoreException.class,
                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
    }

    public Mono<ServerResponse> save(ServerRequest request){
        logger.debug("Endpoint called - save");
        return request.bodyToMono(Deposito.class)
                .flatMap(ac -> {
                    return depositoService
                            .save(ac)
                            .flatMap(obj -> {
                                return HandlerResponseUtils.ok(obj, request);
                            });
                });
    }

    public Mono<ServerResponse> getCurrentBalance(ServerRequest request){
        logger.debug("Endpoint called - getCurrentBalance");
        Deposito accountFilter =
                new Deposito(request.pathVariable("branchNumber"), request.pathVariable("accountNumber"));
        return depositoService.getCurrentBalance(accountFilter)
                .flatMap(resp -> {
                    return HandlerResponseUtils.ok(resp, request);
                })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(CoreException.class,
                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
    }

    public Mono<ServerResponse> withdrawBalance(ServerRequest request){
        logger.debug("Endpoint called - withdrawBalance");
        String accountNumber = request.pathVariable("accountNumber");
        String branchNumber = request.pathVariable("branchNumber");

        return  depositoService.findByAccountNumberAndBranchNumber(accountNumber,branchNumber)
                .flatMap(resp -> {
                    return HandlerResponseUtils.ok(resp, request);
                })
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(CoreException.class,
                        error -> HandlerResponseUtils.badRequest(error.getErrorResponse(), request));
    }

}
