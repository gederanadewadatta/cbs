package id.danafix.cbs.sa.repository;

import id.danafix.cbs.sa.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<Role, Integer> {

    Role findByName(String name);
}