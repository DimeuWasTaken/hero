import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Game {
    private Screen screen;
    Arena arena = new Arena(100, 20);
    TextGraphics graphics;
    public Game(){
        try {
            TerminalSize terminalSize = new TerminalSize(100, 20);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = terminalFactory.createTerminal();
            screen = new TerminalScreen(terminal);


            screen.setCursorPosition(null);   // we don't need a cursor
            screen.startScreen();             // screens must be started
            screen.doResizeIfNecessary();

            graphics = screen.newTextGraphics();
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
            arena.draw(graphics);
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
        return arena.processKey(key);
    }

}
