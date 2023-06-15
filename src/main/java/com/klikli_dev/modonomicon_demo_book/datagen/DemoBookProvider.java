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

    public DemoBookProvider(PackOutput packOutput, String modid, LanguageProvider lang, LanguageProvider... translations) {
        super("demo", packOutput, modid, lang, translations);
    }

    @Override
    protected BookModel generateBook() {

        //we can reference the language providers in the book provider
        this.lang().add(this.context().bookName(), "Demo Book"); //and now we add the actual textual book name
        this.lang().add(this.context().bookTooltip(), "A book to showcase & test Modonomicon features."); //and the tooltip text

        //similarly, you can add translations here:
        this.lang("es_es").add(this.context().bookName(), "Libro de demostración");
        this.lang("es_es").add(this.context().bookTooltip(), "Un libro para mostrar y probar las funciones de Modonomicon.");

        var featuresCategory = this.makeFeaturesCategory();

        //Now we create the book with settings of our choice
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
        this.lang().add(this.context().categoryName(), "Features Category"); //and provide the category name text
        // here is where you would provide translations, as above with the book name and tooltip

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
        this.lang().add(this.context().entryName(), "Multiblock Entry"); //provide the entry name
        this.lang().add(this.context().entryDescription(), "An entry showcasing a multiblock."); //and description

        this.context().page("intro"); //and now the page
        var multiBlockIntroPage =
                BookTextPageModel.builder() //we start with a text page
                        .withText(this.context().pageText()) //now we can use the context to retrieve the description id for the text
                        .withTitle(this.context().pageTitle()) //and for the title
                        .build();
        this.lang().add(this.context().pageTitle(), "Multiblock Page"); //page title
        this.lang().add(this.context().pageText(), "Multiblock pages allow to preview multiblocks both in the book and in the world."); //page text

        this.context().page("multiblock"); //next page
        var multiblockPreviewPage =
                BookMultiblockPageModel.builder() //now a page to show a multiblock
                        .withMultiblockId("modonomicon:blockentity") //sample multiblock from modonomicon
                        .withMultiblockName("multiblocks.modonomicon_demo_book.blockentity") //and the lang key for its name
                        .withText(this.context().pageText()) //plus a page text
                        .withMultiblockName("multiblocks.modonomicon_demo_book.blockentity") //and the lang key for its name
                        .withText(this.context().pageText()) //plus a page text
                        .build();

        //now provide the multiblock name
        //the lang helper does not handle multiblocks, so we manually add the same key we provided in the DemoBookProvider
        this.lang().add("multiblocks.modonomicon_demo_book.blockentity", "Blockentity Multiblock.");
        //and the multiblock page text
        this.lang().add(this.context().pageText(), "A sample multiblock.");

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
