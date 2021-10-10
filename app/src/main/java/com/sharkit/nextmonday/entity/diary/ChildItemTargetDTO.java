package com.sharkit.nextmonday.entity.diary;

import com.sharkit.nextmonday.entity.transformer.TransformToChildItemDTO;
import com.sharkit.nextmonday.entity.transformer.Transformer;

public class ChildItemTargetDTO extends Transformer implements TransformToChildItemDTO {
    private String text;
    private String description;
    private long date;
    private boolean status;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public ChildItemTargetDTO transform(TargetDiary targetDiary) {
        return transformer(targetDiary);
    }
}
