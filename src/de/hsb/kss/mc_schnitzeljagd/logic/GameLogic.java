package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.*;
import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;

public interface GameLogic {

	boolean playNewGame(String name);
	boolean playNewGame(String name, String code);
	Player getPlayer();
    Quest getQuestByAccessCode(String accessCode,Player player);
    Point goToNextPoint();
    Riddle getMandatoryRiddleForCurrentPoint();
    Riddle getNextAdditionalRiddle();
    Hint getNextHintForCurrentMandatoryRiddle();
    Hint getNextHintForAdditionalRiddle();
    int getCurrentPointsForPlayer();
    boolean checkSolutionForMandatoryRiddle(String answer);
    boolean checkSolutionForAdditionalRiddle(String answer);
}
