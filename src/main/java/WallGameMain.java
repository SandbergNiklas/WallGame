import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;



public class WallGameMain {

    static List<Position[]> walls = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(100, 120);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        int x = 50;
        int y = 42;

        final char player = '\u263a';


        terminal.setCursorPosition(x, y);
        terminal.putCharacter(player);

        terminal.flush();


        boolean continueReadingInput = true;

        while (continueReadingInput) {
            int i = 0;
            int j = 0;
            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
                //change from 100
                // % 50 motsvarar hastigheten

                if (i % 50 == 0) {
                    if (j < 100) {



                       wall(terminal,keyStroke,x,y,continueReadingInput);
                        j++;
                        if (continueReadingInput == false) {
                            break;
                        }

                        terminal.flush();
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

                i++;
            }

            while (keyStroke == null);

        }
    }




                public static void wall (Terminal terminal, KeyStroke keyStroke,int x, int y, Boolean
                continueReadingInput) throws IOException, InterruptedException {

                    final char block = '\u2588';

                    int wallSize = 20;
                    int rok = ThreadLocalRandom.current().nextInt(0, 50 - wallSize);
                    int u = 100 - rok;
                    int rok2 = ThreadLocalRandom.current().nextInt((80 - wallSize), u);


                    Position[] wallPositions = new Position[wallSize];
                    Position[] wallPositions1 = new Position[wallSize];
                    for (int k = 0; k < wallSize; k++) {
                        wallPositions[k] = new Position(rok + k, 0);
                        wallPositions1[k] = new Position(rok2 + k, 0);
                    }
                    walls.add(wallPositions);
                    walls.add(wallPositions1);


                    for (Position[] wall : walls) {

                        for (Position go : wall) {
                            terminal.setCursorPosition(go.getX(), go.getY());
                            terminal.putCharacter(' ');
                            go.setY(go.getY()+1);
                            terminal.setCursorPosition(go.getX(), go.getY());
                            terminal.putCharacter(block);
                            if (go.getX() == x && go.getY() == y) {
                                System.out.println("Game Over");
                                continueReadingInput = false;
                                break;
                            }
                        }
                    }
                    terminal.flush();
                    //påbörjar kod för en andra wall slutar


                }
            }



