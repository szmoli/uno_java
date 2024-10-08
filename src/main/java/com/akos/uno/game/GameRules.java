package com.akos.uno.game;

public class GameRules {
    public GameRules(boolean isJumpInAllowed, boolean isDrawStackingAllowed, boolean isSevenOAllowed, boolean isWildFourChallengeAllowed, int pointsGoal) {
        this.isJumpInAllowed = isJumpInAllowed;
        this.isDrawStackingAllowed = isDrawStackingAllowed;
        this.isSevenOAllowed = isSevenOAllowed;
        this.isWildFourChallengeAllowed = isWildFourChallengeAllowed;
        this.pointsGoal = pointsGoal;
    }

    public boolean isJumpInAllowed() {
        return isJumpInAllowed;
    }

    public boolean isDrawStackingAllowed() {
        return isDrawStackingAllowed;
    }

    public boolean isSevenOAllowed() {
        return isSevenOAllowed;
    }

    public boolean isWildFourChallengeAllowed() {
        return isWildFourChallengeAllowed;
    }

    public int getPointsGoal() {
        return pointsGoal;
    }

    private boolean isJumpInAllowed;
    private boolean isDrawStackingAllowed;
    private boolean isSevenOAllowed;
    private boolean isWildFourChallengeAllowed;
    private int pointsGoal;
}
