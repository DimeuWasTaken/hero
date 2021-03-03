import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{

    public Monster(Position position) {
        super(position.getX(), position.getY());
    }

    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#440088"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }

    public Position moveUp(){
        return new Position(position.getX(), position.getY() - 1);
    }
    public Position moveDown(){
        return new Position(position.getX(), position.getY() + 1);
    }
    public Position moveRight(){
        return new Position(position.getX() + 1, position.getY());
    }
    public Position moveLeft(){
        return new Position(position.getX() - 1, position.getY());
    }

    public Position move(){
        Random random = new Random();
        int direction = random.nextInt(4);
        switch(direction){
            case 0:
                return moveUp();
            case 1:
                return moveDown();
            case 2:
                return moveRight();
            case 3:
                return moveLeft();
            default:
                return null;
        }
    }

}
