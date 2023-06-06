package persistence.entity;

public interface EntityManager {

    <T> T find(Class<T> clazz, Long id);

    void persist(Object entity);

    void remove(Object entity);

    <T> T merge(T entity);

    void detach(Object entity);

    void setSnapshot(Object snapshot);

    <T> T getSnapshot(T entity);
}
