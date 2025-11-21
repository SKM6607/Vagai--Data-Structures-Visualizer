package utils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import javax.swing.*;
public final class MainMenuBar extends JMenuBar {
    private MainMenuBar(){

    }
    @Contract(" -> new")
    public static @NotNull MainMenuBar getInstance(){
        return new MainMenuBar();
    }
}
