package bg.uni.sofia.fmi.mjt.dungeon;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Position;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.Treasure;

public class GameEngine {

	private char[][] map;
	private Hero hero;
	private Enemy[] enemies;
	private Treasure[] treasures;
	private int enemiesUsed;
	private int treasuresUsed;

	public GameEngine(char[][] map, Hero hero, Enemy[] enemies, Treasure[] treasures) {
		this.map = map;
		this.hero = hero;
		this.enemies = enemies;
		this.treasures = treasures;
		enemiesUsed = 0;
		treasuresUsed = 0;
	}


	public char[][] getMap() {
		return map;
	}

	public String makeMove(int command) {
		Position newPosition = new Position(hero.getPosition().getCol(), hero.getPosition().getRow());
		switch(command)
		{
		case 0:
			newPosition.moveLeft();
			break;
		case 1:
			newPosition.moveUp();
			break;
		case 2:
			newPosition.moveRight();
			break;
		case 3:
			newPosition.moveDown();
			break;
		default: return "Unknown command entered.";	
		}
		
		char sign = map[newPosition.getRow()][newPosition.getCol()];
		String message = "";
		switch(sign)
		{
		case '.':
			movePosition(newPosition);
			message = "You moved successfully to the next position.";
			break;
		case '#':
			message = "Wrong move. There is an obstacle and you cannot bypass it.";
			break;
		case 'T':
			movePosition(newPosition);
			message = hero.takeTreasure(getTreasure());
			break;
		case 'E':
			message = hero.fightEnemy(getEnemy());
			if(hero.isAlive())
			{
				movePosition(newPosition);
			}
			break;	
		case 'G': 
			message =  "You have successfully passed through the dungeon. Congrats!";
			break;
		}
		return message;
	}


	private Enemy getEnemy()
	{
		if(enemiesUsed < enemies.length)
		{
			return enemies[enemiesUsed++];
		}
		return null;
	}
	
	private Treasure getTreasure() {
		if(treasuresUsed < treasures.length)
		{
			return treasures[treasuresUsed++];
		}
		return null;
	}
	private void movePosition(Position newPosition) {
		map[hero.getPosition().getRow()][hero.getPosition().getCol()] = '.';
		hero.changePosition(newPosition);
		map[hero.getPosition().getRow()][hero.getPosition().getCol()] = 'H';
	}

}