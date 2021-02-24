import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private int width;
    private int height;
    private List<Wall> walls;


    public Arena(int w, int h){
        this.width = w;
        this.height = h;
        this.walls = createWalls();
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
    public void setPosition(int h){
        this.height = h;
    }

    Position position = new Position(10, 10);
    Hero hero = new Hero(position);
    public int processKey(KeyStroke key) {
        System.out.println(key);
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

}
