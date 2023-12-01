/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon_demo_book.datagen.lang;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import net.minecraft.data.PackOutput;

public class EnUsProvider extends AbstractModonomiconLanguageProvider {

    public EnUsProvider(PackOutput packOutput, String modid, ModonomiconLanguageProvider cachedProvider) {
        super(packOutput, modid, "en_us", cachedProvider);
    }

    protected void addTranslations() {
        //Here you can do your normal translations
    }
}
