package supercritical.integration.jei.basic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import supercritical.api.nuclear.fission.CoolantRegistry;
import supercritical.api.nuclear.fission.ICoolantStats;

public class CoolantInfo implements IRecipeWrapper {

    public FluidStack coolant;
    public FluidStack hotCoolant;

    private final String temps;
    private final String heatCapacity;
    private final String heatTransfer;
    private final String moderation;
    private String hydrogen;

    public CoolantInfo(Fluid coolant, Fluid hotCoolant) {
        this.coolant = new FluidStack(coolant, 1000);
        this.hotCoolant = new FluidStack(hotCoolant, 1000);

        ICoolantStats stats = CoolantRegistry.getCoolant(this.coolant.getFluid());

        temps = I18n.format("supercritical.coolant.exit_temp",
                stats.getHotCoolant().getTemperature());
        heatCapacity = I18n.format("supercritical.coolant.heat_capacity",
                stats.getSpecificHeatCapacity());
        heatTransfer = I18n.format(I18n.format("supercritical.coolant.cooling_factor",
                stats.getCoolingFactor()));
        moderation = I18n.format("supercritical.coolant.moderation_factor",
                stats.getModeratorFactor());
        if (stats.accumulatesHydrogen()) {
            hydrogen = I18n.format("supercritical.coolant.accumulates_hydrogen");
        }
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.FLUID, coolant);
        ingredients.setOutput(VanillaTypes.FLUID, hotCoolant);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int fontHeight = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;

        int start = 40;
        int linesDrawn = 0;
        minecraft.fontRenderer.drawString(temps, 0, fontHeight * linesDrawn + start, 0x111111);
        linesDrawn++;
        minecraft.fontRenderer.drawString(heatCapacity, 0, fontHeight * linesDrawn + start, 0x111111);
        linesDrawn++;
        minecraft.fontRenderer.drawString(heatTransfer, 0, fontHeight * linesDrawn + start, 0x111111);
        linesDrawn++;
        minecraft.fontRenderer.drawString(moderation, 0, fontHeight * linesDrawn + start, 0x111111);
        linesDrawn++;

        if (hydrogen != null) {
            minecraft.fontRenderer.drawString(hydrogen, 0, fontHeight * linesDrawn + start, 0x111111);
        }
    }
}
