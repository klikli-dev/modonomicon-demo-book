/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon_demo_book.datagen.lang;

import com.klikli_dev.modonomicon.api.ModonomiconAPI;
import com.klikli_dev.modonomicon.api.datagen.BookLangHelper;
import com.klikli_dev.modonomicon_demo_book.ModonomiconDemoBook;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class EnUsProvider extends LanguageProvider {

    public EnUsProvider(PackOutput packOutput, String modid) {
        super(packOutput, modid, "en_us");
    }

    private void addDemoBook(){
        //We again set up a lang helper to keep track of the translation keys for us.
        //Forge language provider does not give us access to this.modid, so we get it from our main mod class
        var helper = ModonomiconAPI.get().getLangHelper(ModonomiconDemoBook.MODID);
        helper.book("demo"); //we tell the helper the book we're in.
        this.add(helper.bookName(), "Demo Book"); //and now we add the actual textual book name
        this.add(helper.bookTooltip(), "A book to showcase & test Modonomicon features."); //and the tooltip text

        this.addDemoBookFeaturesCategory(helper);
    }

    private void addDemoBookFeaturesCategory(BookLangHelper helper) {
        helper.category("features"); //tell the helper the category we are in
        this.add(helper.categoryName(), "Features Category"); //annd provide the category name text

        this.addDemoBookMultiblockEntry(helper);
    }

    private void addDemoBookMultiblockEntry(BookLangHelper helper) {
        helper.entry("multiblock"); //tell the helper the entry we are in
        this.add(helper.entryName(), "Multiblock Entry"); //provide the entry name
        this.add(helper.entryDescription(), "An entry showcasing a multiblock."); //and description

        helper.page("intro"); //now we configure the intro page
        this.add(helper.pageTitle(), "Multiblock Page"); //page title
        this.add(helper.pageText(), "Multiblock pages allow to preview multiblocks both in the book and in the world."); //page text

        helper.page("multiblock"); //and finally the multiblock page
        //now provide the multiblock name
        //the lang helper does not handle multiblocks, so we manually add the same key we provided in the DemoBookProvider
        this.add("multiblocks.modonomicon.blockentity", "Blockentity Multiblock.");
        this.add(helper.pageText(), "A sample multiblock."); //and the multiblock page text
    }

    protected void addTranslations() {
        this.addDemoBook();
    }
}
