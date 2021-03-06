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
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.klinker.android.theme_spotlight.R;

abstract class AbstractCachingRecyclerAdapter extends RecyclerView.Adapter<AbstractCachingRecyclerAdapter.ViewHolder> {

    private LruCache<String, Bitmap> mIconCache;

    public AbstractCachingRecyclerAdapter() {
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

    public LruCache<String, Bitmap> getIconCache() {
        return this.mIconCache;
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mIconCache.get(key);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView icon;
        public View progressBar;
        public int position;

        public ViewHolder(View item) {
            super(item);

            title = (TextView) item.findViewById(R.id.title);
            description = (TextView) item.findViewById(R.id.publisher);
            icon = (ImageView) item.findViewById(R.id.icon);
            progressBar = item.findViewById(R.id.loading);
        }
    }
}
