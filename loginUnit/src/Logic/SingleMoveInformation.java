package Logic;

import AutoGeneratedClasses.BattleShipGame;

import java.util.ArrayList;
import java.util.List;

public class SingleMoveInformation {
    private int boardSize;
    private BattleShipBoard.BattleShipSquare[][] battleshipBoard;
    private TrackingBoard.TrackingSquare[][] trackingBoard;
    private String player;
    private int score;
    private int hit;
    private int miss;
    private float averageMoveTime;
    private int numOfTurns;
    private int numOfMines;
    private List<BattleShipGame.ShipTypes.ShipType> shipTypeList;
    private int row;
    private int column;
    private int boardType;

    public SingleMoveInformation(int boardSize) {
        this.boardSize = boardSize;
        this.shipTypeList = new ArrayList<>();
        this.battleshipBoard = new BattleShipBoard.BattleShipSquare[boardSize][boardSize];
        this.trackingBoard = new TrackingBoard.TrackingSquare[boardSize][boardSize];
    }

    public BattleShipBoard.BattleShipSquare[][] GetBattleshipBoard() {
        return battleshipBoard;
    }

    public void SetBattleshipBoard(BattleShipBoard.BattleShipSquare[][] battleshipBoard) {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                this.battleshipBoard[i][j] = new BattleShipBoard().new BattleShipSquare();
                this.battleshipBoard[i][j].SetWasAttackedByPlayer(battleshipBoard[i][j].GetWasAttackedByPlayer());
                this.battleshipBoard[i][j].SetWasAttackedByBackFire(battleshipBoard[i][j].GetWasAttackedByBackFire());
                this.battleshipBoard[i][j].SetIsBattleShipHere(battleshipBoard[i][j].GetIsBattleShipHere());
                this.battleshipBoard[i][j].SetIsMineHere(battleshipBoard[i][j].GetIsMineHere());
            }
        }
    }

    public TrackingBoard.TrackingSquare[][] GetTrackingBoard() {
        return trackingBoard;
    }

    public void SetTrackingBoard(TrackingBoard.TrackingSquare[][] trackingBoard) {
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                this.trackingBoard[i][j] = new TrackingBoard().new TrackingSquare();
                this.trackingBoard[i][j].SetIsOccupied(trackingBoard[i][j].GetIsOccupied());
                this.trackingBoard[i][j].SetHasAttacked(trackingBoard[i][j].GetHasAttacked());
            }
        }
    }

    public String GetPlayer() {
        return player;
    }

    public void SetPlayer(String player) {
        this.player = player;
    }

    public int GetScore() {
        return score;
    }

    public void SetScore(int score) {
        this.score = score;
    }

    public int GetHit() {
        return hit;
    }

    public void SetHit(int hit) {
        this.hit = hit;
    }

    public int GetMiss() {
        return miss;
    }

    public void SetMiss(int miss) {
        this.miss = miss;
    }

    public float GetAverageMoveTime() {
        return averageMoveTime;
    }

    public void SetAverageMoveTime(float averageMoveTime) {
        this.averageMoveTime = averageMoveTime;
    }

    public int GetNumOfTurns() {
        return numOfTurns;
    }

    public void SetNumOfTurns(int numOfTurns) {
        this.numOfTurns = numOfTurns;
    }

    public int GetNumOfMines() {
        return numOfMines;
    }

    public void SetNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public List<BattleShipGame.ShipTypes.ShipType> GetShipTypeList() {
        return shipTypeList;
    }

    public void SetShipTypeList(List<BattleShipGame.ShipTypes.ShipType> shipTypeList) {
        for (BattleShipGame.ShipTypes.ShipType type : shipTypeList) {
            BattleShipGame.ShipTypes.ShipType typeCopy = new BattleShipGame.ShipTypes.ShipType();
            typeCopy.setId(type.getId());
            typeCopy.setAmount(type.getAmount());
            typeCopy.setCategory(type.getCategory());
            typeCopy.setLength(type.getLength());
            typeCopy.setScore(type.getScore());
            this.shipTypeList.add(typeCopy);
        }
    }

    public int GetRow() {
        return row;
    }

    public void SetRow(int row) {
        this.row = row;
    }

    public int GetColumn() {
        return column;
    }

    public void SetColumn(int column) {
        this.column = column;
    }

    public int GetBoardType() {
        return boardType;
    }

    public void SetBoardType(int boardType) {
        this.boardType = boardType;
    }
}
