import java.awt.Font;
import java.io.InputStream;

public final class FontLoader {
    private static final String BEBAS_NEUE_PATH = "/fonts/BebasNeue-Regular.ttf";
    private static Font bebasNeueBaseFont;

    private FontLoader() {
    }

    public static Font bebasNeue(int style, float size) {
        Font baseFont = getBebasNeueBaseFont();
        return baseFont.deriveFont(style, size);
    }

    private static Font getBebasNeueBaseFont() {
        if (bebasNeueBaseFont != null) {
            return bebasNeueBaseFont;
        }

        try (InputStream inputStream = FontLoader.class.getResourceAsStream(BEBAS_NEUE_PATH)) {
            if (inputStream == null) {
                System.err.println("Missing font resource: " + BEBAS_NEUE_PATH);
                bebasNeueBaseFont = new Font("SansSerif", Font.PLAIN, 12);
                return bebasNeueBaseFont;
            }

            bebasNeueBaseFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            return bebasNeueBaseFont;
        } catch (Exception ex) {
            System.err.println("Failed to load Bebas Neue from " + BEBAS_NEUE_PATH + ": " + ex.getMessage());
            bebasNeueBaseFont = new Font("SansSerif", Font.PLAIN, 12);
            return bebasNeueBaseFont;
        }
    }
}
