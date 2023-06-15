// SPDX-FileCopyrightText: 2022 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon_demo_book.datagen;

import com.klikli_dev.modonomicon_demo_book.ModonomiconDemoBook;
import com.klikli_dev.modonomicon_demo_book.datagen.lang.EnUsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;

public class DataGenerators {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        var langProvider = new EnUsProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID);
        generator.addProvider(event.includeServer(), new DemoBookProvider(generator.getPackOutput(), ModonomiconDemoBook.MODID, langProvider));
        //Important: Lang provider needs to be added after the book provider to process the texts added by the book provider
        generator.addProvider(event.includeClient(), langProvider);
    }

}
