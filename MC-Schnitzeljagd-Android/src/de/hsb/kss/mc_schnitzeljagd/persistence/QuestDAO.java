/**
 * 
 */
package de.hsb.kss.mc_schnitzeljagd.persistence;

import java.io.IOException;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;

import de.hsb.kss.mc_schnitzeljagd.CloudEndpointUtils;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.Questendpoint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.CollectionResponseQuest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;

/**
 * @author Svenja Hilgen
 *
 */
public class QuestDAO implements EntityDAO<Quest> {
	
	private Questendpoint questEndpoint;
	
	public QuestDAO(){
		 /*
 	     * build the quest endpoint so we can access the data in the AppEngine
 	     */
 	    Questendpoint.Builder questEndpointBuilder = new Questendpoint.Builder(
 	        AndroidHttp.newCompatibleTransport(),
 	        new JacksonFactory(),
 	        new HttpRequestInitializer() {
 	          public void initialize(HttpRequest httpRequest) { }
 	        });

 	    questEndpoint = CloudEndpointUtils.updateBuilder(questEndpointBuilder).build();
	}
	
	public Quest insert(Quest quest) throws IOException{		
   		Quest result = questEndpoint.insertQuest(quest).execute();
   		return result;
	}	
	public List<Quest>loadAll() throws IOException{	
		CollectionResponseQuest questCollectionResponse = questEndpoint.listQuest().execute();
   		return questCollectionResponse.getItems();
	}
	public Quest load(Long id) throws IOException{	
		Quest quest = questEndpoint.getQuest(id).execute();
   		return quest;
	}
	public Quest update(Quest quest) throws IOException{
		Quest result = questEndpoint.updateQuest(quest).execute();
		return result;
	}
	
	public boolean delete(Long id) throws IOException{
		questEndpoint.removeQuest(id).execute();
		return true;
	}
}
