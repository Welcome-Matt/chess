package service;

import model.GameData;

import java.util.ArrayList;

public record GameResult(Integer gameID, ArrayList<GameData> games) {}
