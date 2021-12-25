package server.game;

import server.asciiPanel.AsciiPanel;
import server.screen.Screen;

public class Nothing extends Thing{
    public Nothing(World world){
        super(world);
    }
    @Override
    public char getGlyph() {
        return AsciiPanel.stringCharMap.get(Screen.backgroundStringName[world.gameStage%Screen.backgroundStringName.length]);
    }

}
