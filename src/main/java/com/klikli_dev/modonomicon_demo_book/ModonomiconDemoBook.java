// SPDX-FileCopyrightText: 2022 klikli-dev
//
// SPDX-License-Identifier: MIT

package com.klikli_dev.modonomicon_demo_book;

import com.klikli_dev.modonomicon_demo_book.datagen.DataGenerators;
import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ModonomiconDemoBook.MODID)
public class ModonomiconDemoBook {
    public static final String MODID = "modonomicon_demo_book";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ModonomiconDemoBook() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(DataGenerators::gatherData);
    }
}
