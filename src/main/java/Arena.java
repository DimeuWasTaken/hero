import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private int width;
    private int height;
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;


    public Arena(int w, int h){
        this.width = w;
        this.height = h;
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeigth(){
        return this.height;
    }
    public void setWidth(int w){
        this.width = w;
    }
    public void setHeight(int h){
        this.height = h;
    }

    Position position = new Position(10, 10);
    Hero hero = new Hero(position);
    public int processKey(KeyStroke key) {
        switch(key.getKeyType()){
            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
            case Character:
                if (key.getCharacter() == 'q'){
                    return 1;
                }
            case EOF:
                return 2;
            default:
                break;
        }
        if (verifyMonsterCollisions()){
            System.out.println("game over");
            return 3;
        }
        for (Monster monster: monsters){
            moveMonster(monster.move(), monster);
        }
        if (verifyMonsterCollisions()){
            System.out.println("game over");
            return 3;
        }
        retrieveCoins();
        return 0;
    }

    private void moveHero(Position position) {
        if (canHeroMove(position)){
            hero.setPosition(position);
        }
    }

    private boolean canHeroMove(Position position) {
        for (Wall wall: walls){
            if (wall.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }

    public void draw(TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : monsters)
            monster.draw(graphics);
    }

    private int getHeroX() {
        return hero.getX();
    }
    private int getHeroY() {
        return hero.getY();
    }
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Coin coin = new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);

            while (true){
                if (isValidPosition(coin, coins))
                    break;
            }
            coins.add(coin);
        }
        return coins;
    }

    private List<Monster> createMonsters(){
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            Monster monster = new Monster(new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));

            while (true){
                if (isValidPosition(monster, monsters))
                    break;
            }
            monsters.add(monster);
        }
        return monsters;
    }

    private void retrieveCoins() {
        for (int i = 0; i < this.coins.size(); i++){
            if (this.coins.get(i).position.equals(hero.getPosition())){
                this.coins.remove(i);
                break;
            }
        }
    }




    private boolean isValidPosition(Coin coin, ArrayList<Coin> coins){
        for (int i = 0; i < coins.size(); i++){
            if (coin.position.equals(coins.get(i).position))
                return false;
        }
        return !(coin.position.equals(hero.getPosition()));
    }

    private boolean isValidPosition(Monster monster, ArrayList<Monster> monsters){
        for (int i = 0; i < monsters.size(); i++){
            if (monster.position.equals(monsters.get(i).position))
                return false;
        }
        return !(monster.position.equals(hero.getPosition()));
    }

    private void moveMonster(Position position, Monster monster){
        if (canMonsterMove(position)){
            monster.setPosition(position);
        }
    }

    private boolean canMonsterMove(Position position) {
        for (Wall wall: walls){
            if (wall.getPosition().equals(position)){
                return false;
            }
        }
        return true;
    }

    private boolean verifyMonsterCollisions(){
        for (Monster monster : monsters){
            if (hero.position.equals(monster.position)){
                return true;
            }
        }
        return false;
    }
}
