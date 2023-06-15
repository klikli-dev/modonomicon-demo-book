/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.EntryLocationHelper;
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
        super(packOutput, modid, lang, translations);
    }

    private BookModel makeDemoBook(String bookName) {
        //The lang helper keeps track of the "DescriptionIds", that is, the language keys for translations, for us
        var helper = ModonomiconAPI.get().getLangHelper(this.modid);

        //we tell the helper the book we're in.
        helper.book(bookName);

        //we can reference the language providers in the book provider
        this.lang().add(helper.bookName(), "Demo Book"); //and now we add the actual textual book name
        this.lang().add(helper.bookTooltip(), "A book to showcase & test Modonomicon features."); //and the tooltip text

        //similarly, you can add translations here:
        this.lang("es_es").add(helper.bookName(), "Libro de demostración");
        this.lang("es_es").add(helper.bookTooltip(), "Un libro para mostrar y probar las funciones de Modonomicon.");

        var featuresCategory = this.makeFeaturesCategory(helper);

        //Now we create the book with settings of our choice
        var demoBook = BookModel.create(
                        this.modLoc(bookName), //the id of the book. modLoc() prepends the mod id.
                        helper.bookName() //the name of the book. The lang helper gives us the correct translation key.
                )
                .withTooltip(helper.bookTooltip()) //the hover tooltip for the book. Again we get a translation key.
                .withGenerateBookItem(true) //auto-generate a book item for us.
                .withModel(new ResourceLocation("modonomicon:modonomicon_red")) //use the default red modonomicon icon for the book
                .withCreativeTab(new ResourceLocation("modonomicon", "modonomicon")) //and put it in the modonomicon tab
                .withCategories(featuresCategory);
        return demoBook;
    }

    private BookCategoryModel makeFeaturesCategory(BookLangHelper helper) {
        helper.category("features"); //tell our lang helper the category we are in
        this.lang().add(helper.categoryName(), "Features Category"); //and provide the category name text
        //here is where you would provide translations, as above with the book name and tooltip

        //the entry helper is the second helper for book datagen
        //it allows us to place entries in the category without manually defining the coordinates.
        //each letter can be used to represent an entry
        var entryHelper = ModonomiconAPI.get().getEntryLocationHelper();
        entryHelper.setMap(
                "_____________________",
                "__m______________d___",
                "__________r__________",
                "__c__________________",
                "__________2___3___i__",
                "__s_____e____________"
        );

        //place the multiblock entry where we put the "m" in the map above
        var multiblockEntry = this.makeMultiblockEntry(helper, entryHelper, 'm');

        return BookCategoryModel.create(
                        this.modLoc(helper.category), //the id of the category, as stored in the lang helper. modLoc() prepends the mod id.
                        helper.categoryName() //the name of the category. The lang helper gives us the correct translation key.
                )
                .withIcon("minecraft:nether_star") //the icon for the category. In this case we simply use an existing item.
                .withEntries(multiblockEntry);
    }

    private BookEntryModel makeMultiblockEntry(BookLangHelper helper, EntryLocationHelper entryHelper, char location) {
        helper.entry("multiblock"); //tell our lang helper the entry we are in
        this.lang().add(helper.entryName(), "Multiblock Entry"); //provide the entry name
        this.lang().add(helper.entryDescription(), "An entry showcasing a multiblock."); //and description

        helper.page("intro"); //and now the page
        var multiBlockIntroPage =
                BookTextPageModel.builder() //we start with a text page
                        .withText(helper.pageText()) //lang key for the text
                        .withTitle(helper.pageTitle()) //and for the title
                        .build();
        this.lang().add(helper.pageTitle(), "Multiblock Page"); //page title
        this.lang().add(helper.pageText(), "Multiblock pages allow to preview multiblocks both in the book and in the world."); //page text

        helper.page("multiblock"); //next page
        var multiblockPreviewPage =
                BookMultiblockPageModel.builder() //now a page to show a multiblock
                        .withMultiblockId("modonomicon:blockentity") //sample multiblock from modonomicon
                        .withMultiblockName("multiblocks.modonomicon_demo_book.blockentity") //and the lang key for its name
                        .withText(helper.pageText()) //plus a page text
                        .build();

        //now provide the multiblock name
        //the lang helper does not handle multiblocks, so we manually add the same key we provided in the DemoBookProvider
        this.lang().add("multiblocks.modonomicon_demo_book.blockentity", "Blockentity Multiblock.");
        //and the multiblock page text
        this.lang().add(helper.pageText(), "A sample multiblock.");

        return BookEntryModel.builder()
                .withId(this.modLoc(helper.category + "/" + helper.entry)) //make entry id from lang helper data
                .withName(helper.entryName()) //entry name lang key
                .withDescription(helper.entryDescription()) //entry description lang key
                .withIcon("minecraft:furnace") //we use furnace as icon
                .withLocation(entryHelper.get(location)) //and we place it at the location we defined earlier in the entry helper mapping
                .withPages(multiBlockIntroPage, multiblockPreviewPage) //finally we add our pages to the entry
                .build();
    }

    @Override
    protected void generate() {
        //call our code that generates a book with the id "demo"
        var demoBook = this.makeDemoBook("demo");

        //then add the book to the list of books to generate
        this.add(demoBook);
    }
}
