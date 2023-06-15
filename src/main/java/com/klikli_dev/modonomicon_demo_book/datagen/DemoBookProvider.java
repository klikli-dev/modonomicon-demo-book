/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryEntryMap;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

public class DemoBookProvider extends BookProvider {

    public DemoBookProvider(PackOutput packOutput, String modid, LanguageProvider lang) {
        super("demo", packOutput, modid, lang);
    }

    @Override
    protected BookModel generateBook() {

        var featuresCategory = this.makeFeaturesCategory();

        var demoBook = BookModel.create(
                        this.modLoc(this.context().book), //the id of the book. modLoc() prepends the mod id.
                        this.context().bookName() //the name of the book. The lang helper gives us the correct translation key.
                )
                .withTooltip(this.context().bookTooltip()) //the hover tooltip for the book. Again we get a translation key.
                .withGenerateBookItem(true) //auto-generate a book item for us.
                .withModel(new ResourceLocation("modonomicon:modonomicon_red")) //use the default red modonomicon icon for the book
                .withCreativeTab(new ResourceLocation("modonomicon", "modonomicon")) //and put it in the modonomicon tab
                .withCategories(
                        featuresCategory
                );

        return demoBook;
    }

    @Override
    protected void registerDefaultMacros() {
        //currently we have no macros - they only work with the CategoryProviders
    }

    private BookCategoryModel makeFeaturesCategory() {
        this.context().category("features"); //set context to our new category

        //the entry map is another helper for book datagen
        //it allows us to place entries in the category without manually defining the coordinates.
        //each letter can be used to represent an entry
        //Note that getting it this way is deprecated, but will not be removed.
        //  it is just a hint that it is better to use CategoryProviders, for an example see https://github.com/klikli-dev/modonomicon/tree/version/1.20.1/src/main/java/com/klikli_dev/modonomicon/datagen/book
        var entryMap = ModonomiconAPI.get().getEntryMap();
        entryMap.setMap(
                "_____________________",
                "__m______________d___",
                "__________r__________",
                "__c__________________",
                "__________2___3___i__",
                "__s_____e____________"
        );

        //place the multiblock entry where we put the "m" in the map above
        var multiblockEntry = this.makeMultiblockEntry(entryMap, 'm');

        return BookCategoryModel.create(
                        this.modLoc(this.context().category), //the id of the category, as stored in the lang helper. modLoc() prepends the mod id.
                        this.context().categoryName() //the name of the category. The lang helper gives us the correct translation key.
                )
                .withIcon("minecraft:nether_star") //the icon for the category. In this case we simply use an existing item.
                .withEntries(
                        multiblockEntry
                );
    }

    private BookEntryModel makeMultiblockEntry(CategoryEntryMap entryMap, char location) {
        this.context().entry("multiblock"); //tell our context the entry we are in. It puts it on top of the category.

        this.context().page("intro"); //and now the page
        var multiBlockIntroPage =
                BookTextPageModel.builder() //we start with a text page
                        .withText(this.context().pageText()) //now we can use the context to retrieve the description id for the text
                        .withTitle(this.context().pageTitle()) //and for the title
                        .build();

        this.context().page("multiblock"); //next page
        var multiblockPreviewPage =
                BookMultiblockPageModel.builder() //now a page to show a multiblock
                        .withMultiblockId("modonomicon:blockentity") //sample multiblock from modonomicon
                        .withMultiblockName("multiblocks.modonomicon_demo_book.blockentity") //and the lang key for its name
                        .withText(this.context().pageText()) //plus a page text
                        .build();

        return BookEntryModel.builder()
                .withId(this.modLoc(this.context().category + "/" + this.context().entry)) //make entry id from lang helper data. It is good practice to use the category as a prefix like this which causes the entry to be in a subfolder.
                .withName(this.context().entryName()) //entry name lang key
                .withDescription(this.context().entryDescription()) //entry description lang key
                .withIcon("minecraft:furnace") //we use furnace as icon
                .withLocation(entryMap.get(location)) //and we place it at the location we defined earlier in the entry helper mapping
                .withPages(multiBlockIntroPage, multiblockPreviewPage) //finally we add our pages to the entry
                .build();
    }
}
