/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon.api.datagen.BookProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

public class DemoBookProvider extends BookProvider {

    public DemoBookProvider(PackOutput packOutput, String modid, ModonomiconLanguageProvider lang, ModonomiconLanguageProvider... translations) {
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

        var featuresCategory = new FeaturesCategoryProvider(this, "features").generate();

        var demoBook = BookModel.create(
                        this.modLoc(this.context().bookId()), //the id of the book. modLoc() prepends the mod id.
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
        //currently we have no macros
    }
}
