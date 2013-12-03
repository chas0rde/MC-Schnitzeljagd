package de.hsb.kss.mc_schnitzeljagd.logic;

import java.util.List;

import de.hsb.kss.mc_schnitzeljagd.persistence.model.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Point;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Quest;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

public interface GameLogic {

	boolean playNewGame(String name);
	boolean playNewGame(String name, String code);
	Player getPlayer();
    Quest getQuestByAccessCode(String accessCode,Player player);
    Point goToNextPoint();
    Riddle getMandatoryRiddleForCurrentPoint();
    Riddle getNextAdditionalRiddle();
    List<Hint> getFreeHintsForCurrentPoint();
    List<Hint> getAvailableHintsForCurrentPoint();
    int getCurrentPointsForPlayer();
    boolean checkSolutionForMandatoryRiddle(String answer);
    boolean checkSolutionForAdditionalRiddle(String answer);
}
