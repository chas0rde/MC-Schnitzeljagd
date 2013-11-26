package de.hsb.kss.mc_schnitzeljagd.logic;

import de.hsb.kss.mc_schnitzeljagd.persistence.Hint;
import de.hsb.kss.mc_schnitzeljagd.persistence.Player;
import de.hsb.kss.mc_schnitzeljagd.persistence.Riddle;

import java.util.List;

//Wird noch nicht verwendet, nur als Platzhalter!
class PlayerPointsHelper {

    private static PlayerPointsHelper playerPointsHelper = new PlayerPointsHelper();
    private Player player;

    private PlayerPointsHelper() {
    }

    public static PlayerPointsHelper getInstance() {
        return playerPointsHelper;
    }

    public void addPointsForSolvedRiddle(Riddle riddle) {
        if (player != null) {
            if (riddle.isSolved()) {
                int calculatedPoints = getPoints(riddle.getMaxPoints(), getNrOfUsedHints(riddle.getHintList()), riddle.isMandatory());
                player.setCurrentPoints(player.getCurrentPoints() + calculatedPoints);
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
        if (isRiddleMandatory) {
             return 0;
        } else {
            //TODO: used Hints in Relation to MaxNumber of Riddle
            return 0;
        }
    }

    public void setPlayer(Player player) {
        if (this.player != player) {
            this.player = player;
        }
    }

    public int getCurrentPointsOfPlayer() {
        return player.getCurrentPoints();
    }
}
