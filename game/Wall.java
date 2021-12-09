package game;


import asciiPanel.AsciiPanel;

public class Wall extends Thing {

    Wall(World world) {
        super((char) (AsciiPanel.wallIndex), world);
    }

}
