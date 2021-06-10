package id.danafix.cbs.sa.repository;

import id.danafix.cbs.sa.entity.Recipient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RecipientDao extends CrudRepository<Recipient, Long> {

    List<Recipient> findAll();

    Recipient findByName(String recipientName);

    void deleteByName(String recipientName);
}