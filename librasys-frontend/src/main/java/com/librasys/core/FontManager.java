package com.librasys.core;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum FontManager {
    INSTANCE;

    private Map<String, Font> fonts;

    FontManager() {
        fonts = new HashMap<>();
    }

    public void addFont(String fontName) {
        try {
            String path = String.format("/fonts/%s", fontName);
            InputStream is = getClass().getResourceAsStream(path);
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            fonts.put(fontName, font);
        } catch (FontFormatException | IOException e) {
            Logger.getLogger(FontManager.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    public Font getFont(String fontName) {
        return fonts.get(fontName);
    }
}
