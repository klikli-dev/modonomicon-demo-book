// SPDX-FileCopyrightText: 2022 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon_demo_book.ModonomiconDemoBook;
import com.klikli_dev.modonomicon_demo_book.datagen.lang.EnUsProvider;
import com.klikli_dev.modonomicon_demo_book.datagen.lang.EsEsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        var enUsProvider = new EnUsProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID);
        var esEsProvider = new EsEsProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID);

        generator.addProvider(event.includeServer(), new DemoBookProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID,
                enUsProvider, //first add the default language, will be accessed via .lang()
                esEsProvider //then all other languages, will be accessed via .lang("<lang code>"), for example .lang("es_es")
        ));

        //Important: Lang providers need to be added after the book provider to process the texts added by the book provider
        generator.addProvider(event.includeClient(), enUsProvider);
        generator.addProvider(event.includeClient(), esEsProvider);
    }

}
