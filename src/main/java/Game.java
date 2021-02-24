import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    Position position = new Position(10, 10);
    Hero hero = new Hero(position);
    public Game(){
        try {
            TerminalSize terminalSize = new TerminalSize(100, 50);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);


            screen.setCursorPosition(null);   // we don't need a cursor
            screen.startScreen();             // screens must be started
            screen.doResizeIfNecessary();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() throws IOException{
        draw(screen);
    }

    private void draw(Screen screen) throws IOException{
        int result = 0;
        while (result == 0){
            screen.clear();
            hero.draw(screen);
            screen.refresh();

            KeyStroke key = screen.readInput();
            result = processKey(key);

            if (result == 1){
                screen.close();
            } else if (result == 2){
                break;
            }
        }
    }

    private int processKey(KeyStroke key) {
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
        hero.setPosition(position);
    }

}
