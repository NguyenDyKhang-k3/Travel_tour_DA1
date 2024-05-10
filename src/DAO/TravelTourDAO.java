package DAO;

import java.util.List;

/**
 * @author NHUTLQ
 * @param <EntityType>
 * @param <KeyType>
 */
public abstract class TravelTourDAO<EntityType, KeyType> {

    public abstract void insert(EntityType entity);

    public abstract void update(EntityType entity);

    public abstract void delete(KeyType id);

    public abstract List<EntityType> selectAll();

    public abstract EntityType selectById(String id);

    public abstract List<EntityType> selectBySql(String sql, Object... args);
}
