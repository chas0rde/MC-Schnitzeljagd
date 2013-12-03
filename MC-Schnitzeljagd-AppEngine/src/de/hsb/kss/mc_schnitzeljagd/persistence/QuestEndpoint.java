package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import de.hsb.kss.mc_schnitzeljagd.EMF;

@Api(name = "questendpoint", namespace = @ApiNamespace(ownerDomain = "hsb.de", ownerName = "hsb.de", packagePath = "kss.mc_schnitzeljagd.persistence"))
public class QuestEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listQuest")
	public CollectionResponse<Quest> listQuest(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Quest> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Quest as Quest");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Quest>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Quest obj : execute){
				List<Point> points = obj.getPointList();
				for (Point point : points){
					point.getHintList();
					point.getRiddles();
				}
			}
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Quest> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getQuest")
	public Quest getQuest(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		Quest quest = null;
		try {
			quest = mgr.find(Quest.class, id);
		} finally {
			mgr.close();
		}
		return quest;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param quest the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertQuest")
	public Quest insertQuest(Quest quest) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsQuest(quest)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(quest);
		} finally {
			mgr.close();
		}
		return quest;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param quest the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateQuest")
	public Quest updateQuest(Quest quest) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsQuest(quest)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(quest);
		} finally {
			mgr.close();
		}
		return quest;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeQuest")
	public void removeQuest(@Named("id") Long id) {
		EntityManager mgr = getEntityManager();
		try {
			Quest quest = mgr.find(Quest.class, id);
			mgr.remove(quest);
		} finally {
			mgr.close();
		}
	}

	private boolean containsQuest(Quest quest) {
		if(quest.getKey() == null) return false;
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Quest item = mgr.find(Quest.class, quest.getKey());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
