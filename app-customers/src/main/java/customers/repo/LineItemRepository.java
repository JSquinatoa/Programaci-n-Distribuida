package customers.repo;

import customers.db.LineItem;
import customers.db.LineItemId;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class LineItemRepository implements PanacheRepositoryBase<LineItem, LineItemId> {
}
