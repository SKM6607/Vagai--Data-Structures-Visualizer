package main.interfaces;

import java.awt.*;

public interface DefaultWindowsInterface {
    int width= Toolkit.getDefaultToolkit().getScreenSize().width;
    int height=Toolkit.getDefaultToolkit().getScreenSize().height;
    Color backgroundColor =new Color(0, 18, 121);
    Color foregroundColor =Color.WHITE;
    Font menuFont=new Font(Font.SANS_SERIF, Font.BOLD, 18);
}
