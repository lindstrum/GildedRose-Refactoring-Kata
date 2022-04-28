package com.gildedrose;

import com.gildedrose.listener.ItemQualityListener;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        updateQuality(ItemQualityListener.createDefaultListener());
    }

    private void updateQuality(ItemQualityListener listener) {
        Arrays.asList(items).forEach(listener);
    }
}
