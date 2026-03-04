package service;

import model.GameData;

import java.util.ArrayList;

public record GameResult(int gameID, ArrayList<GameData> games) {}
