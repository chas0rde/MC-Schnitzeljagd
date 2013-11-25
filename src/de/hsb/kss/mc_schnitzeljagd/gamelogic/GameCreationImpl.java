package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;

class GameCreationImpl extends AbstractGameLogic implements GameCreation{

    @Override
    public boolean loadQuestByAccessCode(String code) {
        return loadQuest(code, true);
    }

    @Override
    public void createNewQuest() {
        loadQuest("", true);
    }

    @Override
    public Quest getCurrentQuest() {
        return quest;
    }

    @Override
    public void setQuestInfo(String name, String author) {
        if(quest != null)
        {
            quest.setName(name);
            quest.setAuthor(author);
        }

    }

    @Override
    public boolean save() {
        quest.setAccessCode("42"); //TODO: Create New Code;
        //TODO: Save in DataStore
        return true;
    }

    @Override
    public void addHint(Tip t) {
        if(hints != null)
        {
            hints.add(t);
        }
    }

    @Override
    public void deleteHint(int index) {
        if(hints != null)
        {
            hints.remove(index);
        }
    }

    @Override
    public Tip getHint(int index) {
        if(hints != null)
        {
            return hints.get(index);
        }
        return null;
    }
}
