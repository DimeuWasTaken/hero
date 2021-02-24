import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class Arena {
    private int width;
    private int height;


    public Arena(int w, int h){
        this.width = w;
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
        return (position.getX() > 0 && position.getX() < width && position.getY() > 0 && position.getY() < height);
    }

    public void draw(Screen screen) {
        screen.setCharacter(getHeroX(), getHeroY(), TextCharacter.fromCharacter('X')[0]);
    }

    private int getHeroX() {
        return hero.getX();
    }
    private int getHeroY() {
        return hero.getY();
    }

}
