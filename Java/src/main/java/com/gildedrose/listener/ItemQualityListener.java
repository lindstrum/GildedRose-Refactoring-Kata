package com.gildedrose.listener;

import com.gildedrose.Item;

import java.util.function.Consumer;

/**
 * Listens for items, and mutates an item's state which consumed.
 * <p>
 * This could be replaced with something that implements {@link java.util.function.Function} if we allowed to create new
 * instances of {@link Item}, which would be more immutable
 */
public interface ItemQualityListener extends Consumer<Item> {

    static ItemQualityListener createDefaultListener() {
        return new NavigableMapItemQualityListener();
    }

    void updateNormalItemForDay(Item item);

    void updateAgedBrieForDay(Item item);

    void updateSulfurasForDay(Item item);

    void updateBackstagePassesForDay(Item item);

    void updateConjuredForDay(Item item);

    @Override
    default void accept(Item item) {
        switch (item.name) {
            case "Aged Brie":
                updateAgedBrieForDay(item);
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                updateBackstagePassesForDay(item);
                break;
            case "Conjured Mana Cake":
                updateConjuredForDay(item);
                break;
            case "Sulfuras, Hand of Ragnaros":
                updateSulfurasForDay(item);
                break;
            default:
                updateNormalItemForDay(item);
        }
    }
}
