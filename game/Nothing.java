package game;

import asciiPanel.AsciiPanel;
import screen.Screen;

public class Nothing extends Thing{
    public Nothing(World world){
        super(world);
    }
    @Override
    public char getGlyph() {
        return AsciiPanel.stringCharMap.get(Screen.backgroundStringName[world.gameStage%Screen.backgroundStringName.length]);
    }

}
