package mrriegel.blockdrops;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mrriegel.blockdrops.Plugin.Drop;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

public class Wrapper extends BlankRecipeWrapper implements ITooltipCallback<ItemStack> {

	private ItemStack in;
	private List<Drop> out;

	public Wrapper(ItemStack in, List<Drop> out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public List getInputs() {
		return Collections.singletonList(in);
	}

	@Override
	public List getOutputs() {
		List<ItemStack> lis = Lists.newArrayList();
		for (Drop d : out)
			lis.add(d.out);
		return lis;
	}

	private float chance(ItemStack s, int fortune) {
		for (Drop d : out)
			if (d.out.isItemEqual(s)) {
				switch (fortune) {
				case 0:
					return d.chance0;
				case 1:
					return d.chance1;
				case 2:
					return d.chance2;
				case 3:
					return d.chance3;
				default:
					break;
				}
			}
		return 0f;
	}

	@Override
	public String toString() {
		return "Wrapper [in=" + in + ", out=" + out + "]";
	}

	private Pair<Integer, Integer> pair(ItemStack s, int fortune) {
		for (Drop d : out)
			if (d.out.isItemEqual(s)) {
				switch (fortune) {
				case 0:
					return d.pair0;
				case 1:
					return d.pair1;
				case 2:
					return d.pair2;
				case 3:
					return d.pair3;
				default:
					break;
				}
			}
		return MutablePair.of(0, 0);
	}

	@Override
	public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
		if (!input) {
			long x = (System.currentTimeMillis() / 1500l) % 4;
			String chance = BlockDrops.showChance ? String.format("%.1f", chance(ingredient, (int) x)) + " %  " : "";
			String minmax = BlockDrops.showMinMax ? "Min: " + pair(ingredient, (int) x).getLeft() + "  Max: " + pair(ingredient, (int) x).getRight() : "";
			if (BlockDrops.showChance || BlockDrops.showMinMax)
				tooltip.add(TextFormatting.BLUE + "Fortune " + (0l != x ? I18n.format("enchantment.level." + x) : 0) + " " + TextFormatting.GRAY + chance + minmax);
		}
	}

	public ItemStack getIn() {
		return in;
	}

	public void setIn(ItemStack in) {
		this.in = in;
	}

	public List<Drop> getOut() {
		return out;
	}

	public void setOut(List<Drop> out) {
		this.out = out;
	}
}