package id.danafix.cbs.deposito.repository;

import id.danafix.cbs.deposito.entity.Deposito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class DepositoRepository {
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired
    public DepositoRepository(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public Mono<Deposito> insert(Deposito deposito) {
        return reactiveMongoTemplate.insert(deposito);
    }

    public Mono<Deposito> save(Deposito deposito) {
        return reactiveMongoTemplate.save(deposito);
    }

    public Mono<Deposito> delete(Deposito deposito) {
        return reactiveMongoTemplate.remove(deposito)
                .flatMap(a -> Mono.just(deposito)).onErrorResume(error -> Mono.error(error));
    }

    public Mono<Void> deleteAll(){
        return reactiveMongoTemplate.dropCollection(Deposito.class);
    }

    public Flux<Deposito> findAll(){
        return reactiveMongoTemplate.findAll(Deposito.class);
    }

    public Mono<Deposito> findById(String id) {
        return reactiveMongoTemplate.findById(id, Deposito.class);
    }

    public Flux<Deposito> findByBranch(Deposito depositoFilter) {
        return reactiveMongoTemplate.findAll(Deposito.class)
                .filter(Deposito -> Deposito.getBranchNumber()
                        .compareToIgnoreCase(depositoFilter.getBranchNumber()) == 0);
    }

    public Flux<Deposito> findByDepositoNumber(Deposito depositoFilter) {
        return reactiveMongoTemplate.findAll(Deposito.class)
                .filter(deposito -> deposito.getAccountNumber()
                        .compareToIgnoreCase(depositoFilter.getAccountNumber()) == 0);
    }

    public Mono<Deposito> findByBranchAndDepositoNumber(Deposito depositoFilter) {
        Query queryBranchFilter = new Query(where("branchNumber").is(depositoFilter.getBranchNumber()));
        return reactiveMongoTemplate.find(queryBranchFilter, Deposito.class)
                .filter(deposito -> deposito.getAccountNumber()
                        .compareToIgnoreCase(depositoFilter.getAccountNumber()) == 0)
                .elementAt(0, new Deposito());
    }
}
