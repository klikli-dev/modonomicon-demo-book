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

        generator.addProvider(event.includeClient(), new EnUsProvider(generator, ModonomiconDemoBook.MODID));
        generator.addProvider(event.includeServer(), new DemoBookProvider(generator, ModonomiconDemoBook.MODID));
    }

}
