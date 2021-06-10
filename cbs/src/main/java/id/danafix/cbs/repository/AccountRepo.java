package id.danafix.cbs.repository;

import id.danafix.cbs.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends ReactiveMongoRepository<Account,String> {
}
