package main.menu;

import utils.main.MainCardPanel;

import static main.interfaces.MacroInterface.SEARCHING_ALGORITHMS;
import static main.interfaces.MacroInterface.SEARCHING_ARRAY;

public final class SearchingMenu extends GenericMenu {
    private static SearchingMenu singleton;

    private SearchingMenu(MainCardPanel parent) {
        super(SEARCHING_ALGORITHMS, SEARCHING_ARRAY, parent);
    }

    public static SearchingMenu getInstance(MainCardPanel parent) {
        return (singleton == null) ? singleton = new SearchingMenu(parent) : singleton;
    }
}
