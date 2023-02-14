package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LineBackgroundSpan;
import android.widget.TextView;

class LBS implements LineBackgroundSpan {
    private final TextView tv;
    private int start;
    private int end;

    private float padding = 16f;

    public LBS(TextView tv, int start, int end) {
        this.tv = tv;
        this.start = start;
        this.end = end;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        Layout layout = tv.getLayout();
        int startLine = layout.getLineForOffset(this.start);
        int endLine = layout.getLineForOffset(this.end);
        if (startLine <= lnum && lnum <= endLine) {
            if (startLine == lnum) {
                left = (int) layout.getPrimaryHorizontal(this.start);
            }
            if (endLine == lnum) {
                right = (int) layout.getPrimaryHorizontal(this.end);
            }
            int origColor = p.getColor();
            p.setColor(Color.RED);

            c.drawRect(
                    left - padding,
                    top,
                    right + padding,
                    bottom,
                    p);
            p.setColor(origColor);
        }
    }
}