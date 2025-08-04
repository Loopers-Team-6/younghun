package com.loopers.infrastructure.catalog.product.stock;

import com.loopers.domain.catalog.product.stock.StockModel;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface StockJpaRepository extends JpaRepository<StockModel, Long> {
  Optional<StockModel> findByProductId(Long productId);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s FROM StockModel s WHERE s.productId= :productId")
  Optional<StockModel> findByProductIdWithRock(Long productId);

}
