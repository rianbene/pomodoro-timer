package com.rbene.pomodoro;

public class PomodoroManager {

    public enum State { FOCUS, SHORT_BREAK, LONG_BREAK }

    private int focusTime, shortBreakTime, longBreakTime;
    private int cycleCount = 0;
    private State currentState = State.FOCUS;

    public PomodoroManager(int focus, int shortBreak, int longBreak) {
        this.focusTime = focus;
        this.shortBreakTime = shortBreak;
        this.longBreakTime = longBreak;
    }

    public State getState() {
        return currentState;
    }

    public int getCurrentDurationMinutes() {
        switch (currentState) {
            case FOCUS: return focusTime;
            case SHORT_BREAK: return shortBreakTime;
            case LONG_BREAK: return longBreakTime;
        }
        return focusTime;
    }

    public void nextState() {
        if (currentState == State.FOCUS) {
            cycleCount++;
            if (cycleCount % 4 == 0) currentState = State.LONG_BREAK;
            else currentState = State.SHORT_BREAK;
        } else {
            currentState = State.FOCUS;
        }
    }

    public int getCycleCount() {
        return cycleCount;
    }
}
