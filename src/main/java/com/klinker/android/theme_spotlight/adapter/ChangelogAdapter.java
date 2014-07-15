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

import android.app.Activity;
import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.klinker.android.theme_spotlight.R;

public class ChangelogAdapter extends ArrayAdapter<Spanned> {
    private final Context context;
    private final Spanned[] items;

    public ChangelogAdapter(Context context, Spanned[] spans) {
        super(context, R.layout.changelog_item);
        this.context = context;
        this.items = spans;
    }

    @Override
    public Spanned getItem(int i) {
        return items[i];
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView rowView = (TextView) convertView;

        // recycle the view correctly
        if (rowView == null) {
            rowView = inflateChangelog();
        }

        // set the new text to the item
        rowView.setText(items[position]);

        return rowView;
    }

    // inflate changelog view and return it
    public TextView inflateChangelog() {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        return (TextView) inflater.inflate(R.layout.changelog_item, null);
    }
}