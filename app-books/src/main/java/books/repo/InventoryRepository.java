package books.repo;

import books.db.Inventory;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class InventoryRepository implements PanacheRepositoryBase<Inventory,String> {
}
