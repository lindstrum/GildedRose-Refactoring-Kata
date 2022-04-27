package com.gildedrose;

import com.gildedrose.mapper.ItemQualityListener;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        updateQuality(ItemQualityListener.createDefaultVisitor());
    }

    private void updateQuality(ItemQualityListener listener) {
        Arrays.asList(items).forEach(listener);
    }
}
