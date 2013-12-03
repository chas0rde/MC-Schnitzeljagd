package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.model.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.questendpoint.model.Riddle;

import java.util.List;

//Wird noch nicht verwendet, nur als Platzhalter!
class PlayerPointsHelper {
                   //TODO: Hints removed from Riddle
    private static PlayerPointsHelper playerPointsHelper = new PlayerPointsHelper();
    private Player player;
    private final int multiplierMandatoryRiddle = 5;
    private final int multiplierAdditionalRiddle = 10;

    private PlayerPointsHelper() {
    }

    public static PlayerPointsHelper getInstance() {
        return playerPointsHelper;
    }

    public void addPointsForSolvedRiddle(Riddle riddle) {
        if (player != null) {
            if (riddle.getSolved()) {
//                int calculatedPoints = getPoints(riddle.getMaxPoints(), getNrOfUsedHints(riddle.getHintList()), riddle.isMandatory());
//                player.setCurrentPoints(player.getCurrentPoints() + calculatedPoints);
            }
        }
    }

    private int getNrOfUsedHints(List<Hint> hintList) {
        int counter = 0;
        for (Hint hint : hintList) {
            counter++;
        }
        return counter;
    }

    private int getPoints(int maxPointsOfRiddle, int nrOfUsedHints, boolean isRiddleMandatory) {
        int multiplier = 0;
        if (isRiddleMandatory) {
            multiplier = multiplierMandatoryRiddle;
        } else {
            multiplier = multiplierAdditionalRiddle;
        }
        int percent = (maxPointsOfRiddle / 100) * multiplier;
        return maxPointsOfRiddle - percent * nrOfUsedHints;
    }

    public void setPlayer(Player player) {
        if (this.player != player) {
            this.player = player;
        }
    }

    public int getCurrentPointsOfPlayer() {
        return player != null? player.getCurrentPoints() : 0;
    }
}
