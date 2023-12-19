package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookMultiblockPageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

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
        this.lang().add(this.context().categoryName(), "Features Category"); //provide the category name text

        return BookCategoryModel.create(
                        this.modLoc(this.context().categoryId()), //the id of the category, as stored in the lang helper. modLoc() prepends the mod id.
                        this.context().categoryName() //the name of the category. The lang helper gives us the correct translation key.
                )
                .withIcon(Items.NETHER_STAR); //the icon for the category. In this case we simply use an existing item.

    }


    private BookEntryModel makeMultiblockEntry(char location) {
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
                        .build();
        //now provide the multiblock name
        //the lang helper does not handle multiblocks, so we manually add the same key we provided in the DemoBookProvider
        this.lang().add("multiblocks.modonomicon_demo_book.blockentity", "Blockentity Multiblock.");
        //and the multiblock page text
        this.lang().add(this.context().pageText(), "A sample multiblock.");

        return this.entry(location) //create a new entry at the given location
                .withIcon(Blocks.FURNACE) //we use furnace as icon
                .withPages(
                        multiBlockIntroPage,
                        multiblockPreviewPage
                ); //we add our pages to the entry
    }
}
