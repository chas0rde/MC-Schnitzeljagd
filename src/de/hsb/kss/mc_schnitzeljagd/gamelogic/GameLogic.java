package de.hsb.kss.mc_schnitzeljagd.gamelogic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;
import de.hsb.kss.mc_schnitzeljagd.persistence.Tip;
import de.hsb.kss.mc_schnitzeljagd.persistence.Player;

import java.util.List;

public interface GameLogic {

	boolean playNewGame(String name);
	boolean playNewGame(String name, String code);
	Player getPlayer();
    Quest getQuestByAccessCode(String accessCode,Player player);
    Point getNextPoint();
    Riddle getMandatoryRiddleForCurrentPoint();
    Riddle getNextAdditionalRiddle();
    Tip getNextTipForCurrentMandatoryRiddle();
    Tip getNextTipForAdditionalRiddle();
    int getCurrentPointsForPlayer();
}
