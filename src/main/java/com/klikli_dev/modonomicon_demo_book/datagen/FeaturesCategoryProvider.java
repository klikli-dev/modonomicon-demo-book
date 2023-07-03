package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;

public class FeaturesCategoryProvider extends CategoryProvider {
    public FeaturesCategoryProvider(BookProvider parent, String categoryId) {
        super(parent, categoryId);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "_____________________",
                "__m______________d___",
                "__________r__________",
                "__c__________________",
                "__________2___3___i__",
                "__s_____e____________"
        };
    }

    @Override
    protected void generateEntries() {
        //place the multiblock entry where we put the "m" in the map above
        //this.add() adds it to our category
        //additionally we can store it in a variable to modify it later (such as adding conditions, or setting a parent entry)
        var multiblockEntry = this.add(this.makeMultiblockEntry('m'));
    }

    @Override
    protected BookCategoryModel generateCategory() {
        return BookCategoryModel.create(
                        this.modLoc(this.context().categoryId()), //the id of the category, as stored in the lang helper. modLoc() prepends the mod id.
                        this.context().categoryName() //the name of the category. The lang helper gives us the correct translation key.
                )
                .withIcon("minecraft:nether_star"); //the icon for the category. In this case we simply use an existing item.

    }


    private BookEntryModel makeMultiblockEntry(char location) {
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

        return this.entry(location) //create a new entry at the given location
                .withIcon("minecraft:furnace") //we use furnace as icon
                .withPages(
                        multiBlockIntroPage,
                        multiblockPreviewPage
                ); //we add our pages to the entry
    }
}
