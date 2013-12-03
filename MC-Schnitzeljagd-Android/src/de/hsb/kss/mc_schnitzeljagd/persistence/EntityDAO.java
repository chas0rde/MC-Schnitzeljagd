package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.io.IOException;
import java.util.List;

public interface EntityDAO<T> {
	T insert (T entity) throws IOException;
	List<T> loadAll() throws IOException;
	T load(Long id) throws IOException;
	T update(T entity) throws IOException;
	boolean delete(Long id) throws IOException;
}
