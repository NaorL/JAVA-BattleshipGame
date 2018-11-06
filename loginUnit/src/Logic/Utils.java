package Logic;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Utils implements Serializable {
    private static final long serialVersionUID = 7L;

    public class Statistics implements Serializable {
        private static final long serialVersionUID = 6L;
        private int totalNumberOfTurns;
        private LocalDateTime startTime;
        private String timeElapsedSinceGameStarted;
        private int firstPlayerScore;
        private int secondPlayerScore;
        private int firstPlayerHit;
        private int secondPlayerHit;
        private int firstPlayerMiss;
        private int secondPlayerMiss;
        private int firstPlayerNumOfTurns;
        private int secondPlayerNumOfTurns;
        private float firstPlayerAverageTimeToAttack;
        private float secondPlayerAverageTimeToAttack;
        private String firstPlayerName;
        private String secondPlayerName;
        private String winnerName;

        public void SetWinnerName(String i_WinnerName) {
            winnerName = i_WinnerName;
        }

        public Statistics() {
            totalNumberOfTurns = 0;
        }

        public int GetTotalNumberOfTurns() {
            return totalNumberOfTurns;
        }

        public void SetTotalNumberOfTurns(int i_TotalNumberOfTurns) {
            totalNumberOfTurns = i_TotalNumberOfTurns;
        }

        public LocalDateTime GetStartTime() {
            return startTime;
        }

        public void SetStartTime(LocalDateTime i_StartTime) {
            startTime = i_StartTime;
        }

        public String GetTimeElapsedSinceGameStarted() {
            return timeElapsedSinceGameStarted;
        }

        public void SetTimeElapsedSinceGameStarted(String i_TimeElapsedSinceGameStarted) {
            timeElapsedSinceGameStarted = i_TimeElapsedSinceGameStarted;
        }

        public int GetFirstPlayerScore() {
            return firstPlayerScore;
        }

        public void SetFirstPlayerScore(int i_FirstPlayerScore) {
            firstPlayerScore = i_FirstPlayerScore;
        }

        public int GetSecondPlayerScore() {
            return secondPlayerScore;
        }

        public void SetSecondPlayerScore(int i_SecondPlayerScore) {
            secondPlayerScore = i_SecondPlayerScore;
        }

        public int GetFirstPlayerHit() {
            return firstPlayerHit;
        }

        public void SetFirstPlayerHit(int i_FirstPlayerHit) {
            firstPlayerHit = i_FirstPlayerHit;
        }

        public int GetSecondPlayerHit() {
            return secondPlayerHit;
        }

        public void SetSecondPlayerHit(int i_SecondPlayerHit) {
            secondPlayerHit = i_SecondPlayerHit;
        }

        public int GetFirstPlayerMiss() {
            return firstPlayerMiss;
        }

        public void SetFirstPlayerMiss(int i_FirstPlayerMiss) {
            firstPlayerMiss = i_FirstPlayerMiss;
        }

        public int GetSecondPlayerMiss() {
            return secondPlayerMiss;
        }

        public void SetSecondPlayerMiss(int i_SecondPlayerMiss) {
            secondPlayerMiss = i_SecondPlayerMiss;
        }

        public float GetFirstPlayerAverageTimeToAttack() {
            return firstPlayerAverageTimeToAttack;
        }

        public void SetFirstPlayerAverageTimeToAttack(float i_FirstPlayerAverageTimeToAttack) {
            firstPlayerAverageTimeToAttack = i_FirstPlayerAverageTimeToAttack;
        }

        public float GetSecondPlayerAverageTimeToAttack() {
            return secondPlayerAverageTimeToAttack;
        }

        public void SetSecondPlayerAverageTimeToAttack(float i_SecondPlayerAverageTimeToAttack) {
            secondPlayerAverageTimeToAttack = i_SecondPlayerAverageTimeToAttack;
        }

        public void SetFirstPlayerNumOfTurns(int i_FirstPlayerNumOfTurns) {
            firstPlayerNumOfTurns = i_FirstPlayerNumOfTurns;
        }

        public void SetSecondPlayerNumOfTurns(int i_SecondPlayerNumOfTurns) {
            secondPlayerNumOfTurns = i_SecondPlayerNumOfTurns;
        }

        public int GetFirstPlayerNumOfTurns() {
            return firstPlayerNumOfTurns;
        }

        public int GetSecondPlayerNumOfTurns() {
            return secondPlayerNumOfTurns;
        }

        public void SetFirstPlayerName(String i_Name){
            firstPlayerName = i_Name;
        }

        public void SetSecondPlayerName(String i_Name){
            secondPlayerName = i_Name;
        }
    }
}
