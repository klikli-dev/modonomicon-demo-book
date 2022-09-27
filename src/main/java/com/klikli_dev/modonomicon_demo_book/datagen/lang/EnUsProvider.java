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
import net.minecraftforge.common.data.LanguageProvider;

public class EnUsProvider extends LanguageProvider {

    public EnUsProvider(DataGenerator generator, String modid) {
        super(generator, modid, "en_us");
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
    }

    protected void addTranslations() {
        this.addDemoBook();
    }
}
