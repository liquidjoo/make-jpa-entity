package persistence.entity;

public interface PersistenceContext {

    <T> T getEntity(Object entity);

    void addEntity(Long id, Object entity);
}
