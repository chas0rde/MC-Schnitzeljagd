package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;

import java.util.List;

public interface GameLogic {

    Quest getQuestByAccessCode(String accessCode);
    Point getNextPoint();
    Riddle getMandatoryRiddleForCurrentPoint();
    Riddle getNextAdditionalRiddles();
    Tip getNextTipForCurrentMandatoryRiddle();
    Tip getNextTipForAdditionalRiddle();
}
