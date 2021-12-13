import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class WallGameMain {
    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(100, 120);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        int x = 50;
        int y = 42;
        int wallSize = 20;
        final char player = '\u263a';
        final char block = '\u2588';

        terminal.setCursorPosition(x, y);
        terminal.putCharacter(player);

        terminal.flush();

        int i = 0;
        int j = 0;

        int rok = ThreadLocalRandom.current().nextInt(0, 50 - wallSize);
        int u = 100 - rok;
        int rok2 = ThreadLocalRandom.current().nextInt((80 - wallSize), u);

        Position[] wallPositions = new Position[wallSize];
        Position[] wallPositions1 = new Position[wallSize];

        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;

            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
                //change from 100
                if (i % 100 == 0) {
                    if (j < 100) {
                        for (int k = 0; k < wallSize; k++) {
                            terminal.setCursorPosition(rok + k, j);
                            terminal.putCharacter(block);
                            terminal.setCursorPosition(rok + k, j - 1);
                            terminal.putCharacter(' ');
                            wallPositions[k] = new Position(rok + k, j);
                        }

                        j++;
                        for (Position go : wallPositions) {

                            if (go.getX() == x && go.getY() == y) {
                                System.out.println("Game Over");
                                continueReadingInput = false;
                                break;
                            }
                        }
                        terminal.flush();
                        //påbörjar kod för en andra wall slutar
                        for (int k = 0; k < wallSize; k++) {
                            terminal.setCursorPosition(rok2 + k, j);
                            terminal.putCharacter(block);
                            terminal.setCursorPosition(rok2 + k, j - 1);
                            terminal.putCharacter(' ');
                            wallPositions1[k] = new Position(rok2 + k, j);
                        }


                        for (Position go1 : wallPositions1) {
                            if (go1.getX() == x && go1.getY() == y) {
                                System.out.println("Game Over");
                                continueReadingInput = false;
                                break;
                            }
                        }

                        if (continueReadingInput == false) {
                            break;
                        }

                        terminal.flush();
                    }
                }

                    i++;
                }

                while (keyStroke == null) ;

                if (keyStroke != null) {
                    KeyType type = keyStroke.getKeyType();
                    Character c = keyStroke.getCharacter();

                    if (c == Character.valueOf('q')) {
                        continueReadingInput = false;
                        terminal.close();
                        System.out.println("quit");
                    }
                    int oldX = x;
                    int oldY = y;
                    switch (keyStroke.getKeyType()) {
                        case ArrowDown:
                            y += 1;
                            break;
                        case ArrowUp:
                            y -= 1;
                            break;
                        case ArrowRight:
                            x += 1;
                            break;
                        case ArrowLeft:
                            x -= 1;
                            break;
                    }
                    terminal.setCursorPosition(oldX, oldY);
                    terminal.putCharacter(' ');
                    terminal.setCursorPosition(x, y);
                    terminal.putCharacter(player);

                    terminal.flush();
                }
            }

            terminal.setCursorPosition(25, 25);
            String messageGO = "GAME OVER. YOU LOOSE!";
            for (int ind = 0; ind < messageGO.length(); ind++) {
                terminal.putCharacter(messageGO.charAt(ind));
            }

            terminal.flush();
        }


    }



