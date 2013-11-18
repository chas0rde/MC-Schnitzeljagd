package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;

import java.util.List;

public interface Logic {

    Quest getQuestByManagementCode(String managementCode);
    Point getNextPoint();
    Riddle getMandatoryRiddle();
    List<Riddle> getAdditionalRiddles();
    List<Tip> getTipsForRiddle(Riddle riddle);
}
