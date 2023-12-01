/*
 * SPDX-FileCopyrightText: 2022 klikli-dev
 *
 * SPDX-License-Identifier: MIT
 */

package com.klikli_dev.modonomicon_demo_book.datagen.lang;

import com.klikli_dev.modonomicon.api.datagen.AbstractModonomiconLanguageProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconLanguageProvider;
import net.minecraft.data.PackOutput;

public class EsEsProvider extends AbstractModonomiconLanguageProvider {

    public EsEsProvider(PackOutput packOutput, String modid, ModonomiconLanguageProvider cachedProvider) {
        super(packOutput, modid, "es_es", cachedProvider);
    }

    protected void addTranslations() {
        //Here you can do your normal translations
    }
}