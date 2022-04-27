package com.gildedrose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class GildedRoseTest {

    private static Item[] runTest(Item[] items) {
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        return app.items;
    }

    private static void assertItemEquals(Item actual, String expectedName, int expectedSellin, int expectedQuality) {
        Assertions.assertAll(
            () -> assertEquals(expectedName, actual.name),
            () -> assertEquals(expectedSellin, actual.sellIn),
            () -> assertEquals(expectedQuality, actual.quality)
        );
    }

    @Test
    void testUpdateQualityForEmptyArray() {
        Item[] result = runTest(new Item[0]);
        assertEquals(0, result.length);
    }

    @Test
    void testUpdatingMultipleItems() {
        Item[] result = runTest(new Item[]{
            new Item("some_item1", 1, 4),
            new Item("Sulfuras, Hand of Ragnaros", -10, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 2, 50)
        });

        Assertions.assertAll(
            () -> assertItemEquals(result[0], "some_item1", 0, 3),
            () -> assertItemEquals(result[1], "Sulfuras, Hand of Ragnaros", -10, 80),
            () -> assertItemEquals(result[2], "Backstage passes to a TAFKAL80ETC concert", 1, 50)
        );
    }

    @Test
    void testQualityForNormalItemAndPositiveSellin() {
        Item[] result = runTest(new Item[]{new Item("some_item1", 1, 4)});
        assertItemEquals(result[0], "some_item1", 0, 3);
    }

    @Test
    void testQualityForNormalItemAndZeroSellin() {
        Item[] result = runTest(new Item[]{new Item("some_item2", 0, 2)});
        assertItemEquals(result[0], "some_item2", -1, 0);
    }

    @Test
    void testQualityForNormalItemAndNegativeSellin() {
        Item[] result = runTest(new Item[]{new Item("something_else", -2, 2)});
        assertItemEquals(result[0], "something_else", -3, 0);
    }

    @Test
    void testZeroQualityForNormalItemAndPositiveSellin() {
        Item[] result = runTest(new Item[]{new Item("some_item3", 2, 0)});
        assertItemEquals(result[0], "some_item3", 1, 0);
    }

    @Test
    void testZeroQualityForNormalItemAndZeroSellin() {
        Item[] result = runTest(new Item[]{new Item("some_item4", 0, 0)});
        assertItemEquals(result[0], "some_item4", -1, 0);
    }

    @Test
    void testZeroQualityForNormalItemAndNegativeSellin() {
        Item[] result = runTest(new Item[]{new Item("who_knows", -100, 0)});
        assertItemEquals(result[0], "who_knows", -101, 0);
    }

    @Test
    void testLowQualityForAgedBrieAndPostiveSellin() {
        Item[] result = runTest(new Item[]{new Item("Aged Brie", 2, 0)});
        assertItemEquals(result[0], "Aged Brie", 1, 1);
    }

    @Test
    void testLowQualityForAgedBrieAndZeroSellin() {
        Item[] result = runTest(new Item[]{new Item("Aged Brie", 0, 5)});
        assertItemEquals(result[0], "Aged Brie", -1, 7);
    }

    @Test
    void testLowQualityForAgedBrieAndNegativeSellin() {
        Item[] result = runTest(new Item[]{new Item("Aged Brie", -10, 20)});
        assertItemEquals(result[0], "Aged Brie", -11, 22);
    }

    @Test
    void testMaxQualityForAgedBrieAndPositiveSellin() {
        Item[] result = runTest(new Item[]{new Item("Aged Brie", 10, 50)});
        assertItemEquals(result[0], "Aged Brie", 9, 50);
    }

    @Test
    void testMaxQualityForAgedBrieAndZeroSellin() {
        Item[] result = runTest(new Item[]{new Item("Aged Brie", 0, 50)});
        assertItemEquals(result[0], "Aged Brie", -1, 50);
    }

    @Test
    void testMaxQualityForAgedBrieAndNegativeSellin() {
        Item[] result = runTest(new Item[]{new Item("Aged Brie", -30, 50)});
        assertItemEquals(result[0], "Aged Brie", -31, 50);
    }

    @Test
    void testQualityForSulfurasAndPositiveSellin() {
        Item[] result = runTest(new Item[]{new Item("Sulfuras, Hand of Ragnaros", 30, 80)});
        assertItemEquals(result[0], "Sulfuras, Hand of Ragnaros", 30, 80);
    }

    @Test
    void testQualityForSulfurasAndZer0Sellin() {
        Item[] result = runTest(new Item[]{new Item("Sulfuras, Hand of Ragnaros", 0, 80)});
        assertItemEquals(result[0], "Sulfuras, Hand of Ragnaros", 0, 80);
    }

    @Test
    void testQualityForSulfurasAndNegativeSellin() {
        Item[] result = runTest(new Item[]{new Item("Sulfuras, Hand of Ragnaros", -10, 80)});
        assertItemEquals(result[0], "Sulfuras, Hand of Ragnaros", -10, 80);
    }

    @Test
    void testQualityForBackstagePassesBefore10Days() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 11, 25)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", 10, 26);
    }

    @Test
    void testQualityForBackstagePassesBefore5Days() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 6, 10)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", 5, 12);
    }

    @Test
    void testQualityForBackstagePassesWithin5Days() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 4, 10)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", 3, 13);
    }

    @Test
    void testQualityForBackstagePassesAtZeroDays() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 24)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", -1, 0);
    }

    @Test
    void testMaxQualityForBackstagePassesBefore10Days() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 11, 50)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", 10, 50);
    }

    @Test
    void testMaxQualityForBackstagePassesBefore5Days() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 6, 50)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", 5, 50);
    }

    @Test
    void testMaxQualityForBackstagePassesWithin5Days() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 4, 50)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", 3, 50);
    }

    @Test
    void testMaxQualityForBackstagePassesAtZeroDays() {
        Item[] result = runTest(new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 0, 50)});
        assertItemEquals(result[0], "Backstage passes to a TAFKAL80ETC concert", -1, 0);
    }

    @Test
    void testQualityForConjuredWithPositiveSellin() {
        Item[] result = runTest(new Item[]{new Item("Conjured Mana Cake", 3, 10)});
        assertItemEquals(result[0], "Conjured Mana Cake", 2, 8);
    }

    @Test
    void testQualityForConjuredWithZeroSellin() {
        Item[] result = runTest(new Item[]{new Item("Conjured Mana Cake", 0, 4)});
        assertItemEquals(result[0], "Conjured Mana Cake", -1, 0);
    }

    @Test
    void testQualityForConjuredWithNegativeSellin() {
        Item[] result = runTest(new Item[]{new Item("Conjured Mana Cake", -4, 8)});
        assertItemEquals(result[0], "Conjured Mana Cake", -5, 4);
    }

    @Test
    void testZeroQualityForConjuredWithPositiveSellin() {
        Item[] result = runTest(new Item[]{new Item("Conjured Mana Cake", 200, 0)});
        assertItemEquals(result[0], "Conjured Mana Cake", 199, 0);
    }

    @Test
    void testZeroQualityForConjuredWithZeroSellin() {
        Item[] result = runTest(new Item[]{new Item("Conjured Mana Cake", 0, 0)});
        assertItemEquals(result[0], "Conjured Mana Cake", -1, 0);
    }

    @Test
    void testZeroQualityForConjuredWithNegativeSellin() {
        Item[] result = runTest(new Item[]{new Item("Conjured Mana Cake", -100, 0)});
        assertItemEquals(result[0], "Conjured Mana Cake", -101, 0);
    }
}
