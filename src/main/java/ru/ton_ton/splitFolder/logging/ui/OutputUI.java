package ru.ton_ton.splitFolder.logging.ui;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class OutputUI {
    private static final Level OUTPUT_UI = Level.getLevel("OUTPUT_UI");
    private static final Logger logger = LogManager.getLogger();

    public static void add(String info) {
        logger.log(OUTPUT_UI, info);
    }
}
