package com.gildedrose.listener;

import com.gildedrose.Item;
import org.apache.commons.lang3.Validate;

import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

/**
 * {@inheritDoc}
 *
 * @implNote This implementation stores the bounds for each step Sorted tree, and then looks for the closest one below
 * its current sellin value
 */
class NavigableMapItemQualityListener implements ItemQualityListener {

    @Override
    public void updateNormalItemForDay(Item item) {
        NavigableMap<Integer, Integer> expiryToChangeInQuality = new TreeMap<>();
        expiryToChangeInQuality.put(1, -1);
        expiryToChangeInQuality.put(Integer.MIN_VALUE, -2);
        updateItemForDayConditionalOnExpiry(item, expiryToChangeInQuality);
    }

    @Override
    public void updateAgedBrieForDay(Item item) {
        assertItemNameEquals(item, "Aged Brie");
        NavigableMap<Integer, Integer> expiryToChangeInQuality = new TreeMap<>();
        expiryToChangeInQuality.put(1, 1);
        expiryToChangeInQuality.put(Integer.MIN_VALUE, 2);
        updateItemForDayConditionalOnExpiry(item, expiryToChangeInQuality);
    }

    @Override
    public void updateSulfurasForDay(Item item) {
        //Nothing because its legendary
    }

    @Override
    public void updateBackstagePassesForDay(Item item) {
        assertItemNameEquals(item, "Backstage passes to a TAFKAL80ETC concert");
        NavigableMap<Integer, Integer> expiryToChangeInQuality = new TreeMap<>();
        expiryToChangeInQuality.put(11, 1);
        expiryToChangeInQuality.put(6, 2);
        expiryToChangeInQuality.put(1, 3);
        expiryToChangeInQuality.put(Integer.MIN_VALUE, -1 * item.quality);
        updateItemForDayConditionalOnExpiry(item, expiryToChangeInQuality);
    }


    @Override
    public void updateConjuredForDay(Item item) {
        assertItemNameEquals(item, "Conjured Mana Cake");
        NavigableMap<Integer, Integer> expiryToChangeInQuality = new TreeMap<>();
        expiryToChangeInQuality.put(1, -2);
        expiryToChangeInQuality.put(Integer.MIN_VALUE, -4);
        updateItemForDayConditionalOnExpiry(item, expiryToChangeInQuality);
    }

    private static void assertItemNameEquals(Item item, String name) {
        Validate.isTrue(item.name.equals(name));
    }

    private void updateItemForDayConditionalOnExpiry(Item item, NavigableMap<Integer, Integer> expiryToChangeInQuality) {
        Integer changeInQuality = expiryToChangeInQuality.floorEntry(item.sellIn).getValue();
        if (changeInQuality == null) {
            String errorMessage = "No viable amount change for item.sellin= " + item.sellIn + " and steps=" + expiryToChangeInQuality;
            throw new IllegalArgumentException(errorMessage);
        }
        updateItemForDay(item, changeInQuality);
    }

    private void updateItemForDay(Item item, int changeInQuality) {
        Objects.requireNonNull(item);
        int newQuality = item.quality + changeInQuality;
        if (newQuality > 50) {
            newQuality = 50;
        } else if (newQuality < 0) {
            newQuality = 0;
        }
        item.sellIn -= 1;
        item.quality = newQuality;
    }
}
