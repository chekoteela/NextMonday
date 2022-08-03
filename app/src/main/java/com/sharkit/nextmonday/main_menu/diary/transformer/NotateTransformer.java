package com.sharkit.nextmonday.main_menu.diary.transformer;

import static com.sharkit.nextmonday.main_menu.diary.transformer.ByteArrayTransformer.toByteArray;
import static com.sharkit.nextmonday.main_menu.diary.transformer.ByteArrayTransformer.toObject;

import android.util.Log;

import com.sharkit.nextmonday.main_menu.diary.domain.Notate;
import com.sharkit.nextmonday.main_menu.diary.enums.NotateType;
import com.sharkit.nextmonday.main_menu.diary.entity.NotateDTO;
import com.sharkit.nextmonday.main_menu.diary.enums.TemplateType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotateTransformer {

    public static List<Notate> toNotates(List<NotateDTO> notateDTOs) {
        return notateDTOs.stream()
                .map(NotateTransformer::toNotate)
                .collect(Collectors.toList());
    }

    public static Notate toNotate(NotateDTO notateDTO) {
        return Notate.builder()
                .parentFolderId(notateDTO.getParentFolderId())
                .notateType((NotateType) toObject(notateDTO.getNotateType()))
                .templateType((TemplateType) toObject(notateDTO.getTemplateType()))
                .id(notateDTO.getId())
                .templateId(notateDTO.getTemplateId())
                .name(notateDTO.getName())
                .build();
    }

    public static NotateDTO toNotateDTO(Notate notate) {
        return NotateDTO.builder()
                .id(notate.getId())
                .parentFolderId(notate.getParentFolderId())
                .name(notate.getName())
                .templateId(notate.getTemplateId())
                .notateType(toByteArray(notate.getNotateType()))
                .templateType(toByteArray(notate.getTemplateType()))
                .build();
    }


}
