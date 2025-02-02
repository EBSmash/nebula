package cope.nebula.client.ui.click.component;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

import java.util.ArrayList;

import static cope.nebula.util.Globals.mc;

/**
 * Represents a base component
 *
 * @author aesthetical
 * @since 3/12/22
 */
public abstract class Component {
    private final String name;
    private double x, y;
    private double width, height;

    protected final ArrayList<Component> children = new ArrayList<>();

    public Component(String name) {
        this.name = name;
    }

    public abstract void drawComponent(int mouseX, int mouseY);
    public abstract void mouseClicked(int mouseX, int mouseY, int button);
    public abstract void mouseReleased(int mouseX, int mouseY, int state);
    public abstract void keyTyped(char typedChar, int keyCode);

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isMouseInBounds(int mouseX, int mouseY) {
        return isMouseWithinBounds(mouseX, mouseY, this.x, this.y, this.width, this.height);
    }

    public static boolean isMouseWithinBounds(int mouseX, int mouseY, double x, double y, double w, double h) {
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
    }

    public static void playClickSound() {
        mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }
}
