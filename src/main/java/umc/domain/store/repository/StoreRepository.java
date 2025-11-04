package umc.domain.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
}
