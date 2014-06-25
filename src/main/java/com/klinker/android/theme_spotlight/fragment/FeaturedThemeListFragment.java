/*
 * Copyright (C) 2014 Klinker Apps, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.klinker.android.theme_spotlight.fragment;

import android.app.ListFragment;

public class FeaturedThemeListFragment extends ListFragment {

    private static final String TAG = "FeaturedThemeFragment";

    // create new instance of our featured list
    public static FeaturedThemeListFragment newInstance() {
        FeaturedThemeListFragment frag = new FeaturedThemeListFragment();
        return frag;
    }

    public FeaturedThemeListFragment() {

    }
}
