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

package com.klinker.android.theme_spotlight.adapter;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.klinker.android.theme_spotlight.R;
import com.klinker.android.theme_spotlight.data.FeaturedThemer;
import com.klinker.android.theme_spotlight.data.NetworkIconLoader;
import com.klinker.android.theme_spotlight.fragment.FeaturedThemerFragment;

public class FeaturedThemerAdapter extends AbstractRecyclerAdapter {

    private static final String TAG = "ThemeAdapter";
    private final FeaturedThemerFragment fragment;
    private final FeaturedThemer[] items;

    private LruCache<String, Bitmap> mIconCache;

    public FeaturedThemerAdapter(FeaturedThemerFragment fragment, FeaturedThemer[] items) {
        this.fragment = fragment;
        this.items = items;

        // set up the icon cacher
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mIconCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = getLayoutInflater().inflate(R.layout.theme_item, parent, false);
        final ViewHolder holder = createViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeaturedThemer clickedThemer = items[holder.position];

                // if this is a single pane view, then start a new activity to display our theme
                // if this is a dual pane view, then post this to the spotlight activity themeItemClicked
                // where we will then display that theme in a fragment on the screen
                if (fragment.isTwoPane()) {
//                    fragment.themeItemClicked(clickedApp);
                } else {
//                    Intent intent = new Intent(fragment.getActivity(), ThemeActivity.class);
//                    intent.putExtra(ThemeFragment.ARG_PACKAGE_NAME, clickedApp.getPackageName());
//                    fragment.getActivity().startActivity(intent);
                }
            }
        });

        return holder;
    }

    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(fragment.getActivity());
    }

    public ViewHolder createViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position = position;

        final FeaturedThemer item = items[position];

        holder.title.setText(item.getName());

        String description = item.getDescription();

        if (description != null) {
            holder.description.setText(item.getDescription());
            holder.description.setVisibility(View.VISIBLE);
        } else {
            holder.description.setVisibility(View.GONE);
        }

        // check if we already have this item stored
        Bitmap icon = getBitmapFromMemCache(item.getIconUrl());
        if (icon != null) {
            holder.icon.setImageBitmap(icon);
        } else {
            // since we are loading on a different thread for the icon, set the current
            // one to transparent (don't want it looking funny during recycling
            holder.icon.setImageResource(android.R.color.transparent);

            // start a new thread to download and cache our icon
            NetworkIconLoader loader = new NetworkIconLoader(item.getIconUrl(), holder.icon, item.getIconUrl());
            new Thread(loader).start();
        }

        if (holder.progressBar.getVisibility() == View.VISIBLE) {
            holder.progressBar.setVisibility(View.GONE);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mIconCache.get(key);
    }
}
