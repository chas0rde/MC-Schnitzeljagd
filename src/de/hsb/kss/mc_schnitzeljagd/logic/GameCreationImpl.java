package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;

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
    public void addHint(Hint hint) {
        if(hints != null)
        {
            hints.add(hint);
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
    public Hint getHint(int index) {
        if(hints != null)
        {
            return hints.get(index);
        }
        return null;
    }
}
