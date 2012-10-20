package electricexpansion.alex_hawks.misc;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class WireMillRecipes
{
	private static final WireMillRecipes drawingBase = new WireMillRecipes();

	private static HashMap<String, Integer> inputToRecipe = new HashMap<String, Integer>();
	private static HashMap<Integer, ItemStack> recipeToOutput = new HashMap<Integer, ItemStack>();
	private static HashMap<Integer, Double> recipeToWatts = new HashMap<Integer, Double>();
	private static HashMap<Integer, Integer> recipeToInputQTY = new HashMap<Integer, Integer>();
	//Yes, I know that I can use an Integer[] for the last one, But I won't!!! - Alex_hawks
	
	/**
	 * Used to call methods addDrawing and getDrawingResult.
	 */
	public static final WireMillRecipes drawing()
	{return drawingBase;}

	/**
	 * Adds a drawing recipe.
	 * @param input As an ItemStack
	 * @param output As an ItemStack
	 * @param watts The Watts required for the recipe, Time to process is directly proportional.
	 */
	public static void addDrawing(ItemStack input, ItemStack output, double watts)
	{
		try
		{
			if(input != null && output != null && watts > 0)
			{
				boolean j = true;
				int nextRecipeID = recipeToOutput.size();
				inputToRecipe.put(stackSizeToOne(input) + "", nextRecipeID);
				recipeToOutput.put(nextRecipeID, output);
				recipeToWatts.put(nextRecipeID, watts);
				recipeToInputQTY.put(nextRecipeID, input.stackSize);
			}
			else if (input == null) 
				throw new IOException("Error: Input cannot be null.");
			else if (output == null)
				throw new IOException("Error: Output cannot be null.");
			else if (watts <= 0)
				throw new IOException("Error: Watts must be greater than 0.");
		}
		catch(IOException e)
		{e.printStackTrace();}
	}

	/**
	 * This one supports Forge Ore-Dictionary.
	 * @param input
	 * @param output
	 * @param watts
	 */
	public static void addDrawing(String input, ItemStack output, double watts)
	{
		for ( ItemStack input2 : OreDictionary.getOres(input))
			addDrawing(input2, output, watts);
	}

	/**
	 * Used to get the resulting ItemStack from a source ItemStack
	 * @param item The Source ItemStack
	 * @return The result ItemStack
	 */
	public ItemStack getDrawingResult(ItemStack input) 
	{
		try
		{
			int recipeID = 0;
			recipeID = inputToRecipe.get(stackSizeToOne(input) + "");
			if(input.stackSize >= recipeToInputQTY.get(recipeID))
				return (ItemStack)this.recipeToOutput.get(recipeID);
			else return null;
		}
		catch(NullPointerException e) 
		{return (ItemStack)null;}
	}
	
	public int getInputQTY(ItemStack input)
	{	
		try
		{
			int recipeID = 0;
			recipeID = inputToRecipe.get(stackSizeToOne(input) + "");
			if(input.stackSize >= recipeToInputQTY.get(recipeID))
				return (int)this.recipeToInputQTY.get(recipeID);
			else return (Integer) null;
		}
		catch(NullPointerException e) 
		{return (Integer)null;}
	}
	
	/**
	 * Used to get the required watts from a source ItemStack
	 * @param item The Source ItemStack
	 * @return The result ItemStack
	 */
	public double getDrawingWatts(ItemStack input) 
	{
			try
			{
				int recipeID = 0;
				recipeID = inputToRecipe.get(stackSizeToOne(input) + "");
				if(input.stackSize >= recipeToInputQTY.get(recipeID))
					return (Double)this.recipeToWatts.get(recipeID);
				else return (Double) null;
			}
			catch(NullPointerException e) 
			{return (Double)null;}
	}

	/**
	 * 
	 * @param i An ItemStack
	 * @return The ItemStack, with StackSize set to 1
	 */
	public static ItemStack stackSizeToOne(ItemStack i)
	{
		if(i != null)
			return new ItemStack(i.itemID, 1, i.getItemDamage());
		else return null;
	}
}