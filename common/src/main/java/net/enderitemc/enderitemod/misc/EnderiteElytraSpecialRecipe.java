package net.enderitemc.enderitemod.misc;

import java.util.Map;

import net.enderitemc.enderitemod.EnderiteMod;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class EnderiteElytraSpecialRecipe extends SpecialCraftingRecipe {
    public EnderiteElytraSpecialRecipe(Identifier identifier, CraftingRecipeCategory category) {
        super(identifier, category);
    }

    public boolean matches(RecipeInputInventory craftingInventory, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;

        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack3 = craftingInventory.getStack(i);
            if (!itemStack3.isEmpty()) {
                if (itemStack3.isOf(EnderiteMod.ENDERITE_CHESTPLATE.get())) {
                    if (!itemStack2.isEmpty()) {
                        return false;
                    }

                    itemStack2 = itemStack3;
                } else {
                    if (!(itemStack3.getItem() instanceof ElytraItem)) {
                        return false;
                    }

                    if (!itemStack.isEmpty()) {
                        return false;
                    }

                    itemStack = itemStack3;
                }
            }
        }

        if (!itemStack.isEmpty() && !itemStack2.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public ItemStack craft(RecipeInputInventory craftingInventory, DynamicRegistryManager registryManager) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;

        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack3 = craftingInventory.getStack(i);
            if (!itemStack3.isEmpty()) {
                if (itemStack3.isOf(EnderiteMod.ENDERITE_CHESTPLATE.get())) {
                    itemStack = itemStack3;
                } else if (itemStack3.getItem() instanceof ElytraItem) {
                    itemStack2 = itemStack3.copy();
                }
            }
        }

        if (itemStack2.isEmpty()) {
            return itemStack2;
        } else {
            ItemStack stackO = new ItemStack(EnderiteMod.ENDERITE_ELYTRA.get());
            Map<Enchantment, Integer> map1 = EnchantmentHelper.get(itemStack);
            Map<Enchantment, Integer> map2 = EnchantmentHelper.get(itemStack2);

            for (Map.Entry<Enchantment, Integer> entry2 : map2.entrySet()) {
                map1.merge(entry2.getKey(), entry2.getValue(), (v1, v2) -> v1 < v2 ? v2 : v1);
            }

            EnchantmentHelper.set(map1, stackO);

            NbtCompound nbt = stackO.getNbt();
            NbtCompound nbt_get = itemStack.getNbt();
            if(nbt_get!=null) {
                NbtCompound trim_nbt = nbt_get.getCompound("Trim");
                if(trim_nbt!=null) {
                    nbt.put("Trim", trim_nbt);
                    stackO.setNbt(nbt);
                }
            }

            return stackO;
        }
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    public RecipeSerializer<?> getSerializer() {
        return EnderiteMod.ENDERITE_EYLTRA_SPECIAL_RECIPE.get();
    }

}