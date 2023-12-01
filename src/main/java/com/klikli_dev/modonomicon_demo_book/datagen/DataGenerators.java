// SPDX-FileCopyrightText: 2022 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.klikli_dev.modonomicon_demo_book.ModonomiconDemoBook;
import com.klikli_dev.modonomicon_demo_book.datagen.lang.EnUsProvider;
import com.klikli_dev.modonomicon_demo_book.datagen.lang.EsEsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        //we create language provider caches where our book provider can store texts in.
        var enUsCache = new LanguageProviderCache("en_us");
        var esEsCache = new LanguageProviderCache("es_es");;

        generator.addProvider(event.includeServer(), new DemoBookProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID,
                enUsCache, //first add the default language, will be accessed via .lang()
                esEsCache //then all other languages, will be accessed via .lang("<lang code>"), for example .lang("es_es")
        ));

        //Important: Lang providers need to be added after the book provider to process the texts added by the book provider
        //           Additionally, we need to hand over our caches to the actual language provider, so the book texts are added.
        generator.addProvider(event.includeClient(), new EnUsProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID, enUsCache));
        generator.addProvider(event.includeClient(), new EsEsProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID, esEsCache));
    }

}
