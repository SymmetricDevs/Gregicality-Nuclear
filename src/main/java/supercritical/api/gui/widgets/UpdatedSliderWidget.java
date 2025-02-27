package supercritical.api.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.widgets.SliderWidget;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import gregtech.api.util.function.FloatConsumer;
import supercritical.api.util.function.FloatSupplier;
import supercritical.mixins.gregtech.SliderWidgetAccessor;

public class UpdatedSliderWidget extends SliderWidget {

    private final FloatSupplier detector;
    private final SliderWidgetAccessor self;

    public UpdatedSliderWidget(String name, int xPosition, int yPosition, int width, int height, float min, float max,
                               float currentValue, FloatConsumer responder, FloatSupplier detector) {
        super(name, xPosition, yPosition, width, height, min, max, currentValue, responder);
        this.detector = detector;
        this.self = (SliderWidgetAccessor) this;
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY, float partialTicks, IRenderContext context) {
        Position pos = getPosition();
        Size size = getSize();
        if (self.getBackgroundArea() != null) {
            self.getBackgroundArea().draw(pos.x, pos.y, size.width, size.height);
        }
        self.setSliderPosition((detector.get() - self.getMin()) / (self.getMax() - self.getMin()));
        self.setDisplayStringRaw(getDisplayString());

        self.getSliderIcon().draw(pos.x + (int) (self.getSliderPosition() * (float) (size.width - 8)), pos.y,
                self.getSliderWidth(),
                size.height);
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        fontRenderer.drawString(self.getDisplayStringRaw(),
                pos.x + size.width / 2 - fontRenderer.getStringWidth(self.getDisplayStringRaw()) / 2,
                pos.y + size.height / 2 - fontRenderer.FONT_HEIGHT / 2, self.getTextColor());
    }
}
